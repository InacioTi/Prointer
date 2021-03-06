package com.faculdade.faculdade.RecycleView.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.faculdade.faculdade.Login.model.User;
import com.faculdade.faculdade.RecycleView.repository.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tarefas.db";
    private static final int DATABASE_VERSION = 2;
    private final String CREATE_TABLE = "CREATE TABLE Tarefas (ID INTEGER PRIMARY KEY AUTOINCREMENT, Titulo TEXT NOT NULL, Professor TEXT, Semestre TEXT NOT NULL, Descri TEXT, Vip INTEGER NOT NULL, Data TEXT NOT NULL, HoraInicial TEXT NOT NULL);";

    //private final String CREATE_TABLE = "ALTER TABLE Tarefas ADD COLUMN Descri TEXT; ";
    //private final String CREATE_TABLE = "DROP TABLE IF EXISTS Tarefas;";
    //private final String UPDATE_TABLE = "ALTER TABLE Tarefas ADD COLUMN Descri TEXT; ";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(UPDATE_TABLE);
    }

}
