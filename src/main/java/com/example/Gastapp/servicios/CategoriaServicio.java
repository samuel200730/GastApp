package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Gastapp.modelos.Categoria;
import com.example.Gastapp.repositorios.ICategoriaRepositorio;

@Service
public class CategoriaServicio {

    @Autowired
    private ICategoriaRepositorio repositorio;

    // GUARDAR
    public Categoria guardar(Categoria datos){

        return repositorio.save(datos);
    }

    // LISTAR
    public List<Categoria> listar(){

        return repositorio.findAll();
    }

    // BUSCAR POR ID
    public Categoria buscarPorId(Long id){

        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria no encontrada"
                ));
    }

    // ACTUALIZAR
    public Categoria actualizar(Long id, Categoria datos){

        Categoria categoriaBuscada = buscarPorId(id);

        categoriaBuscada.setNombre(datos.getNombre());
        categoriaBuscada.setFechaCreacion(datos.getFechaCreacion());
        categoriaBuscada.setResponsable(datos.getResponsable());
        categoriaBuscada.setJustificacion(datos.getJustificacion());
        categoriaBuscada.setPresupuestoAsignado(datos.getPresupuestoAsignado());
        categoriaBuscada.setColorIdentificador(datos.getColorIdentificador());
        categoriaBuscada.setLimiteMensual(datos.getLimiteMensual());
        categoriaBuscada.setTipoCategoria(datos.getTipoCategoria());
        categoriaBuscada.setNivelImportancia(datos.getNivelImportancia());

        return repositorio.save(categoriaBuscada);
    }

    // ELIMINAR
    public void eliminar(Long id){

        Categoria categoriaBuscada = buscarPorId(id);

        repositorio.delete(categoriaBuscada);
    }
}