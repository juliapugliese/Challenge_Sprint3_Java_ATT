package org.example.entities.ServicoModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.Map;

public class Plano extends _BaseEntity {

    private String nomePlano;
    private String descricaoPlano;
    private String recursosPlano;
    private float precoPlano;


    public Plano() {}

    public Plano(int id, String nomePlano, String descricaoPlano) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, String recursosPlano) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, float precoPlano) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.precoPlano = precoPlano;
    }

    public Plano(int id, String nomePlano, String descricaoPlano, String recursosPlano, float precoPlano) {
        super(id);
        this.nomePlano = nomePlano;
        this.descricaoPlano = descricaoPlano;
        this.recursosPlano = recursosPlano;
        this.precoPlano = precoPlano;
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

    @Override
    public String toString() {
        return  nomePlano + "(" + super.getId() +")" +
                ", preco:" + precoPlano +
                ", descricao: " + descricaoPlano +
                ", recursos:" + recursosPlano +
                "\r\n";
    }

    public Map<Boolean, ArrayList<String>> validate() {
        // trabalhar com uma lista de erros ao invés de fazer o throw de exceção direto no primeiro erro
        // isso permite que o usuário veja todos os erros de uma vez
        // e não apenas o primeiro erro que ocorreu
        // assim ele pode corrigir todos os erros de uma vez
        var errors = new ArrayList<String>();
        if (nomePlano == null || nomePlano.isBlank())
            errors.add("Nome do plano não pode ser vazio");

        if (descricaoPlano == null || descricaoPlano.isBlank())
            errors.add("Descrição do plano não pode ser vazia");

        if (precoPlano < 0)
            errors.add("Preço do plano não pode ser menor que zero");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
