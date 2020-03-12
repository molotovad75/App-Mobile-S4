package com.example.pendu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pendu.controle.Connexion;
import com.example.pendu.controle.Inscription;

public class MainActivity extends AppCompatActivity {
    private Button btnCo;
    private Button btnInscr;
    private Button btnScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnPlay = findViewById(R.id.AccueilBtnPlay);
        btnPlay.setEnabled(false);
        btnCo = findViewById(R.id.btnCo);
        btnInscr = findViewById(R.id.btnInscr);
        btnScore = findViewById(R.id.AccueilBtnScore);

        /*btnScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent score = new Intent(MainActivity.this, ListRecap.class);
                startActivity(score);
            }
        });*/
        btnCo.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent Connexion = new Intent(MainActivity.this, Connexion.class);
                startActivity(Connexion);
            }
        });
        btnInscr.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent Inscription = new Intent(MainActivity.this, Inscription.class);
                startActivity(Inscription);
            }
        });

    }
}

    /*@Override



    ***************
    https://www.knowband.com/blog/fr/non-classifiee/comment-r%C3%A9cup%C3%A9rer-les-donn%C3%A9es-de-firebase-sous-android/
    **************
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            boolean etatPartie = data.getBooleanExtra(partie.getEtatPartie(), false);
            ArrayList<utilisateur> users = lireUtilisateurFile();
            if (contientUtilisateur(users, user)) {
                if (etatPartie) {
                    users.get(users.indexOf(user)).setNbMotsCorrects(users.get(users.indexOf(user)).getNbMotsCorrects() + 1);
                }
            } else {
                if (etatPartie)
                    user.setNbMotsCorrects(1);
                users.add(user);
            }
            ecrireUtilisateurFile(users);
        }
    }*/
