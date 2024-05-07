package org.example.entities.UsuarioModel;

import java.util.ArrayList;
import java.util.Map;

public class Cliente extends Usuario {
    private long cpf;
    private String telefone;
    private String empresa;
    private long cnpj;
    private String cargo;
    private String segmento;
    private String tamanhoEmpresa;
    private String pais;
    private String perguntasOuComentarios;

    public Cliente() {
    }

    public Cliente(String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String empresa, long cnpj, String cargo, String segmento, String tamanhoEmpresa, String pais) {
        super(nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
    }

    public Cliente(int id, String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String empresa, long cnpj, String cargo, String segmento, String tamanhoEmpresa, String pais, String emailCorporativo) {
        super(id, nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;

    }

    public Cliente(String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String empresa, long cnpj, String cargo, String segmento, String tamanhoEmpresa, String pais, String emailCorporativo, String perguntasOuComentarios) {
        super(nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
        this.perguntasOuComentarios = perguntasOuComentarios;
    }

    public Cliente(int id, String nomeUsuario, String senha, String nomeCompleto, String email, long cpf, String telefone, String empresa, long cnpj, String cargo, String segmento, String tamanhoEmpresa, String pais, String emailCorporativo, String perguntasOuComentarios) {
        super(id, nomeUsuario, senha, nomeCompleto, email);
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
        this.perguntasOuComentarios = perguntasOuComentarios;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getTamanhoEmpresa() {
        return tamanhoEmpresa;
    }

    public void setTamanhoEmpresa(String tamanhoEmpresa) {
        this.tamanhoEmpresa = tamanhoEmpresa;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
                " na sede do(a) " + pais;
    }


    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();

        if (!Long.toString(cpf).matches("\\d{11}"))
            errors.add("O CPF deve conter 11 caracteres");

        if (telefone == null || telefone.isBlank() || !telefone.replaceAll("[.-]", "").matches("\\d{11}"))
            errors.add("O telefone deve ser composto por 11 números, incluindo o ddd");


        if (empresa == null || empresa.isBlank())
            errors.add("Nome da empresa não pode ser vazio");

        if (!Long.toString(cnpj).matches("\\d{14}"))
            errors.add("O CNPJ deve conter 11 caracteres");

        if (cargo == null || cargo.isBlank())
            errors.add("Cargo não pode ser vazio");

        if (segmento == null || segmento.isBlank())
            errors.add("Segmento não pode ser vazio");

        if (tamanhoEmpresa == null || tamanhoEmpresa.isBlank())
            errors.add("Tamanho da empresa não pode ser vazio");

        if (pais == null || pais.isBlank())
            errors.add("País não pode ser vazio");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }

}

