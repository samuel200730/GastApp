package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Usuario;
import com.example.Gastapp.servicios.UsuarioServicio;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio servicio;

    // GUARDAR USUARIO
    @PostMapping
    public Usuario guardar(@RequestBody Usuario datos) {

        return servicio.guardar_usuario(datos);
    }

    // LISTAR USUARIOS
    @GetMapping
    public List<Usuario> listar() {

        return servicio.listar_usuarios();
    }

    // BUSCAR USUARIO POR ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Long id){

        return servicio.buscarPorId(id);
    }

    // ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    public Usuario actualizar(
            @PathVariable Long id,
            @RequestBody Usuario datos){

        return servicio.actualizar(id, datos);
    }

    // ELIMINAR USUARIO
   @DeleteMapping("/{id}")
public ResponseEntity<Void> eliminar(@PathVariable Long id){
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
}
}