package org.example.service;


import org.example.entities.UsuarioModel.Usuario;
import org.example.repositories.UsuariosRepository;

public class UsuarioService {
    private final UsuariosRepository usuariosRepository;

    public UsuarioService(){
        usuariosRepository = new UsuariosRepository();
    }

    public void create(Usuario usuario){
        var validation = usuario.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            usuariosRepository.create(usuario);
    }


    public void update(int id, Usuario usuario){
        var validation = usuario.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            usuariosRepository.update(id, usuario);
    }

    public void delete(int id){
        usuariosRepository.delete(id);
    }
//    public void delete(Usuario usuario){
//        var validation = usuario.validate();
//
//        if(validation.containsKey(false))
//            throw new IllegalArgumentException(validation.get(false).toString());
//        else
//            usuariosRepository.delete(usuario.getId());
//    }
}
