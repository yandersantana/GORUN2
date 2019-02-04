package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class login2 extends AppCompatActivity {
    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/listarusuarios";
    URL url;
    ArrayList listaUsuarios=new ArrayList();
    String responseText;
    StringBuffer response;
    TextView txtUser , txtPass;
    String respuesta;
    ServicioWeb servicio;
    int idUser;
    String usuario;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        txtUser=(TextView) findViewById(R.id.txtUser);
        txtPass=(TextView) findViewById(R.id.txtPass);
        TextView goregister = (TextView) findViewById(R.id.register);
        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemintent = new Intent(login2.this, FormRegister.class);
                login2.this.startActivity(itemintent);
            }
        });

        Button goresume = (Button) findViewById(R.id.login);
        goresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnectedToInternet())
                {
                    // Run AsyncTask
                    servicio = (ServicioWeb) new ServicioWeb().execute();
                }
                else
                {
                    Log.d(TAG, "Error Conexion");
                    Bundle args = new Bundle();
                    args.putString("titulo", "Advertencia");
                    args.putString("texto", "No hay conexión de Internet");
                    FragmentError f=new FragmentError();
                    f.setArguments(args);
                    f.show(getSupportFragmentManager(), "FragmentError");

                }
            }
        });

    }



    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
    private class ServicioWeb extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "UI thread onPreExecute");
        }

        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }
        protected String getWebServiceResponseData() {
            try {
                url=new URL(path);
                Log.d(TAG, "ServerData: " + path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();

                Log.d(TAG, "Response code: " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    // Reading response from input Stream
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String output;
                    response = new StringBuffer();

                    while ((output = in.readLine()) != null) {
                        response.append(output);
                    }
                    in.close();
                }}
            catch(Exception e){
                e.printStackTrace();
            }

            responseText = response.toString();


            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String mail = jsonobject.getString("mail");
                    String pass=jsonobject.getString("pass");
                    String name=jsonobject.getString("nombre");
                    int id = jsonobject.getInt("idusuario");
                    if(String.valueOf(txtUser.getText()).equals(String.valueOf(mail))){
                        Log.d(TAG,"ENTRO");
                        if (String.valueOf(txtPass.getText()).equals(String.valueOf(pass))){
                            respuesta="correcto";
                            usuario=name;
                            idUser=id;
                            email=mail;

                        }else{

                        }
                    }else{
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Print progress to the log
            Log.d(TAG, values[0] + " is prime " + values[1] + " % ready");
        }

        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "onPostExecute" );

            if (respuesta=="correcto" ){
                Intent itemintent = new Intent(login2.this, resume.class);
                itemintent.putExtra("mail" , email);
                itemintent.putExtra("name" , usuario);
                itemintent.putExtra("id" , idUser);

                startActivity(itemintent);

            }else{
                Log.d(TAG, "Login fail:" + respuesta);
                Bundle args = new Bundle();
                args.putString("titulo", "Advertencia");
                args.putString("texto", "Usuario o contraseña incorrecta");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getSupportFragmentManager(), "FragmentError");
            }
        }



    }





}
