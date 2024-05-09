package br.unipar.exemplo.rest.models;

import br.unipar.exemplo.rest.dto.ClienteRequest;


public class Cliente {
    private int id;
    private String nome;
    private String cpf;

    public Cliente(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Cliente() {
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public static Cliente requestToCliente(ClienteRequest dto){
        return new Cliente(0,dto.getNome(),dto.getCpf());
    }
}
