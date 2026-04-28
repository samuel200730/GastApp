package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.MetodoDePago;
import com.example.Gastapp.servicios.MetodoDePagoServicio;

@RestController
@RequestMapping("/api/metodos_de_pago")
public class MetodoDePagoControlador {

    @Autowired
    private MetodoDePagoServicio servicio;

    @PostMapping
    public MetodoDePago guardar(@RequestBody MetodoDePago datos){
        return servicio.guardar(datos);
    }

    @GetMapping
    public List<MetodoDePago> listar(){
        return servicio.listar();
    }
}