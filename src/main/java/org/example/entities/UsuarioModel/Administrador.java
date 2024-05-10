package org.example.entities.UsuarioModel;

public class Administrador extends Usuario {
    public Administrador() {
    }

    public Administrador(String nomeUsuario, String senha, String nomeCompleto, String email) {
        super(nomeUsuario, senha, nomeCompleto, email);
    }

    public Administrador(int id, String nomeUsuario, String senha, String nomeCompleto, String email) {
        super(id, nomeUsuario, senha, nomeCompleto, email);
    }

    @Override
    public String toString() {
        return super.getNomeCompleto() + "(" + super.getId() +") " + super.getEmail();
    }

}

