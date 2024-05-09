
package br.unipar.exemplo.rest.repositories;

import br.unipar.exemplo.rest.infraestructure.ConnectionFactory;
import br.unipar.exemplo.rest.models.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.naming.NamingException;

public class ClienteRepository {
    
    private static final String INSERT = "INSERT INTO CLIENTE( NOME, CPF) VALUES ( ?, ?)";
    private static final String LISTALL = "SELECT * FROM CLIENTE c ORDER BY c.nome ASC";
    private static final String FINDBYID = "SELECT * FROM CLIENTE c WHERE c.id = ?";
    private static final String DELETE
            = "DELETE FROM CLIENTE WHERE ID = ?";

    private static final String UPDATE
            = "UPDATE CLIENTE SET NOME = ?, CPF = ? WHERE ID = ?";
    
    public Cliente insert(Cliente cliente) throws SQLException{
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            
            conn = new ConnectionFactory().getConnection();

            pstmt = conn.prepareStatement(INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());

            pstmt.executeUpdate();
            
            rs = pstmt.getGeneratedKeys();
            rs.next();
            cliente.setId(rs.getInt(1));
            
        } finally {

            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null)
                conn.close();
            if (rs != null) {
                rs.close();
            }
        }
        return cliente;
    }
    public void delete(int id) throws SQLException, NamingException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new ConnectionFactory().getConnection();

            pstmt = connection.prepareStatement(DELETE);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public Cliente update(Cliente cliente) throws SQLException, NamingException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new ConnectionFactory().getConnection();

            pstmt = connection.prepareStatement(UPDATE);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setInt(3, cliente.getId());

            pstmt.executeUpdate();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return cliente;

    }
    public ArrayList<Cliente> listAll() throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(LISTALL);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));

                clientes.add(cliente);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return clientes;
    }

    public Cliente findById(int clienteid) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente c = null;

        try {
            conn = new ConnectionFactory().getConnection();
            ps = conn.prepareStatement(FINDBYID);
            ps.setInt(1, clienteid);
            rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente();
                c.setId(clienteid);
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return c;
    }
}
