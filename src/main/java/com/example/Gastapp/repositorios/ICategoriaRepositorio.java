package com.example.Gastapp.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.Categoria;

@Repository
public interface ICategoriaRepositorio extends JpaRepository<Categoria, Long> {

    // Espacio para consultas personalizadas

}