package com.example.Gastapp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Gastapp.modelos.Categoria;
import com.example.Gastapp.servicios.CategoriaServicio;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaControlador {

    @Autowired
    private CategoriaServicio servicio;

    @PostMapping
    public Categoria guardar(@RequestBody Categoria datos){
        return servicio.guardar(datos);
    }

    @GetMapping
    public List<Categoria> listar(){
        return servicio.listar();
    }
}