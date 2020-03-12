package com.example.pendu.controle;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pendu.R;
import com.example.pendu.modele.mot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Connexion extends AppCompatActivity {
    private mot mot;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private String email;
    private String password;
    private static final String TAG = "connect";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DataMot = database.getReference("mot");
    private DatabaseReference DataUser = database.getReference("utilisateur");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        mAuth = FirebaseAuth.getInstance();
        final Button btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setEnabled(false);
        Button btnLogin = findViewById(R.id.buttonLogin);
        final TextView textLogin = findViewById(R.id.ConnectTextLogin);
        final TextView textPassword = findViewById(R.id.ConnectTtextPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (textPassword.getText().toString().isEmpty() ||
                        textLogin.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Connexion.this);
                    builder.setTitle("Enter values in all text areas !")
                            .create()
                            .show();
                }  else {
                    email = textLogin.getText().toString();
                    password = textPassword.getText().toString();
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information"
                                        Toast.makeText(Connexion.this, "sign in with success !",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        btnPlay.setEnabled(true);
                                        btnPlay.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent partie = new Intent(Connexion.this, partie.class);
                                                partie.putExtra("mot", mot.getChaineCar());
                                                startActivity(partie);
                                            }
                                        });

                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Connexion.this, "sign in failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });


                }
            }
        });
        DataMot.addListenerForSingleValueEvent(new ValueEventListener() {
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
    public void lireMotData(DataSnapshot d) {
        Random r = new Random();
        String indMot = String.valueOf(r.nextInt((int) (d.getChildrenCount()))+1);
        mot motData = new mot();
        System.out.println(d.child(indMot).toString());
        for (DataSnapshot da : d.child(indMot).getChildren()){
            if (da.getKey().contains("chaineCar")){
                motData.setChaineCar((String) da.getValue());
                System.out.println(motData.getChaineCar());
                this.mot = motData;
            }
        }
    }

}
