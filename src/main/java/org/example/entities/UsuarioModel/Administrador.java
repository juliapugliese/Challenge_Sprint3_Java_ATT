package org.example.entities.UsuarioModel;

public class Administrador extends Usuario {
    public Administrador() {
    }
    @Override
    public String toString() {
        return super.getNomeCompleto() + "(" + super.getId() +") " + super.getEmail();
    }

}

