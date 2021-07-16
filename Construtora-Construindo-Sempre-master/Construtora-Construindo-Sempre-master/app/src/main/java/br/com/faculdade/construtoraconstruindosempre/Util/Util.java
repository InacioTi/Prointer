package br.com.faculdade.construtoraconstruindosempre.Util;

import android.app.AlertDialog;
import android.content.Context;

import br.com.faculdade.construtoraconstruindosempre.R;

/**
 * Created by edinilson.silva on 12/09/2016.
 */
public class Util {

    public  static void alert(Context context, String mensagem){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(R.string.app_name);

        alertDialog.setMessage(mensagem);

        //Criando um botão onde contem texo OK sem ação.
        alertDialog.setPositiveButton("Ok", null);

        //Mostra a mensagem na tela
        alertDialog.show();
    }
}
