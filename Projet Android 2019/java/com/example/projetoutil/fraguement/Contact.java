package com.example.projetoutil.fraguement;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetoutil.Connexion;
import com.example.projetoutil.MainActivity;
import com.example.projetoutil.R;

import static com.example.projetoutil.apresinscription.currentuser;


public class Contact extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText Email;
    EditText Subject;
    EditText Message;

    public Contact() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contact, container, false);
        EditText Nom = (EditText)view.findViewById(R.id.Nom_contact);
        Subject = (EditText)view.findViewById(R.id.Tele_contact);
         Message = (EditText)view.findViewById(R.id.message_contact);
         Email = (EditText)view.findViewById(R.id.Emai_Contact);
        Button mButton =(Button)view.findViewById(R.id.btn_send_email);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendMail();
            }
        });
                return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    private  void  sendMail()
    {
        String recipientList = Email.getText().toString();
        String[] recipients= recipientList.split(",");

        String Subjects = Subject.getText().toString();
        String MESS = Message.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,Subjects);
        intent.putExtra(Intent.EXTRA_TEXT, MESS);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"clement.bricout@orange.fr"));

    }

}
