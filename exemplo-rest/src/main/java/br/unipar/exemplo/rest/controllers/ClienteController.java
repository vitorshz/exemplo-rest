package br.unipar.exemplo.rest.controllers;

import br.unipar.exemplo.rest.dto.ClienteFindAllResponse;
import br.unipar.exemplo.rest.dto.ClienteRequest;
import br.unipar.exemplo.rest.dto.ExceptionResponse;
import br.unipar.exemplo.rest.exceptions.ObjectNaoEncontradoException;
import br.unipar.exemplo.rest.exceptions.ValidacaoException;
import br.unipar.exemplo.rest.models.Cliente;
import br.unipar.exemplo.rest.services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("cliente")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteController {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteController.class.getName());
    
    
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
    
    // Método para inserir um novo cliente
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insert(ClienteRequest cliente,
            @Context HttpServletRequest request) throws ValidacaoException, SQLException {

        try {
            // Inicializa o serviço de cliente
            ClienteService clienteService = new ClienteService();
            // Insere o cliente usando o serviço e converte o objeto de requisição em um objeto de domínio de cliente
            Cliente clienteDomain = clienteService.insert(Cliente.requestToCliente(cliente));
            // Retorna uma resposta 201 (Created) com o URI do novo cliente
            return Response.created(URI.create(
                    request.getRequestURI() + clienteDomain.getId())
            ).build();

        } // Captura exceção SQL
        catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
            // Cria uma resposta de erro 500 (Internal Server Error) com uma mensagem personalizada
            ExceptionResponse response
                    = new ExceptionResponse("Ops, algo ocorreu de errado, tente novamente mais tarde",
                            new Date(),
                            request.getRequestURI(),
                            Response.Status.INTERNAL_SERVER_ERROR.toString());

            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(response).build();
        } // Captura exceção de validação
        catch (ValidacaoException validacaoException) {
            LOGGER.log(Level.INFO, validacaoException.toString());

            // Cria uma resposta de erro 400 (Bad Request) com uma mensagem personalizada
            ExceptionResponse response = new ExceptionResponse("Ops Algo ocorreu de errado",
                    new Date(), request.getRequestURI(), Response.Status.BAD_REQUEST.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } // Captura outras exceções
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
            // Cria uma resposta de erro 500 (Internal Server Error) com uma mensagem personalizada
            ExceptionResponse response
                    = new ExceptionResponse("Erro desconhecido entre em contato com o fornecedor",
                            new Date(),
                            request.getRequestURI(),
                            Response.Status.INTERNAL_SERVER_ERROR.toString());

            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(response).build();
        }
    }

    // Método para encontrar um cliente por ID
    @GET
    @Path("{id}") // cliente/12121
    public Response findById(@PathParam("id") int id, @Context HttpServletRequest request) {
        try {
            // Inicializa o serviço de cliente
            ClienteService clienteService = new ClienteService();
            // Encontra o cliente pelo ID usando o serviço
            Cliente cliente = clienteService.findById(id);
            // Retorna uma resposta 200 (OK) com os detalhes do cliente
            return Response.ok(cliente).build();
        } // Captura exceção SQL
        catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
            // Cria uma resposta de erro 500 (Internal Server Error) com uma mensagem personalizada
            ExceptionResponse response
                    = new ExceptionResponse("Erro desconhecido entre em contato com o fornecedor",
                            new Date(),
                            request.getRequestURI(),
                            Response.Status.INTERNAL_SERVER_ERROR.toString());

            return Response.
                    status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(response).build();
        } // Captura exceção de validação
        catch (ValidacaoException validacaoException) {
            LOGGER.log(Level.INFO, validacaoException.toString());

            // Cria uma resposta de erro 400 (Bad Request) com uma mensagem personalizada
            ExceptionResponse response = new ExceptionResponse("Ops Algo ocorreu de errado",
                    new Date(), request.getRequestURI(), Response.Status.BAD_REQUEST.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } // Captura exceção quando o objeto não é encontrado
        catch (ObjectNaoEncontradoException ex) {
            LOGGER.log(Level.INFO, ex.toString());

            // Cria uma resposta de erro 400 (Bad Request) com uma mensagem personalizada
            ExceptionResponse response = new ExceptionResponse("Ops Algo ocorreu de errado",
                    new Date(), request.getRequestURI(), Response.Status.BAD_REQUEST.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } // Captura outras exceções
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.toString());

            // Cria uma resposta de erro 400 (Bad Request) com uma mensagem personalizada
            ExceptionResponse response = new ExceptionResponse("Ops Algo ocorreu de errado",
                    new Date(), request.getRequestURI(), Response.Status.BAD_REQUEST.toString());

            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        }
    }

    // Método para encontrar todos os clientes
    @GET // cliente?nome=vitor
    public Response findAll(@QueryParam("nome") String nome, @Context HttpServletRequest request) throws SQLException {
        // Inicializa o serviço de cliente
        ClienteService clienteService = new ClienteService();
        // Obtém todos os clientes usando o serviço
        ArrayList<Cliente> clientes = clienteService.listAll();

        // Retorna uma resposta 200 (OK) com a lista de clientes
        return Response.ok(clientes).build();

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
