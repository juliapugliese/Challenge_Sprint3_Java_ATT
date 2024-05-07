package org.example.entities.UsuarioModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

public class Usuario extends _BaseEntity{
    private String nomeUsuario;
    private String senha;
    private String nomeCompleto;
    private String email;


    public Usuario () {}

    public Usuario(int id, String nomeUsuario, String senha, String nomeCompleto, String email) {
        super(id);
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
    }

    public Usuario(String nomeUsuario, String senha, String nomeCompleto, String email) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.email = email;
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

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("nomeUsuario='" + nomeUsuario + "'")
                .add("senha='" + senha + "'")
                .add("nomeCompleto='" + nomeCompleto + "'")
                .add("email='" + email + "'")
                .toString();
    }

    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();
        if (nomeUsuario == null || nomeUsuario.isBlank())
            errors.add("Nome de usuário não pode ser vazio");

        if (senha == null || senha.isBlank())
            errors.add("Senha não pode ser vazia");

        if (nomeCompleto == null || nomeCompleto.isBlank())
            errors.add("Nome de Administrador não pode ser vazio");

        if (email == null || email.isBlank())
            errors.add("Email não pode ser vazio");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
