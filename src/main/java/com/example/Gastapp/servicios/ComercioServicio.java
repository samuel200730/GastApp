package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Gastapp.modelos.Comercio;
import com.example.Gastapp.repositorios.IComercioRepositorio;

@Service
public class ComercioServicio {

    @Autowired
    private IComercioRepositorio repositorio;

    // GUARDAR
    public Comercio guardar(Comercio datos){

        return repositorio.save(datos);
    }

    // LISTAR
    public List<Comercio> listar(){

        return repositorio.findAll();
    }

    // BUSCAR POR ID
    public Comercio buscarPorId(Long id){

        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Comercio no encontrado"
                ));
    }

    // ACTUALIZAR
    public Comercio actualizar(Long id, Comercio datos){

        Comercio comercioBuscado = buscarPorId(id);

        comercioBuscado.setNit(datos.getNit());
        comercioBuscado.setNombre(datos.getNombre());
        comercioBuscado.setActividad(datos.getActividad());
        comercioBuscado.setContacto(datos.getContacto());
        comercioBuscado.setDireccion(datos.getDireccion());
        comercioBuscado.setCiudad(datos.getCiudad());
        comercioBuscado.setTelefono(datos.getTelefono());
        comercioBuscado.setEstadoComercio(datos.getEstadoComercio());
        comercioBuscado.setTipoEstablecimiento(datos.getTipoEstablecimiento());

        return repositorio.save(comercioBuscado);
    }

    // ELIMINAR
    public void eliminar(Long id){

        Comercio comercioBuscado = buscarPorId(id);

        repositorio.delete(comercioBuscado);
    }
}