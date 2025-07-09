package model.entities;

import java.io.Serializable;

public class Campus implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String endereco;
    private int ano_criacao;
    private String site;

    public Campus() {
    }

    public Campus(int id, String nome, String endereco, int ano_criacao, String site) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.ano_criacao = ano_criacao;
        this.site = site;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getAno_criacao() {
        return ano_criacao;
    }

    public String getSite() {
        return site;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setAno_criacao(int ano_criacao) {
        this.ano_criacao = ano_criacao;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
