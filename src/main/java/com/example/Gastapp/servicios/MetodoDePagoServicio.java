package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gastapp.modelos.MetodoDePago;
import com.example.Gastapp.repositorios.IMetodoDePagoRepositorio;

@Service
public class MetodoDePagoServicio {

    @Autowired
    private IMetodoDePagoRepositorio repositorio;

    public MetodoDePago guardar(MetodoDePago datos){
        return repositorio.save(datos);
    }

    public List<MetodoDePago> listar(){
        return repositorio.findAll();
    }
}