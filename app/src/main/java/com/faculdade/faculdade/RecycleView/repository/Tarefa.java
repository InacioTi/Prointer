package com.faculdade.faculdade.RecycleView.repository;

import java.io.Serializable;


public class Tarefa implements Serializable {

    private int id;
    private String titulo;
    private String descri;
    private String professor;
    private String semestre;
    private boolean vip;

    public Tarefa(int id, String titulo, String descri, String professor,  String semestre, boolean vip){
        this.id = id;
        this.titulo = titulo;
        this.descri = descri;
        this.professor = professor;
        this.semestre = semestre;
        this.vip = vip;
    }

    public int getId(){ return this.id; }
    public String getTitulo(){ return this.titulo; }
    public String getDesc(){ return this.descri; }
    public String getProfessor(){ return this.professor; }
    public boolean getVip(){ return this.vip; }
    public String getSemestre(){ return this.semestre; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Tarefa)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
