package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.modelos.Categoria;
import com.example.Gastapp.repositorios.ICategoriaRepositorio;

@Service
public class CategoriaServicio {

    @Autowired
    private ICategoriaRepositorio repositorio;

    public Categoria guardar(Categoria datos){
        return repositorio.save(datos);
    }

    public List<Categoria> listar(){
        return repositorio.findAll();
    }
}