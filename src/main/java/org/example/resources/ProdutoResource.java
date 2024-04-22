package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.ServicoModel.Produto;
import org.example.repositories.ProdutosRepository;
import org.example.services.ProdutoService;

import java.util.List;

@Path("produto")
public class ProdutoResource {

    public ProdutosRepository produtoRepository;
    public ProdutoService produtoService;

    public ProdutoResource(){
        produtoRepository = new ProdutosRepository();
        produtoService = new ProdutoService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> readAll(){
       return produtoRepository.readAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        var produto = produtoRepository.read(id);
        return produto.isPresent() ?
                Response.ok(produto.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    //TESTAR GETALLBYPLANO
    @GET
    @Path("plano/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByPlano(@PathParam("id") int idPlano){
        return Response.ok(produtoRepository.readAllByPlano(idPlano)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Produto produto){
        try{
            produtoService.create(produto);
            return Response.status(Response.Status.CREATED).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Produto produto){
        try{
            produtoService.update(id, produto);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id){
        try{
            produtoService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
