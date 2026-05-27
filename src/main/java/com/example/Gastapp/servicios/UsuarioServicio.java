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

    // GUARDAR USUARIO
    public Usuario guardar_usuario(Usuario datosUsuario){

        if(datosUsuario.getNombre() == null || datosUsuario.getNombre().isBlank()){

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre del usuario es obligatorio"
            );
        }

        if(datosUsuario.getDocumento() == null || datosUsuario.getDocumento().length() < 5){

            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El documento es invalido"
            );
        }

        return repositorio.save(datosUsuario);
    }

    // LISTAR USUARIOS
    public List<Usuario> listar_usuarios(){

        return repositorio.findAll();
    }

    // BUSCAR USUARIO POR ID
    public Usuario buscarPorId(Long id){

        return repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuario no encontrado"
                ));
    }

    // ACTUALIZAR USUARIO
    public Usuario actualizar(Long id, Usuario datos){

        Usuario usuarioBuscado = buscarPorId(id);

        usuarioBuscado.setNombre(datos.getNombre());
        usuarioBuscado.setTipodoc(datos.getTipodoc());
        usuarioBuscado.setDocumento(datos.getDocumento());
        usuarioBuscado.setEdad(datos.getEdad());
        usuarioBuscado.setCorreoElectronico(datos.getCorreoElectronico());
        usuarioBuscado.setTelefono(datos.getTelefono());
        usuarioBuscado.setDireccion(datos.getDireccion());
        usuarioBuscado.setEstadoCuenta(datos.getEstadoCuenta());
        usuarioBuscado.setFechaRegistro(datos.getFechaRegistro());

        return repositorio.save(usuarioBuscado);
    }

    // ELIMINAR USUARIO
  public void eliminar(Long id){
    if (!repositorio.existsById(id)) return;
    repositorio.deleteById(id);
}
}