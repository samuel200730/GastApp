package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Gastapp.modelos.MetodoDePago;
import com.example.Gastapp.repositorios.IMetodoDePagoRepositorio;

@Service
public class MetodoDePagoServicio {

    @Autowired
    private IMetodoDePagoRepositorio repositorio;

    // GUARDAR
    public MetodoDePago guardar(MetodoDePago datos){

        return repositorio.save(datos);
    }

    // LISTAR
    public List<MetodoDePago> listar(){

        return repositorio.findAll();
    }

    // BUSCAR POR ID
    public MetodoDePago buscarPorId(Long id){

        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Metodo de pago no encontrado"
                ));
    }

    // ACTUALIZAR
    public MetodoDePago actualizar(Long id, MetodoDePago datos){

        MetodoDePago metodoBuscado = buscarPorId(id);

        metodoBuscado.setNombre(datos.getNombre());
        metodoBuscado.setFranquicia(datos.getFranquicia());
        metodoBuscado.setEstado(datos.getEstado());
        metodoBuscado.setDescripcion(datos.getDescripcion());
        metodoBuscado.setCupoDisponible(datos.getCupoDisponible());
        metodoBuscado.setFechaVencimiento(datos.getFechaVencimiento());
        metodoBuscado.setNumeroReferencia(datos.getNumeroReferencia());
        metodoBuscado.setBancoEmisor(datos.getBancoEmisor());
        metodoBuscado.setTipoMetodo(datos.getTipoMetodo());

        return repositorio.save(metodoBuscado);
    }

    // ELIMINAR
    public void eliminar(Long id){

        MetodoDePago metodoBuscado = buscarPorId(id);

        repositorio.delete(metodoBuscado);
    }
}