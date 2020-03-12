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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Inscription extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseDatabase database;
    private DatabaseReference data;
    private String email;
    private String password;
    private TextView InputMail;
    private TextView InputPseudo;
    private TextView InputPassword;
    private Button btnInscr;
    private static final String TAG = "connect";
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        mAuth = FirebaseAuth.getInstance();
        btnPlay = findViewById(R.id.InscBtnPlay);
        btnPlay.setEnabled(false);
        InputMail = findViewById(R.id.textLogin);
        InputPseudo = findViewById(R.id.textPseudo);
        InputPassword = findViewById(R.id.textPassword);
        btnInscr = findViewById(R.id.buttonRegister);
        final TextView ConfirmPassword  = findViewById(R.id.ConfirmTextPassword);
        database = FirebaseDatabase.getInstance();
        data = database.getReference("utilisateur");

        btnInscr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (InputMail.getText().toString().isEmpty() ||
                    InputMail.getText().toString().isEmpty() ||
                    InputMail.getText().toString().isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Inscription.this);
                        builder.setTitle("Enter values in all text areas !")
                            .create()
                            .show();
                }
                else if (!ConfirmPassword.getText().toString().equals(InputPassword.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Inscription.this);
                    builder.setTitle("Confirm well your password !")
                            .create()
                            .show();
                }
                else{
                    email = InputMail.getText().toString();
                    password = InputPassword.getText().toString();
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(Inscription.this, "user created with success !.",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        btnPlay.setEnabled(true);
                                        btnPlay.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent partie = new Intent(Inscription.this, partie.class);
                                                startActivity(partie);
                                            }
                                        });
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Inscription.this, "fail to create user.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });

                }
            }
        });
    }
    public void updateUI(FirebaseUser user){
        String keyID = data.push().getKey();
        data.child(keyID).setValue(user);
    }

}
