package org.example.entities.ServicoModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.Map;

public class Plano extends _BaseEntity {

    private String nomePlano;
    private String descricaoPlano;
    private String recursosPlano;
    private float precoPlano;
    private String tipo;


    public Plano() {}

    public Plano(String nomePlano, String descricaoPlano, String tipo) {
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.tipo = tipo;
    }

    public Plano(String nomePlano, String descricaoPlano, String recursosPlano, String tipo) {
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
        this.tipo = tipo;
    }

    public Plano(String nomePlano, String descricaoPlano, float precoPlano, String tipo) {
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.precoPlano = precoPlano;
        this.tipo = tipo;
    }

    public Plano(String nomePlano, String descricaoPlano, String recursosPlano, float precoPlano, String tipo) {
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
        this.precoPlano = precoPlano;
        this.tipo = tipo;
    }



    public Plano(int id, String nomePlano, String descricaoPlano, String tipo) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.tipo = tipo;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, String recursosPlano, String tipo) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
        this.tipo = tipo;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, float precoPlano, String tipo) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.precoPlano = precoPlano;
        this.tipo = tipo;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, String recursosPlano, float precoPlano, String tipo) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
        this.precoPlano = precoPlano;
        this.tipo = tipo;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public String getDescricaoPlano() {
        return descricaoPlano;
    }

    public void setDescricaoPlano(String descricaoPlano) {
        this.descricaoPlano = descricaoPlano;
    }

    public String getRecursosPlano() {
        return recursosPlano;
    }

    public void setRecursosPlano(String recursosPlano) {
        this.recursosPlano = recursosPlano;
    }


    public float getPrecoPlano() {
        return precoPlano;
    }

    public void setPrecoPlano(float precoPlano) {
        this.precoPlano = precoPlano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return  nomePlano + "(" + super.getId() +")" +
                ", preco:" + precoPlano +
                ", descricao: " + descricaoPlano +
                ", recursos:" + recursosPlano +
                "\r\n";
    }

    public Map<Boolean, ArrayList<String>> validate() {

        var errors = new ArrayList<String>();
        if (nomePlano == null || nomePlano.isBlank())
            errors.add("Nome do plano não pode ser vazio");

        if (descricaoPlano == null || descricaoPlano.isBlank())
            errors.add("Descrição do plano não pode ser vazia");

        if (recursosPlano == null || recursosPlano.isBlank())
            errors.add("Os recursos do plano não podem estar em branco");

        if (precoPlano < 0)
            errors.add("Preço do plano não pode ser menor que zero");

        if (tipo == null || tipo.isBlank())
            errors.add("O tipo do plano não pode ser vazio");
        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
