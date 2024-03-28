package org.example.entities.UsuarioModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

public class Usuario extends _BaseEntity{
    private String nomeUsuario;
    private String senha;


    public Usuario () {}

    public Usuario(int id, String nomeUsuario, String senha) {
        super(id);
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public Usuario(String nomeUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("nomeUsuario='" + nomeUsuario + "'")
                .add("senha='" + senha + "'")
                .toString();
    }

    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();
        if (nomeUsuario == null || nomeUsuario.isBlank())
            errors.add("Nome de usuário não pode ser vazio");

        if (senha == null || senha.isBlank())
            errors.add("Senha não pode ser vazia");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
