package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.modelos.Comercio;
import com.example.Gastapp.repositorios.IComercioRepositorio;

@Service
public class ComercioServicio {

    @Autowired
    private IComercioRepositorio repositorio;

    public Comercio guardar(Comercio datos){
        return repositorio.save(datos);
    }

    public List<Comercio> listar(){
        return repositorio.findAll();
    }
}
