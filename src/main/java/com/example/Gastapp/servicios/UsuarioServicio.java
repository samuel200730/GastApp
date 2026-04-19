package com.example.Gastapp.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Gastapp.modelos.Usuario;
import com.example.Gastapp.repositorios.IUsuarioRepositorio;

@Service
public class UsuarioServicio {
    @Autowired
    private IUsuarioRepositorio repositorio;

     public Usuario guardar_usuario(Usuario datosUsuario){

        //validar la operacion que me estan pidiendo hacer
        if(datosUsuario.getNombre()==null || datosUsuario.getNombre().isBlank() || datosUsuario.getNombre().isEmpty()){

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre del usuario es obligatorio, revisa por favor"
            );

        }

        if(datosUsuario.getDocumento().length()<5){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "el documento es invalido"
            );
        }

        //Despues de las validaciones intento guardar los datos que me enviaron
        return repositorio.save(datosUsuario);
        
    }

    //servicio para listar todos los usuarios en BD

    public List<Usuario> listar_usuarios(){
        return repositorio.findAll();
    }
    
}
