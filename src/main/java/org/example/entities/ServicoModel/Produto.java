package org.example.entities.ServicoModel;

import org.example.entities._BaseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Produto extends _BaseEntity {
    private String nomeProduto;
    private String descricaoProduto;
    private List<Plano> planoPagamento = new ArrayList<>();
    private Plano sucessPlans;

    public Produto () {}


    public Produto(String nomeProduto, String descricaoProduto, List<Plano> planoPagamento) {
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.planoPagamento = planoPagamento;
    }

    public Produto(String nomeProduto, String descricaoProduto, List<Plano> planoPagamento, Plano sucessPlans) {
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.planoPagamento = planoPagamento;
        this.sucessPlans = sucessPlans;
    }



    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }


    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public List<Plano> getPlanoPagamento() {
        return planoPagamento;
    }

    public void setPlanoPagamento(List<Plano> planoPagamento) {
        this.planoPagamento = planoPagamento;
    }

    public Plano getSucessPlans() {
        return sucessPlans;
    }

    public void setSucessPlans(Plano sucessPlans) {
        this.sucessPlans = sucessPlans;
    }

    @Override
    public String toString() {
        return nomeProduto + "(" + super.getId() + ")" +
                "\r\nDescrição do Produto: " + descricaoProduto +
                "\r\nPlanos de Pagamento: " + planoPagamento;
    }


    public Map<Boolean, ArrayList<String>> validate() {

        var errors = new ArrayList<String>();
        if (nomeProduto == null || nomeProduto.isBlank())
            errors.add("Nome do produto não pode ser vazio");

        if (descricaoProduto == null || descricaoProduto.isBlank())
            errors.add("Descrição do produto não pode ser vazio");

        if (planoPagamento == null || planoPagamento.isEmpty())
            errors.add("O produto deve conter um plano de pagamento");

        return !errors.isEmpty() ?
                Map.of(false, errors) :
                Map.of(true, errors);
    }
}
