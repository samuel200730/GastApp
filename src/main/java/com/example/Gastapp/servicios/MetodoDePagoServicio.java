package com.example.Gastapp.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.repositorios.IMetodoDePagoRepositorio;
import com.example.Gastapp.repositorios.IUsuarioRepositorio;

@Service
public class MetodoDePagoServicio {
    @Autowired
    private IMetodoDePagoRepositorio repositorio;
    
}
