package com.faculdade.faculdade.RecycleView.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.faculdade.faculdade.RecycleView.sql.DbGateway;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    private final String TABLE_TAREFAS = "Tarefas";
    private DbGateway gw;

    public TarefaDAO(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public List<Tarefa> retornarTodos(){
        List<Tarefa> tarefas = new ArrayList<>();
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Tarefas", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String titulo = cursor.getString(cursor.getColumnIndex("Titulo"));
            String descri = cursor.getString(cursor.getColumnIndex("Descri"));
            String professor = cursor.getString(cursor.getColumnIndex("Professor"));
            String semestre = cursor.getString(cursor.getColumnIndex("Semestre"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            tarefas.add(new Tarefa(id, titulo,descri, professor, semestre, vip));
        }
        cursor.close();
        return tarefas;
    }

    public Tarefa retornarUltimo(){
        Cursor cursor = gw.getDatabase().rawQuery("SELECT * FROM Tarefas ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String titulo = cursor.getString(cursor.getColumnIndex("Titulo"));
            String descri = cursor.getString(cursor.getColumnIndex("Descri"));
            String professor = cursor.getString(cursor.getColumnIndex("Professor"));
            String semestre = cursor.getString(cursor.getColumnIndex("Semestre"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            cursor.close();
            return new Tarefa(id, titulo, descri, professor, semestre, vip);
        }

        return null;
    }

    public boolean salvar(String titulo, String descri, String professor, String semestre, boolean vip){
        return salvar(0, titulo, descri, professor, semestre, vip);
    }

    public boolean salvar(int id, String titulo,String descri, String professor, String semestre, boolean vip){
        ContentValues cv = new ContentValues();
        cv.put("Titulo", titulo);
        cv.put("Descri", descri);
        cv.put("Professor", professor);
        cv.put("Semestre", semestre);
        cv.put("Vip", vip ? 1 : 0);
        if(id > 0)
            return gw.getDatabase().update(TABLE_TAREFAS, cv, "ID=?", new String[]{ id + "" }) > 0;
        else
            return gw.getDatabase().insert(TABLE_TAREFAS, null, cv) > 0;
    }

    public boolean excluir(int id){
        return gw.getDatabase().delete(TABLE_TAREFAS, "ID=?", new String[]{ id + "" }) > 0;
    }
}
