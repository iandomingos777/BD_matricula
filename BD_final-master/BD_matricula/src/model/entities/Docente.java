package model.entities;
import java.io.Serializable;

public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String formacao;
    private int telefone;
    private String area;
    private String lattes;

    public Docente() {
    }

    public Docente(int id, String nome, String formacao, int telefone, String area, String lattes) {
        this.id = id;
        this.nome = nome;
        this.formacao = formacao;
        this.telefone = telefone;
        this.area = area;
        this.lattes = lattes;
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

    public String getFormacao() {
        return formacao;
    }

    public int getTelefone() {
        return telefone;
    }

    public String getArea() {
        return area;
    }

    public String getLattes() {
        return lattes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setLattes(String lattes) {
        this.lattes = lattes;
    }
}
