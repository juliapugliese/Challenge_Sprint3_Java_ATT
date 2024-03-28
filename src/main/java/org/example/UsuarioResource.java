package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.UsuarioModel.Usuario;
import org.example.repositories.UsuariosRepository;
import org.example.service.UsuarioService;

import java.util.List;

@Path("usuario")

public class UsuarioResource {

    public UsuariosRepository usuariosRepository;
    public UsuarioService usuarioService;

    public UsuarioResource(){
        usuariosRepository = new UsuariosRepository();
        usuarioService = new UsuarioService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> readAll(){
        return usuariosRepository.readAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        var produto = usuariosRepository.read(id);
        return produto.isPresent() ?
                Response.ok(produto.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Usuario usuario){
        try{
            usuarioService.create(usuario);
            return Response.status(Response.Status.CREATED).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Usuario usuario){
        try{
            usuarioService.update(id, usuario);
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
            usuarioService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
