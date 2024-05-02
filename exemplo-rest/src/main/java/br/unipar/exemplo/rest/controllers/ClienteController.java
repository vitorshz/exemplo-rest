package br.unipar.exemplo.rest.controllers;

import br.unipar.exemplo.rest.dto.ClienteFindAllResponse;
import br.unipar.exemplo.rest.dto.ClienteRequest;
import br.unipar.exemplo.rest.models.Cliente;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
@Path("cliente")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteController {
    
//    @GET
//    @Path(value = "/ping")
//    public Response ping(){
//        return Response
//                .ok("pong")
//                .build();
//    }
//    
//    @GET
//    @Path("/pong")
//    public Response pong(){
//        return Response
//                .ok("ping")
//                .build();
//    }
//    C @Post
//    R @GET
//    U @Put
//    D @Delete
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(ClienteRequest cliente){
        return Response.ok(cliente).build();
    }
    
    @GET
    @Path("{id}") // cliente/12121
    public Cliente findById(@PathParam("id") int id){
        return new Cliente(1,"Vitor","12121212121");
    }
    
    @GET // cliente?nome=vitor
    public List<ClienteFindAllResponse> findAll(@QueryParam("nome") String nome){
        ArrayList<ClienteFindAllResponse> listaClientes = new ArrayList<>();
        listaClientes.add(new ClienteFindAllResponse(1,"vitin"));
        listaClientes.add(new ClienteFindAllResponse(2,"vitin2"));
        listaClientes.add(new ClienteFindAllResponse(3, "vitin3"));
        
        return listaClientes;
    }
    
    @PUT
    @Path("{id}")
        @Consumes(MediaType.APPLICATION_JSON)

    public Cliente edit(@PathParam("id") int id,Cliente cliente){
        return cliente;
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("{id}") int id){
        
    }
    
    
    
}
