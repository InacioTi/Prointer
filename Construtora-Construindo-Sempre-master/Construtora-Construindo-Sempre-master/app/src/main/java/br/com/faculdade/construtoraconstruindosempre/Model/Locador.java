package br.com.faculdade.construtoraconstruindosempre.Model;

/**
 * Created by edinilson.silva on 16/09/2016.
 */
public class Locador {

    private Integer id_locador;
    private String nome;
    private String email;
    private String cpf;
    private String rg;

    public Integer getId_locador() {
        return id_locador;
    }

    public void setId_locador(Integer id_professor) {
        this.id_locador = id_locador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String disciplina) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String curso) {
        this.rg = rg;
    }

    public Locador(String nome, String email, String cpf, String rg) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.rg = rg;
    }

    public Locador() {
    }
}
