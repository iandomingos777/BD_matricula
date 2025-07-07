package model.entities;

import java.io.Serializable;

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private int horas;
    private int requisito;
    private boolean eletiva;
    private int id_curso;

    public Disciplina() {
    }

    public Disciplina(int id, String nome, int horas, int requisito, boolean eletiva, int id_curso) {
        this.id = id;
        this.nome = nome;
        this.horas = horas;
        this.requisito = requisito;
        this.eletiva = eletiva;
        this.id_curso = id_curso;
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

    public int getHoras() {
        return horas;
    }

    public int getRequisito() {
        return requisito;
    }

    public boolean isEletiva() {
        return eletiva;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public void setRequisito(int requisito) {
        this.requisito = requisito;
    }

    public void setEletiva(boolean eletiva) {
        this.eletiva = eletiva;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }
}
