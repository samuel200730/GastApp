package com.example.Gastapp.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gastapp.modelos.Usuario;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // ✅ Buscar por nombre exacto
    List<Usuario> findByNombre(String nombre);

    // ✅ Buscar por nombre que contenga texto
    List<Usuario> findByNombreContaining(String nombre);

    // ✅ Buscar por edad
    List<Usuario> findByEdad(Integer edad);

    // (Opcional - recomendado)
    // Buscar por documento
    // Optional<Usuario> findByDocumento(String documento);
}
