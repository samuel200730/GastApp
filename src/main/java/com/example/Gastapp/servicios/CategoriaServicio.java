package com.example.Gastapp.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.repositorios.ICategoriaRepositorio;
import com.example.Gastapp.repositorios.IUsuarioRepositorio;

@Service
public class CategoriaServicio {
    @Autowired
    private ICategoriaRepositorio repositorio;
    
}
