package com.example.pendu.controle;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pendu.MainActivity;
import com.example.pendu.R;
import com.example.pendu.modele.utilisateur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//import android.widget.ArrayAdapter;

public class ListRecap extends ListActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("utilisateur");
    private static final String TAG = "recapUser";
    private ArrayList<utilisateur> listUsers;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    private ListRecap(){
        listUsers = new ArrayList<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button BtnAccueil = findViewById(R.id.btnScoreToAccueil);
        Button BtnDeconnect = findViewById(R.id.ScoreBtnDeconnect);
        BtnAccueil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListRecap.this);

                builder.setTitle("go back to the main activity ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListRecap.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });
        BtnDeconnect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListRecap.this);

                builder.setTitle("go back to the main activity ?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(ListRecap.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
        });

       // ArrayAdapter<String> myAdapter = new ArrayAdapter <String>(this, R.layout.text_listview, R.id.textList, listUsers);
        //setListAdapter(myAdapter);
        BtnAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accueil = new Intent(ListRecap.this, MainActivity.class);
                startActivity(accueil);
            }
        });
    }
    private void showData(DataSnapshot d) {
        for (DataSnapshot ds : d.getChildren()){
            utilisateur u = new utilisateur();
            u.setPseudo(ds.child(userID).getValue(utilisateur.class).getPseudo());
            u.setEmail(ds.child(userID).getValue(utilisateur.class).getEmail());
            u.setNbMotsCorrects(ds.child(userID).getValue(utilisateur.class).getNbMotsCorrects());
            listUsers.add(u);

        }
    }
}
