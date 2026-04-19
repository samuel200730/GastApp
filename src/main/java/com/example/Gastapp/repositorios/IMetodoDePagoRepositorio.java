package com.example.Gastapp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.MetodoDePago;

@Repository
public interface IMetodoDePagoRepositorio extends JpaRepository<MetodoDePago,Integer> {

    //Espacio para crear consultas personalizadas
    

    

}