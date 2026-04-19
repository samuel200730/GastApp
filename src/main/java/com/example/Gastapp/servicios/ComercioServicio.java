package com.example.Gastapp.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.repositorios.IComercioRepositorio;
import com.example.Gastapp.repositorios.IUsuarioRepositorio;

@Service
public class ComercioServicio {
    @Autowired
    private IComercioRepositorio repositorio;
    
}
