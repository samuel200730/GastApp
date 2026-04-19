package com.example.Gastapp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.Comercio;

@Repository
public interface IComercioRepositorio extends JpaRepository<Comercio,Integer> {

    //Espacio para crear consultas personalizadas
    

    

}