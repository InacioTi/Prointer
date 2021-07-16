package br.com.faculdade.construtoraconstruindosempre.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.faculdade.construtoraconstruindosempre.Login.activities.LoginActivity;
import br.com.faculdade.construtoraconstruindosempre.R;

/**
 * Created by FATE - Aluno on 13/09/2016.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, 3000);

    }

}
