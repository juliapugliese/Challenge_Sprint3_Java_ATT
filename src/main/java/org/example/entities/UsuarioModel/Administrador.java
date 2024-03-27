package org.example.entities.UsuarioModel;

import java.util.ArrayList;
import java.util.Map;

public class Administrador extends Usuario {
    private String nomeAdm;
    private String email;


    public Administrador() {
    }

    public Administrador(int id, String nomeUsuario, String senha, String nomeAdm, String email) {
        super(id, nomeUsuario, senha);
        this.nomeAdm = nomeAdm;
        this.email = email;
    }

    public String getNomeAdm() {
        return nomeAdm;
    }

    public void setNomeAdm(String nomeAdm) {
        this.nomeAdm = nomeAdm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return   nomeAdm + "(" + super.getId() +") " +
                email;
    }

    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();

        if (nomeAdm == null || nomeAdm.isBlank())
            errors.add("Nome de Administrador não pode ser vazio");

        if (email == null || email.isBlank())
            errors.add("Email não pode ser vazio");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }

}

