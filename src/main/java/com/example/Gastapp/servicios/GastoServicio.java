package com.example.Gastapp.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.repositorios.IGastoRepositorio;
import com.example.Gastapp.repositorios.IUsuarioRepositorio;

@Service
public class GastoServicio {
    @Autowired
    private IGastoRepositorio repositorio;
    
}
