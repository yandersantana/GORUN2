
package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResumeFragment extends Fragment {

    public ResumeFragment() {}
    String textomail;
    String textname;
    int idUsuario;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null){


             textomail = getArguments().getString("mail");
            textname = getArguments().getString("name");
            idUsuario = getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_resume, container, false);
        TextView tv = (TextView) inf.findViewById(R.id.txtMAIL);
        tv.setText(textomail);
        TextView tv2 = (TextView) inf.findViewById(R.id.txtNAME);
        tv2.setText(textname);
        TextView tv3 = (TextView) inf.findViewById(R.id.txtID);
        tv3.setText("#"+idUsuario);
        return inf;
    }


    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txtMAIL);
        textView.setText(text);
    }




}

