package com.example.projetoutil;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.projetoutil.Validating.checkEmail;
import static com.example.projetoutil.Validating.checkPassword;

public class Inscription extends AppCompatActivity {

    String email ,password1,password2;
    String motdepasse, motdepasse2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        getSupportActionBar().hide();
        final Button apresinscrp = (Button)findViewById(R.id.btn_loginIns);
        final TextView connexion = (TextView) findViewById(R.id.link_Inscrip);
        final EditText emails = (EditText) findViewById(R.id.input_email);

        final  EditText mdp1 = (EditText) findViewById(R.id.input_password);
        final EditText mdp2 = (EditText) findViewById(R.id.input_password_confirmation);
        connexion.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Inscription.this,Connexion.class );
                startActivity(intent);

            }
        });

        apresinscrp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Inscription.this,apresinscription.class );
                email = emails.getText().toString();

                if (checkEmail(email)== true && password1 == password2 )
                {

                    intent.putExtra("email",email);
                    intent.putExtra("password",password1);
                    startActivity(intent);
                }
                else
                {
                   emails.getBackground().setColorFilter(R.drawable.border, PorterDuff.Mode.DST_OVER);
                }
                            }
        });

    }






}
