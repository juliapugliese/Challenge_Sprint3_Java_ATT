package org.example.services;

import org.example.entities.ServicoModel.Produto;
import org.example.repositories.ProdutosRepository;

public class ProdutoService {

    private final ProdutosRepository produtoRepository;

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

    public void delete(int id){
        produtoRepository.delete(id);
    }
//    public void delete(Produto produto){
//        var validation = produto.validate();
//
//        if(validation.containsKey(false))
//            throw new IllegalArgumentException(validation.get(false).toString());
//        else
//            produtoRepository.delete(produto.getId());
//    }
//
//    public void delete(int id){
//        var validation = produtoRepository.read(id).get().validate();
//
//        if(validation.containsKey(false))
//            throw new IllegalArgumentException(validation.get(false).toString());
//        else
//            produtoRepository.delete(id);
//    }
}
