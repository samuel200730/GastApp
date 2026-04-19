package com.example.Gastapp.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.Usuario;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario,Integer> {

    //Espacio para crear consultas personalizadas
    List<Usuario> findByNombres(String nombres);
    //buscar por documento (1)
    //Optional<Usuario> findByDocumento(String documento);

    //buscar por nombres que contengan nnn (lista)
    List<Usuario> findByNombresContaining(String nombres);

    //buscar por edad (lista)
    List<Usuario> findByEdad(Integer edad);

    

    

}
