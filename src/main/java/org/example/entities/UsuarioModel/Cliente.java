package org.example.entities.UsuarioModel;

public class Cliente extends Usuario {
    private String nomeCompleto;
    private int cpf;
    private String telefone;
    private String empresa;
    private int cnpj;
    private String cargo;
    private String segmento;
    private String tamanhoEmpresa;
    private String pais;
    private String emailCorporativo;
    private String perguntasOuComentarios;


    public Cliente() {}


    public Cliente(int id, String nomeUsuario, String senha, String nomeCompleto, int cpf, String telefone, String empresa, int cnpj, String cargo, String segmento, String tamanhoEmpresa, String pais, String emailCorporativo) {
        super(id, nomeUsuario, senha);
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
        this.emailCorporativo = emailCorporativo;
    }

    public Cliente(int id, String nomeUsuario, String senha, String nomeCompleto, String telefone, String empresa, String cargo, String segmento, String tamanhoEmpresa, String pais, String emailCorporativo, String perguntasOuComentarios) {
        super(id, nomeUsuario, senha);
        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.empresa = empresa;
        this.cargo = cargo;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
        this.emailCorporativo = emailCorporativo;
        this.perguntasOuComentarios = perguntasOuComentarios;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
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

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public String getPerguntasOuComentarios() {
        return perguntasOuComentarios;
    }

    public void setPerguntasOuComentarios(String perguntasOuComentarios) {
        this.perguntasOuComentarios = perguntasOuComentarios;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getCnpj() {
        return cnpj;
    }

    public void setCnpj(int cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return   nomeCompleto + " " +
                cargo +
                " da empresa " + empresa +
                " na sede do(a) " + pais;
    }



}

