package br.com.faculdade.construtoraconstruindosempre.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edinilson.silva on 09/09/2016.
 */
public class DatabaseUtil2 extends SQLiteOpenHelper {

    private static final String NOME_BASE_DE_DADOS = "Construtora.db";

    private static final int VERSAO_BASE_DE_DADOS = 1;

    private static final String TB_RESERVA = "tb_reserva";
    private static final String TB_LOCADOR = "tb_locador";
    private static final String TB_EQUIP = "tb_equip";

    private static final String KEY_ID = "id";

    //COLUNA TABELA LOCADOR
    private static final String KEY_NOMELOCADOR = "nome_loca";
    private static final String KEY_EMAIL = "email_loca";
    private static final String KEY_CPF = "cpf_loca";
    private static final String KEY_RG = "rg_loca";

    //COLUNA TABELA EQUIPAMENTO
    private static final String KEY_NOMEEQUIP = "nome_equip";
    private static final String KEY_QUANT = "quant_equip";

    //COLUNA TABELA RESERVA
    private static final String KEY_NOME_LOCA_RESERVA = "nome_loca_reserva";
    private static final String KEY_NOME_EQUIP_RESERVA = "nome_equip_reserva";
    private static final String KEY_DESTINO = "destino_reserva";
    private static final String KEY_DATA = "data_reserva";
    private static final String KEY_HORA_INICIAL = "hora_inicial_reserva";
    private static final String KEY_HORA_FINAL = "hora_final_reserva";

    //CRIANDO TABELA LOCADOR
    private static final String CRIAR_TABELA_LOCADOR = "CREATE TABLE " + TB_LOCADOR + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NOMELOCADOR + " TEXT, " + KEY_EMAIL + " TEXT, "  +
            KEY_CPF + " INT, " + KEY_RG + " IN" + ")";

    //CRIANDO TABLEA EQUIPAMENTO
    private static final String CRIA_TABLEA_EQUIPAMENTO = "CREATE TABLE " + TB_EQUIP + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NOMEEQUIP + " TEXT, " + KEY_QUANT + " TEXT" + ")";

    //CRIANDO TABELA RESERVA
    private static final String CRIAR_TABELA_RESERVA = "CREATE TABLE " + TB_RESERVA + " (" +
            KEY_ID + " INTEGER PRIMARY KEY, " + KEY_NOME_LOCA_RESERVA + " INTEGER, " +
            KEY_NOME_EQUIP_RESERVA + " INTEGER, " + KEY_DESTINO + " TEXT, " + KEY_DATA + " DATETIME, " +
            KEY_HORA_INICIAL + " DATETIME, " + KEY_HORA_FINAL + " DATETIME" + ")";


    public DatabaseUtil2(Context context) {
        super(context, NOME_BASE_DE_DADOS, null , VERSAO_BASE_DE_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder stringBuilderCreateTabelaReserva = new StringBuilder();

        stringBuilderCreateTabelaReserva.append("CREATE TABLE tb_reserva (");
        stringBuilderCreateTabelaReserva.append("       id_reserva INTEGER PRIMARY KEY AUTOINCREMENT, ");
        stringBuilderCreateTabelaReserva.append("       nomeLocador TEXT NOT NULL, ");
        stringBuilderCreateTabelaReserva.append("       equipamento TEXT NOT NULL, ");
        stringBuilderCreateTabelaReserva.append("       destino TEXT NOT NULL, ");
        stringBuilderCreateTabelaReserva.append("       data TEXT NOT NULL, ");
        stringBuilderCreateTabelaReserva.append("       horarioInicial TEXT NOT NULL, ");
        stringBuilderCreateTabelaReserva.append("       horarioFinal TEXT NOT NULL);");

        db.execSQL(stringBuilderCreateTabelaReserva.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS tb_reserva");
        onCreate(db);
    }

    public SQLiteDatabase getConexaoDataBase(){
        return  this.getWritableDatabase();
    }
}
