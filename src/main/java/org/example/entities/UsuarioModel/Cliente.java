package org.example.entities.UsuarioModel;

import java.util.ArrayList;
import java.util.Map;

public class Cliente extends Usuario {
    private long cpf;
    private String telefone;
    private String cargo;
    private Empresa empresa;
    private String perguntasOuComentarios;

    public Cliente() {
    }

    public Cliente(String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String cargo, Empresa empresa, String perguntasOuComentarios) {
        super(nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.cargo = cargo;
        this.empresa = empresa;
        this.perguntasOuComentarios = perguntasOuComentarios;
    }

    public Cliente(String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String cargo, Empresa empresa) {
        super(nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.cargo = cargo;
        this.empresa = empresa;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getPerguntasOuComentarios() {
        return perguntasOuComentarios;
    }

    public void setPerguntasOuComentarios(String perguntasOuComentarios) {
        this.perguntasOuComentarios = perguntasOuComentarios;
    }

    @Override
    public String toString() {
        return   super.getNomeCompleto() + " " +
                cargo +
                " da empresa " + empresa +
                " na sede do(a) " + empresa.getPais();
    }


    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();

        if (!Long.toString(cpf).matches("\\d{11}"))
            errors.add("O CPF deve conter 11 caracteres");

        if (telefone == null || telefone.isBlank() || !telefone.replaceAll("[.-]", "").matches("\\d{11}"))
            errors.add("O telefone deve ser composto por 11 números, incluindo o ddd");

        if (cargo == null || cargo.isBlank())
            errors.add("Cargo não pode ser vazio");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }

}

