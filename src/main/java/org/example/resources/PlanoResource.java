package org.example.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entities.ServicoModel.Plano;
import org.example.repositories.PlanosRepository;
import org.example.service.PlanoService;

import java.util.List;
@Path("plano")
public class PlanoResource {

    public PlanosRepository planoRepository;
    public PlanoService planoService;

    public PlanoResource(){
        planoRepository = new PlanosRepository();
        planoService = new PlanoService();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Plano> readAll(){
        return planoRepository.readAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        var plano = planoRepository.read(id);
        return plano.isPresent() ?
                Response.ok(plano.get()).build() :
                Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Plano plano){
        try{
            planoService.create(plano);
            return Response.status(Response.Status.CREATED).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Plano plano){
        try{
            planoService.update(id, plano);
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
            planoService.delete(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        catch(IllegalArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
