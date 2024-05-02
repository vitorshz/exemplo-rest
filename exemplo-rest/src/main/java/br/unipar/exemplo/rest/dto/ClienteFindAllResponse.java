package br.unipar.exemplo.rest.dto;


public class ClienteFindAllResponse {
    private int id;
    private String nome;

    public ClienteFindAllResponse(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ClienteFindAllResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
