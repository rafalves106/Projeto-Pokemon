/**
 * @author falvesmac
 */

package br.com.falves.controller;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;
import br.com.falves.service.TreinadorService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/treinadores")
public class TreinadorResouce {
    private TreinadorService treinadorService = new TreinadorService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            return Response.ok(treinadorService.listaTreinadores()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") String id){
        try {
            Treinador t = treinadorService.consultarTreinador(id);
            return Response.ok(t).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(@Valid Treinador treinador) {
        try {
            treinadorService.cadastrarTreinador(treinador);
            return Response.status(Response.Status.CREATED).entity(treinador).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

}