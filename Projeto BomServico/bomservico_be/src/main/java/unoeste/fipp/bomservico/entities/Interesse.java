package unoeste.fipp.bomservico.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Interesse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "int_id")
    private int id;

    @Column(name = "int_nome")
    private String nome;

    @Column(name = "int_fone")
    private String fone;

    @Column(name = "int_email")
    private String email;

    @Column(name = "int_mensagem")
    private String mensagem;

    @Column(name = "anu_id")
    private int anu_id;

    public Interesse() {
    }

    public Interesse(int id, String nome, String fone, String email, String mensagem, int anu_id) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.mensagem = mensagem;
        this.anu_id = anu_id;
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

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getAnu_id() {
        return anu_id;
    }

    public void setAnu_id(int anu_id) {
        this.anu_id = anu_id;
    }
}
