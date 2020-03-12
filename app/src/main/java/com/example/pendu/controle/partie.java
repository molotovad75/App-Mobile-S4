package com.example.pendu.controle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class partie extends AppCompatActivity implements View.OnClickListener {
    private mot mot;
    private ArrayList<TextView> listText;
    private TextView coupsRestans;
    private static int NbEssais = 15;
    private FirebaseAuth mAuth;
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DataMot = database.getReference("mot");
    private DatabaseReference DataUser = database.getReference("utilisateur");
    private static final String TAG = "mot";

    private boolean estGagné;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.partie);
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

        DataMot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                lireMotData(dataSnapshot);
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
    public void lireMotData(DataSnapshot d) {
        Random r = new Random();
        String indMot = String.valueOf(r.nextInt((int) (d.getChildrenCount())));
        mot mot = new mot();
        mot.setChaineCar(d.child(indMot).getValue(mot.class).getChaineCar());
        this.mot = mot;
        System.out.println(mot.getChaineCar());
    }

    public ArrayList<TextView> placerTxtViewLettre(){
        int margeagauche = 10;
        int len = mot.getChaineCar().length();
        ArrayList<TextView> listText = new ArrayList<>();
        LinearLayout postLayout = findViewById(R.id.LinearLayoutPartie);
        // Définition du composant Text.
        for (int i=0; i<len; ++i) {
            TextView txtLettre = new TextView(this);
            txtLettre.setId(i);
            txtLettre.setWidth(15);
            txtLettre.setLeft(margeagauche);
            txtLettre.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            // Définition de la façon dont le composant va remplir le layout.
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // Ajout du composant au layout.
            listText.add(txtLettre);
            postLayout.addView(txtLettre,layoutParam);
            margeagauche += 10;
        }
        return listText;
    }
}
