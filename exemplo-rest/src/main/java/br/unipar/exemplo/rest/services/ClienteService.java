package br.unipar.exemplo.rest.services;

import br.unipar.exemplo.rest.exceptions.ObjectNaoEncontradoException;
import br.unipar.exemplo.rest.exceptions.ValidacaoException;
import br.unipar.exemplo.rest.models.Cliente;
import br.unipar.exemplo.rest.repositories.ClienteRepository;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;


public class ClienteService {
    public Cliente insert(Cliente cliente) throws ValidacaoException, SQLException{
        if(cliente.getCpf().isEmpty()){
            throw new ValidacaoException("Cpf não pode ser vazio!");
        }
        if(cliente.getCpf().trim().length()!= 11){
            throw new ValidacaoException("Valor de cpf Inválido!");
        }
        if(cliente.getNome().trim().isEmpty()){
            throw new ValidacaoException("Nome não pode ser vazio!");
        }
        
        return new ClienteRepository().insert(cliente);
        
    }
    public Cliente update(Cliente cliente) throws SQLException, ValidacaoException, NamingException{
        if (cliente.getCpf().isEmpty()) {
            throw new ValidacaoException("Cpf não pode ser vazio!");
        }
        if (cliente.getCpf().trim().length() != 11) {
            throw new ValidacaoException("Valor de cpf Inválido!");
        }
        if (cliente.getNome().trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio!");
        }
        return new ClienteRepository().update(cliente);
        
    }
    public void delete(int id) throws
            ValidacaoException,
            SQLException,
            NamingException {

        if (id == 0) {
            throw new ValidacaoException("Informe o Cliente a ser Deletado");
        }

        new ClienteRepository().delete(id);

    }
    public ArrayList<Cliente> listAll() throws SQLException{
        
        return new ClienteRepository().listAll();
    }
    
    public Cliente findById(int id) throws SQLException, ObjectNaoEncontradoException, ValidacaoException{
        if(id==0){
            throw new ValidacaoException("Informe um id válido");
        }
    
       Cliente c = new ClienteRepository().findById(id);
       if(c.getId() == 0 && c.getNome().isBlank()){
           throw new ObjectNaoEncontradoException("Recurso não encontrado");
       }
       return c;
    }
    
    
}
