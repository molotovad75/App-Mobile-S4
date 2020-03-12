package com.example.pendu.controle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pendu.MainActivity;
import com.example.pendu.R;
import com.example.pendu.modele.mot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class partie extends AppCompatActivity implements View.OnClickListener {
    private mot mot;
    private ArrayList<TextView> listText;
    private TextView coupsRestans;
    private static int NbEssais = 15;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private static final String TAG = "mot";

    private boolean estGagné;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.partie);
        Intent intent = getIntent();
        this.mot = new mot();
        mot.setChaineCar(intent.getStringExtra("mot"));
        coupsRestans = findViewById(R.id.CoupsRestants);
        coupsRestans.setText("Nombre de coups restants : " + NbEssais);
        coupsRestans.setTextIsSelectable(false);
        Button BtnValiderLettre = findViewById(R.id.BtnValideLetter);
        Button BtnDeconnect = findViewById(R.id.PartieBtnDeconnect);
        Button BtnAccueil = findViewById(R.id.PartieBtnAcceuil);
        BtnValiderLettre.setOnClickListener(this);
        this.listText = placerTxtViewLettre();
        BtnDeconnect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(partie.this);

                builder.setTitle("Are you sure you want to deconnect ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(partie.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });
        BtnAccueil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(partie.this);

                builder.setTitle("go back to the main activity ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(partie.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        String suiteLettre = "";
        for (TextView t : listText) {
            if (t.getText().equals(mot.getChaineCar().charAt(listText.indexOf(t))) && t.isTextSelectable()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Bonne lettre !")
                        .create()
                        .show();
                t.setTextIsSelectable(false);
            } else if (!t.getText().equals(mot.getChaineCar().charAt(listText.indexOf(t))) && t.isTextSelectable()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Mauvaise lettre !")
                        .create()
                        .show();
                t.setText("");
                --NbEssais;
                coupsRestans.setText("Coups restants : " + NbEssais);

            }
            suiteLettre.concat((String) t.getText());
        }
        if (suiteLettre.equals(mot.getChaineCar())){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Game finished! You win !" )
                    .setPositiveButton("go back to the principal activity", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
        else{
            int c = 0;
            for (TextView t : listText){
                if (t.getText() != ""){
                    ++c;
                }
            }
            if (c == mot.getChaineCar().length()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Game finished! You loose !")
                        .setPositiveButton("go back to the principal activity", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

    public ArrayList<TextView> placerTxtViewLettre(){
        int margeagauche = 10;
        int len = mot.getChaineCar().length();
        ArrayList<TextView> listText = new ArrayList<>();
        LinearLayout postLayout = findViewById(R.id.LinearLayoutPartie);
        // Définition du composant Text.
        for (int i=0; i<len; ++i) {
            System.out.println("passage boucle");
            TextView txtLettre = new TextView(this);
            txtLettre.setId(i);
            txtLettre.setWidth(100);
            //txtLettre.setLeft(margeagauche);
            txtLettre.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            // Définition de la façon dont le composant va remplir le layout.
            postLayout.addView(txtLettre);
            margeagauche += 10;
        }
        return listText;
    }
}
