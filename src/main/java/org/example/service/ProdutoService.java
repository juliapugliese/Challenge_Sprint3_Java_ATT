package org.example.service;

import org.example.entities.ServicoModel.Produto;
import org.example.repositories.ProdutosRepository;

public class ProdutoService {

    private ProdutosRepository produtoRepository;

    public ProdutoService(){
        produtoRepository = new ProdutosRepository();
    }

    public void create(Produto produto){
        var validation = produto.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            produtoRepository.create(produto);
    }

    public void update(int id, Produto produto){
        var validation = produto.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            produtoRepository.update(id, produto);
    }


    public void delete(Produto produto){
        var validation = produto.validate();

        if(validation.containsKey(false))
            throw new IllegalArgumentException(validation.get(false).toString());
        else
            produtoRepository.delete(produto.getId());
    }
}
