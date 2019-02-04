package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class FragmentNewTeam extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String titulo = getArguments().getString("titulo");
        String texto = getArguments().getString("texto");
        return createSimpleDialog(titulo,texto);
    }


    public AlertDialog createSimpleDialog(String titulo, String texto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogError);

        builder.setTitle(titulo)
                .setMessage(texto)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Log.d("Holaaaaaaaaa", "aqui hare algo");
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                            }
                        });

        return builder.create();
    }
}
