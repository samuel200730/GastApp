package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.modelos.Gasto;
import com.example.Gastapp.repositorios.IGastoRepositorio;

@Service
public class GastoServicio {

    @Autowired
    private IGastoRepositorio repositorio;

    public Gasto guardar(Gasto datos){
        return repositorio.save(datos);
    }

    public List<Gasto> listar(){
        return repositorio.findAll();
    }
}
