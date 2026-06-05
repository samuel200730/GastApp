package com.example.Gastapp.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/reporte")
@CrossOrigin(origins = "*")
public class ReporteControlador {

    /**
     * POST /api/reporte/generar
     * Ejecuta el script Python reporte_gastapp.py y devuelve las rutas
     * de los archivos generados (HTML y PDF).
     *
     * El script debe estar en la misma carpeta desde donde se ejecuta
     * Spring Boot, o ajusta SCRIPT_PATH a la ruta absoluta correcta.
     */
    private static final String SCRIPT_NAME = "reporte_gastapp.py";

    @PostMapping("/generar")
    public ResponseEntity<Map<String, Object>> generarReporte() {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            // Busca el script en el directorio de trabajo actual
            File script = new File(SCRIPT_NAME);
            if (!script.exists()) {
                respuesta.put("ok", false);
                respuesta.put("error", "No se encontro el archivo " + SCRIPT_NAME +
                        " en: " + new File(".").getAbsolutePath());
                return ResponseEntity.status(500).body(respuesta);
            }

            // Ejecuta el script Python
            ProcessBuilder pb = new ProcessBuilder("python", SCRIPT_NAME);
            pb.directory(new File("."));
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            // Captura la salida del script
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));
            StringBuilder salida = new StringBuilder();
            String linea;
            String archivoHtml = null;
            String archivoPdf  = null;

            while ((linea = reader.readLine()) != null) {
                salida.append(linea).append("\n");
                if (linea.contains("HTML ->") || linea.contains("HTML generado:")) {
                    archivoHtml = linea.trim().replaceAll(".*->\\s*", "").replaceAll(".*HTML generado:\\s*", "").trim();
                }
                if (linea.contains("PDF  ->") || linea.contains("PDF generado:")) {
                    archivoPdf = linea.trim().replaceAll(".*->\\s*", "").replaceAll(".*PDF generado:\\s*", "").trim();
                }
            }

            int exitCode = proceso.waitFor();

            if (exitCode != 0) {
                respuesta.put("ok", false);
                respuesta.put("error", "El script termino con errores.");
                respuesta.put("detalle", salida.toString());
                return ResponseEntity.status(500).body(respuesta);
            }

            respuesta.put("ok", true);
            respuesta.put("mensaje", "Reporte generado exitosamente");
            respuesta.put("archivoHtml", archivoHtml);
            respuesta.put("archivoPdf",  archivoPdf);
            respuesta.put("salida", salida.toString());
            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            respuesta.put("ok", false);
            respuesta.put("error", e.getMessage());
            return ResponseEntity.status(500).body(respuesta);
        }
    }

    /**
     * GET /api/reporte/descargar?archivo=reporte_gastapp_xxx.pdf
     * Descarga el archivo generado directamente desde el backend.
     */
    @GetMapping("/descargar")
    public ResponseEntity<byte[]> descargarArchivo(@RequestParam String archivo) {
        try {
            File file = new File(archivo);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
            byte[] bytes = Files.readAllBytes(file.toPath());
            String contentType = archivo.endsWith(".pdf")
                    ? "application/pdf"
                    : "text/html";
            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
