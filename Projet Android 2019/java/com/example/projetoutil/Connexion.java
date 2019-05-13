package com.example.projetoutil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.projetoutil.Connexion;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.projetoutil.apresinscription.currentuser;

public class Connexion extends AppCompatActivity {
DbHandler BD;
    ConstraintLayout mLayout;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        setContentView(R.layout.activity_connexion);
        getSupportActionBar().hide();
        BD= new DbHandler(this);
        mLayout = (ConstraintLayout) findViewById(R.id.connexionlayout);
        Button mButton =(Button) findViewById(R.id.btn_login);
        animationDrawable = (AnimationDrawable) mLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();
        final EditText email = (EditText)findViewById(R.id.input_email);
        final EditText password = (EditText)findViewById(R.id.input_password);
        final TextView inscription = (TextView) findViewById(R.id.link_signup);
        inscription.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){



                    Intent intent = new Intent(Connexion.this,Inscription.class );
                    startActivity(intent);



            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    currentuser = BD.GetUser(email.getText().toString(),password.getText().toString());
                    Intent intent = new Intent(Connexion.this,MainActivity.class );


                    startActivity(intent);



            }
        });
    }
}
