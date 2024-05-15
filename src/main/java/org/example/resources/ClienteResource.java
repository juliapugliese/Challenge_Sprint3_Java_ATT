package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.UsuarioModel.Cliente;
import org.example.entities.UsuarioModel.Usuario;
import org.example.repositories.UsuariosRepository;
import org.example.services.UsuarioService;

import java.util.List;

@Path("cliente")

public class ClienteResource {

    public UsuariosRepository usuariosRepository;
    public UsuarioService usuarioService;

    public ClienteResource(){
        usuariosRepository = new UsuariosRepository();
        usuarioService = new UsuarioService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> readAll(){
        return usuariosRepository.readAllCLT();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        var cliente = usuariosRepository.readCLT(id);
        return cliente.isPresent() ?
                Response.ok(cliente.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Cliente usuario){
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
    public Response update(@PathParam("id") int id, Cliente usuario){
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
