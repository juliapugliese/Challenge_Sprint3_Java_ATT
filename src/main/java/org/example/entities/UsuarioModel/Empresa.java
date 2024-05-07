package org.example.entities.UsuarioModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.Map;

public class Empresa extends _BaseEntity {
    private String nomeEmpresa;
    private long cnpj;
    private String segmento;
    private String tamanhoEmpresa;
    private String pais;


    public Empresa() {
    }

    public Empresa(String nomeEmpresa, long cnpj, String segmento, String tamanhoEmpresa, String pais) {
        this.nomeEmpresa = nomeEmpresa;
        this.cnpj = cnpj;
        this.segmento = segmento;
        this.tamanhoEmpresa = tamanhoEmpresa;
        this.pais = pais;
    }


    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
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


    public Map<Boolean, ArrayList<String>> validate() {
        var errors = new ArrayList<String>();

        if (nomeEmpresa == null || nomeEmpresa.isBlank())
            errors.add("Nome da empresa não pode ser vazio");

        if (!Long.toString(cnpj).matches("\\d{14}"))
            errors.add("O CNPJ deve conter 11 caracteres");

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
