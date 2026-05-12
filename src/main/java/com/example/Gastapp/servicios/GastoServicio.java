package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Gastapp.modelos.Gasto;
import com.example.Gastapp.repositorios.IGastoRepositorio;

@Service
public class GastoServicio {

    @Autowired
    private IGastoRepositorio repositorio;

    // GUARDAR
    public Gasto guardar(Gasto datos){

        return repositorio.save(datos);
    }

    // LISTAR
    public List<Gasto> listar(){

        return repositorio.findAll();
    }

    // BUSCAR POR ID
    public Gasto buscarPorId(Long id){

        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Gasto no encontrado"
                ));
    }

    // ACTUALIZAR
    public Gasto actualizar(Long id, Gasto datos){

        Gasto gastoBuscado = buscarPorId(id);

        gastoBuscado.setDescripcion(datos.getDescripcion());
        gastoBuscado.setFecha(datos.getFecha());
        gastoBuscado.setValor(datos.getValor());
        gastoBuscado.setIcono(datos.getIcono());
        gastoBuscado.setReferenciaTransaccion(datos.getReferenciaTransaccion());
        gastoBuscado.setCanalCompra(datos.getCanalCompra());
        gastoBuscado.setCantidadItems(datos.getCantidadItems());
        gastoBuscado.setEstadoGasto(datos.getEstadoGasto());
        gastoBuscado.setNumeroComprobante(datos.getNumeroComprobante());

        return repositorio.save(gastoBuscado);
    }

    // ELIMINAR
    public void eliminar(Long id){

        Gasto gastoBuscado = buscarPorId(id);

        repositorio.delete(gastoBuscado);
    }
}