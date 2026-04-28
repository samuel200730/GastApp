package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Gasto;
import com.example.Gastapp.servicios.GastoServicio;

@RestController
@RequestMapping("/api/gastos")
public class GastoControlador {

    @Autowired
    private GastoServicio servicio;

    @PostMapping
    public Gasto guardar(@RequestBody Gasto datos){
        return servicio.guardar(datos);
    }

    @GetMapping
    public List<Gasto> listar(){
        return servicio.listar();
    }
}