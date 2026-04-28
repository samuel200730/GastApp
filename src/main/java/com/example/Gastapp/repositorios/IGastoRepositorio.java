package com.example.Gastapp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.Gasto;

@Repository
public interface IGastoRepositorio extends JpaRepository<Gasto, Long> {

    // Espacio para consultas personalizadas

}