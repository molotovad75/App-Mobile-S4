package com.example.pendu.controle;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pendu.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Connexion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private String email;
    private String password;
    private static final String TAG = "connect";
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
    }

}
