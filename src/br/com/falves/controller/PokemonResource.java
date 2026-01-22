/**
 * @author falvesmac
 */

package br.com.falves.controller;

import br.com.falves.domain.Pokemon;
import br.com.falves.service.PokemonService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pokemons")
public class PokemonResource {
    private PokemonService pokemonService = new PokemonService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos(){
        try {
            return Response.ok(pokemonService.listaPokemons()).build();
        } catch (Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorId(@PathParam("numero") String numeroStr){
        try {
            Pokemon p = pokemonService.consultarPokemon(numeroStr);
            return Response.ok(p).build();
        } catch (IllegalArgumentException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrar(@Valid Pokemon pokemon) {
        try {
            pokemonService.cadastrarPokemon(pokemon);
            return Response.status(Response.Status.CREATED).entity(pokemon).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}