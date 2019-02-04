package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class TeamsFragment extends Fragment {
    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/guardarEquipo";
    public final static String path2 = "https://restgorun.herokuapp.com/listarEquipos";
    public final static String path3 = "https://restgorun.herokuapp.com/guardarUsuarioEnEquipo";
    public final static String path4 = "https://restgorun.herokuapp.com/listarUsuariosxEquipo";
    public final static String path5 = "https://restgorun.herokuapp.com/listarUsuarios";
    URL url;
    String responseText;
    StringBuffer response;
    String respuesta;
    Integer respuesta2;
    ServicioWeb servicio;
    ServicioWeb2 servicio2;
    ServicioWeb3 servicio3;
    ServicioWeb4 servicio4;
    ServicioWeb5 servicio5;
    public TextView tv;
    public TextView detalleEquipoRow;
    public TextView nombreEquipoRow;
    public TextView jugador1;
    public TextView jugador2;
    public TextView jugador3;
    public TextView txtEmail;
    public TextView mensaje3;
    public String txtEmail2;
    public TextView mensajitoError;

    public static String nameEquipo;
    public static String detalleEquipo;
    public static int idTeam;
    public static int counter;

    public static String nombreEquipoSolicitado;
    public static String detalleEquipoSolicitado;
    public static int idEquipoSolicitado;
    public static String nombreEquipoSeleccionado;

    String textomail;
    String textname;
    public static int idUsuario;
    Integer idEq;
    public static View inf;
    public ArrayList listaUsuarios=new ArrayList();
    public ArrayList listaBotones = new ArrayList();
    public Button btnequipo1;
    public Button btnequipo2;
    public Button btnequipo3;
    public Button btnequipo4;
    public Button btnequipo5;

    public static String resAgreIntegrante;
    public static String resAgreIntegrante2;

    public TeamsFragment() {}

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
        inf = inflater.inflate(R.layout.fragment_teams, container, false);


        btnequipo1 = (Button) inf.findViewById(R.id.btn1);
        btnequipo2 = (Button) inf.findViewById(R.id.btn2);
        btnequipo3 = (Button) inf.findViewById(R.id.btn3);
        btnequipo4 = (Button) inf.findViewById(R.id.btn4);
        btnequipo5 = (Button) inf.findViewById(R.id.btn5);


        btnequipo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnequipo1.getText().equals("NUEVO EQUIPO")){
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    //TextView mensajitoError = (TextView) popupWindow.findViewById(R.id.mensajeChiquito);
                    mensajitoError = (TextView) popupView.findViewById(R.id.mensajeChiquito);
                    Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnguardar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //
                            // tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    nombreEquipoSeleccionado = btnequipo1.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();

                    Button btn = (Button) popupView.findViewById(R.id.btnCerrarEquipo);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnAgregarJugador);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            final View popupView2 = LayoutInflater.from(getActivity()).inflate(R.layout.nuevointegrante, null);
                            final PopupWindow popupWindow2 = new PopupWindow(popupView2, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            popupWindow2.setFocusable(true);
                            popupWindow2.showAsDropDown(popupView2, 0, 0);

                            Button btn = (Button) popupView2.findViewById(R.id.btnclose);
                            btn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    popupWindow2.dismiss();
                                }
                            });

                            Button btn2 = (Button) popupView2.findViewById(R.id.btnagregar);
                            btn2.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {

                                    txtEmail = (TextView) popupView2.findViewById(R.id.txtcorreo);
                                    txtEmail2 = txtEmail.getText().toString();
                                    Log.d(TAG, "mostrando mail"+txtEmail2);
                                    Log.d(TAG, "presentando respuesta11"+resAgreIntegrante);
                                    servicio5 = (ServicioWeb5) new ServicioWeb5().execute();
                                    popupWindow2.dismiss();
                                    Log.d(TAG, "presentando respuesta11336"+resAgreIntegrante2);
                                    /*
                                    if (resAgreIntegrante =="AgregadoCorrectamente"){
                                        //popupWindow2.dismiss();
                                         final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                                         final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                            popupWindow.setFocusable(true);
                                       mensaje3 = (TextView) popupView.findViewById(R.id.mensajePersonalizado);
                                        Log.d(TAG, "presentando respuesta113355"+mensaje3.getText());
                                         mensaje3.setText("Se registrò el usuario con èxito");
                                         popupWindow.showAsDropDown(popupView, 0, 0);
                                    }else {
                                        final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                                        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                        popupWindow.setFocusable(true);
                                         mensaje3 = (TextView) popupView.findViewById(R.id.mensajePersonalizado);
                                         String valor = String.valueOf(mensaje3.getText());
                                        Log.d(TAG, "presentando respuesta113355"+valor );


                                        mensaje3.setText("No se pudo registrar el usuario");
                                        popupWindow.showAsDropDown(popupView, 0, 0);

                                    }
                                    */

                                }

                            });

                        }

                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                    Log.d(TAG, "presentando respuesta112"+resAgreIntegrante);
                }
            }
        });




        btnequipo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnequipo2.getText().equals("NUEVO EQUIPO")){
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnguardar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    nombreEquipoSeleccionado = btnequipo2.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    Button btn = (Button) popupView.findViewById(R.id.btnCerrarEquipo);

                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnAgregarJugador);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                            final View popupView2 = LayoutInflater.from(getActivity()).inflate(R.layout.nuevointegrante, null);
                            final PopupWindow popupWindow2 = new PopupWindow(popupView2, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            popupWindow2.setFocusable(true);
                            popupWindow2.showAsDropDown(popupView2, 0, 0);

                            Button btn = (Button) popupView2.findViewById(R.id.btnclose);
                            btn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    popupWindow2.dismiss();
                                }
                            });

                            Button btn2 = (Button) popupView2.findViewById(R.id.btnagregar);
                            btn2.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {

                                    txtEmail = (TextView) popupView2.findViewById(R.id.txtcorreo);
                                    txtEmail2 = txtEmail.getText().toString();
                                    Log.d(TAG, "mostrando mail"+txtEmail2);

                                    servicio5 = (ServicioWeb5) new ServicioWeb5().execute();


                                }
                            });
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }
            }
        });

        btnequipo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnequipo3.getText().equals("NUEVO EQUIPO")){
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnguardar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow3 = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow3.setFocusable(true);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    nombreEquipoSeleccionado = btnequipo3.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    Button btn = (Button) popupView.findViewById(R.id.btnCerrarEquipo);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow3.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnAgregarJugador);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow3.dismiss();
                            final View popupView2 = LayoutInflater.from(getActivity()).inflate(R.layout.nuevointegrante, null);
                            final PopupWindow popupWindow2 = new PopupWindow(popupView2, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            popupWindow2.setFocusable(true);
                            popupWindow2.showAsDropDown(popupView2, 0, 0);

                            Button btn = (Button) popupView2.findViewById(R.id.btnclose);
                            btn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    popupWindow2.dismiss();
                                }
                            });

                            Button btn2 = (Button) popupView2.findViewById(R.id.btnagregar);
                            btn2.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {

                                    txtEmail = (TextView) popupView2.findViewById(R.id.txtcorreo);
                                    txtEmail2 = txtEmail.getText().toString();
                                    Log.d(TAG, "mostrando mail"+txtEmail2);

                                    servicio5 = (ServicioWeb5) new ServicioWeb5().execute();


                                }
                            });
                        }
                    });
                    popupWindow3.showAsDropDown(popupView, 0, 0);
                }
            }
        });

        btnequipo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnequipo4.getText().equals("NUEVO EQUIPO")){
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnguardar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    nombreEquipoSeleccionado = btnequipo4.getText().toString();
                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    Button btn = (Button) popupView.findViewById(R.id.btnCerrarEquipo);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnAgregarJugador);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }
            }
        });

        btnequipo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnequipo5.getText().equals("NUEVO EQUIPO")){
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnguardar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    nombreEquipoSeleccionado = btnequipo5.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    Button btn = (Button) popupView.findViewById(R.id.btnCerrarEquipo);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnAgregarJugador);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView nombreEquipo = (TextView) popupView.findViewById(R.id.txtnomequipo);
                            TextView detalle = (TextView) popupView.findViewById(R.id.txtdetequipo);
                            //tv.setText(nombreEquipo.getText());
                            nameEquipo=String.valueOf(nombreEquipo.getText());

                            detalleEquipo=String.valueOf(detalle.getText());
                            Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                            servicio = (ServicioWeb) new ServicioWeb().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }
            }
        });


        servicio3 = (ServicioWeb3) new ServicioWeb3(inf).execute();

        return inf;
    }

    private class ServicioWeb4 extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData2();
        }

        protected String getWebServiceResponseData2() {
            try {
                url=new URL(path2);
                Log.d(TAG, "ServerData: " + path2);
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
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String nEquipo = jsonobject.getString("nombre");
                    String dEquipo = jsonobject.getString("detalle");

                    int idEquipo = jsonobject.getInt("idequipo");

                    if (nombreEquipoSeleccionado.equals(nEquipo)){
                        nombreEquipoSolicitado=nEquipo;
                        idEquipoSolicitado= idEquipo;
                        detalleEquipoSolicitado= dEquipo;
                    }else{

                    }
                }
                this.getWebServiceResponseData(idEquipoSolicitado);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "respuestas";
        }
        protected String getWebServiceResponseData(int idEquipoABuscar) {
            try {
                url=new URL(path4);
                Log.d(TAG, "ServerData: " + path2);
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
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                int indexUsuarios = 0;
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    //String nPersona = jsonobject.getString("nombre");
                    int idEquipoBuscando = jsonobject.getInt("idequipo");

                    int idPersonaBuscando = jsonobject.getInt("idusuario");

                    if (idEquipoABuscar == idEquipoBuscando){
                        indexUsuarios++;
                        String nombrePersona = this.getWebServiceResponseData3(idPersonaBuscando);
                        if(indexUsuarios == 1){
                            jugador1.setText(nombrePersona);
                        }
                        if(indexUsuarios == 2){
                            jugador2.setText(nombrePersona);
                        }
                        if(indexUsuarios == 3){
                            jugador3.setText(nombrePersona);
                        }

                    }else{

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected String getWebServiceResponseData3(int idUsuarioABuscar) {
            try {
                url=new URL(path5);
                Log.d(TAG, "ServerData: " + path2);
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
                    String nPersona = jsonobject.getString("nombre");
                    int idPersonaBuscando = jsonobject.getInt("idusuario");

                    if (idUsuarioABuscar == idPersonaBuscando){
                        return nPersona;

                    }else{

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }


        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            if (respuesta=="respuestas"){
                detalleEquipoRow.setText(detalleEquipoSolicitado);
                nombreEquipoRow.setText(nombreEquipoSolicitado);

            }else{

            }

        }
    }



    private class ServicioWeb3 extends AsyncTask<Integer, Integer, String> {
        private final View vista;

        public ServicioWeb3(View inf) {
            this.vista =inf;
        }

        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData(idUsuario);
        }

        protected String getWebServiceResponseData(int dato) {
            try {
                url=new URL(path4);
                Log.d(TAG, "ServerData: " + path2);
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
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                int indiceBotones = 0;
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int idUserRow = jsonobject.getInt("idusuario");

                    int idEquipoRow = jsonobject.getInt("idequipo");


                    if (idUserRow == dato){
                        indiceBotones++;
                        this.buscarNombredeEquipo(idEquipoRow);
                        Log.d(TAG, "buscandoid" + this.buscarNombredeEquipo(idEquipoRow) );

                        if(indiceBotones == 1){
                            btnequipo1.setText(this.buscarNombredeEquipo(idEquipoRow));
                        }
                        if(indiceBotones == 2){
                            btnequipo2.setText(this.buscarNombredeEquipo(idEquipoRow));
                        }
                        if(indiceBotones == 3){
                            btnequipo3.setText(this.buscarNombredeEquipo(idEquipoRow));
                        }
                        if(indiceBotones == 4){
                            btnequipo4.setText(this.buscarNombredeEquipo(idEquipoRow));
                        }
                        if(indiceBotones == 5){
                            btnequipo5.setText(this.buscarNombredeEquipo(idEquipoRow));
                        }
                        Log.d(TAG, "INDICES" + indiceBotones);

                        listaUsuarios.add(this.buscarNombredeEquipo(idEquipoRow) );
                        Log.d(TAG, "lista de usuarios" +listaUsuarios );

                    }else{

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "respuesta";
        }






        protected String buscarNombredeEquipo(int dato) {
            try {
                url=new URL(path2);
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
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    //int idUserRow = jsonobject.getInt("idusuario");
                    String nombreEquipoRow = jsonobject.getString("nombre");
                    int idEquipoRow = jsonobject.getInt("idequipo");


                    if (idEquipoRow == dato){
                        return nombreEquipoRow;
                    }else{

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "falla";
        }





        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "data:siiiiiiiiiiiiii" + idEq );

            Log.d(TAG, "onPostExecute");
            if (respuesta=="correcto"){

            }else{

            }

        }
    }


    private class ServicioWeb5 extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData(txtEmail2);
        }

        protected String getWebServiceResponseData(String dato) {
            try {
                url=new URL(path5);
                Log.d(TAG, "ServerData: " + path5);
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
            Log.d(TAG, "yo recibi "+dato);

            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                //int indiceBotones = 0;
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int idUserEquipo = jsonobject.getInt("idusuario");
                    String mailx = jsonobject.getString("mail");
                    Log.d(TAG, "lokendo "+ mailx);
                    if (dato.equals(mailx)){
                        Log.d(TAG, "digimon"+mailx);

                        this.añadirIntegranteEquipo(idUserEquipo);
                        //return "";
                        respuesta="satisfactorio";
                    }else{
                        Log.d(TAG, "NOVALE"+mailx);
                        //mensajitoError.setText("El usuario no existe en la base");
                    }
                    //return "";
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta;
        }




        protected void añadirIntegranteEquipo(int usuario) {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, Integer> intMap = new HashMap<>();
            //this.getWebServiceResponseData2(nameEquipo);
            intMap.put("idusuario", usuario);
            intMap.put("idequipo", idEquipoSolicitado);
            Log.d(TAG, "pokemon"+ usuario);
            Log.d(TAG, "digimon"+ idEquipoSolicitado);

            String requestBody = FormRegister.Utils.buildPostParameters(intMap);
            try {
                urlConnection = (HttpURLConnection) FormRegister.Utils.makeRequest("POST", path3, null, "application/x-www-form-urlencoded", requestBody);
                InputStream inputStream;
                Log.d(TAG, requestBody);
                // get stream
                if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }
                respuesta="correcto";
                resAgreIntegrante = "AgregadoCorrectamente";
                //return respuesta;
            } catch (Exception e) {
                e.printStackTrace();
                //return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }


        }


        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "data:siiiiiiiiiiiiii" + idEq );

            Log.d(TAG, "onPostExecute");
            if (respuesta=="satisfactorio"){


                Bundle args = new Bundle();
                args.putString("titulo", "Advertencia");
                args.putString("texto", "Se registro su jugador");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getFragmentManager(), "FragmentError");


                /*resAgreIntegrante = "AgregadoCorrectamente";
                TeamsFragment.mostrarMensaje(resAgreIntegrante);
                Log.d(TAG, "si paso por aqui ");
                Log.d(TAG, "resAgre "+resAgreIntegrante);*/


            }else{

            }

        }
    }

    private static void mostrarMensaje(String resAgreIntegrante) {
        Log.d(TAG, "aqui llegue"+resAgreIntegrante);
        resAgreIntegrante2=resAgreIntegrante;
        Log.d(TAG, "aqui llegue2"+resAgreIntegrante2);
        resAgreIntegrante=resAgreIntegrante;
         /*if(resAgreIntegrante =="AgregadoCorrectamente"){
                    //popupWindow2.dismiss();
                    final View popupView = LayoutInflater.from().inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    //popupWindow.setFocusable(true);
                    final TextView mensaje = (TextView) inf.findViewById(R.id.mensajePersonalizado);
                   // mensaje.setText("Se registrò el usuario con èxito");
                    popupWindow.showAsDropDown(popupView, 0, 0);
                }else {
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    final TextView mensaje = (TextView) inf.findViewById(R.id.mensajePersonalizado);
                    mensaje.setText("No se pudo registrar el usuario");
                    popupWindow.showAsDropDown(popupView, 0, 0);

                }*/

    }


    private class ServicioWeb extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("nombre", nameEquipo);
            stringMap.put("detalle", detalleEquipo);

            String requestBody = FormRegister.Utils.buildPostParameters(stringMap);
            try {
                urlConnection = (HttpURLConnection) FormRegister.Utils.makeRequest("POST", path, null, "application/x-www-form-urlencoded", requestBody);
                InputStream inputStream;
                Log.d(TAG, requestBody);
                // get stream
                if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }
                respuesta="correcto";
                idEq = this.getWebServiceResponseData2(nameEquipo);
                return respuesta;
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }


        protected Integer getWebServiceResponseData2(String dato) {
            try {
                url=new URL(path2);
                Log.d(TAG, "ServerData: " + path2);
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
            String nequipo2= dato;
            responseText = response.toString();
            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String nEquipo = jsonobject.getString("nombre");

                    int idEquipo = jsonobject.getInt("idequipo");

                    if (String.valueOf(nequipo2).equals(String.valueOf(nEquipo))){
                        idTeam=idEquipo;
                        respuesta2= idTeam;
                        servicio2 = (ServicioWeb2) new ServicioWeb2().execute();

                    }else{

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta2;
        }



        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "data:siiiiiiiiiiiiii" + listaUsuarios );

            Log.d(TAG, "onPostExecute");
            if (respuesta=="correcto"){

            }else{

            }

        }
    }

    public static class Utils{
        public static String buildPostParameters(Object content) {
            String output = null;
            if ((content instanceof String) ||
                    (content instanceof JSONObject) ||
                    (content instanceof JSONArray)) {
                output = content.toString();
            } else if (content instanceof Map) {
                Uri.Builder builder = new Uri.Builder();
                HashMap hashMap = (HashMap) content;
                if (hashMap != null) {
                    Iterator entries = hashMap.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry entry = (Map.Entry) entries.next();
                        builder.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                        entries.remove(); // avoids a ConcurrentModificationException
                    }
                    output = builder.build().getEncodedQuery();
                }
            }

            return output;
        }

        public static URLConnection makeRequest(String method, String apiAddress, String accessToken, String mimeType, String requestBody) throws IOException {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(apiAddress);

                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(!method.equals("GET"));
                urlConnection.setRequestMethod(method);

                urlConnection.setRequestProperty("Authorization", "Bearer " + accessToken);

                urlConnection.setRequestProperty("Content-Type", mimeType);
                OutputStream outputStream = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                writer.write(requestBody);
                writer.flush();
                writer.close();
                outputStream.close();

                urlConnection.connect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return urlConnection;
        }
    }



    private class ServicioWeb2 extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, Integer> intMap = new HashMap<>();
            this.getWebServiceResponseData2(nameEquipo);
            intMap.put("idusuario", idUsuario);
            intMap.put("idequipo", idEq);
            String requestBody = FormRegister.Utils.buildPostParameters(intMap);
            try {
                urlConnection = (HttpURLConnection) FormRegister.Utils.makeRequest("POST", path3, null, "application/x-www-form-urlencoded", requestBody);
                InputStream inputStream;
                Log.d(TAG, requestBody);
                // get stream
                if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = urlConnection.getInputStream();
                } else {
                    inputStream = urlConnection.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, response = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    response += temp;
                }
                respuesta="correcto";
                return respuesta;
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }


        protected String getWebServiceResponseData2(String dato) {
            try {
                url=new URL(path2);
                Log.d(TAG, "ServerData: " + path2);
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
            String mailcompare= dato;
            responseText = response.toString();
            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String nEquipo = jsonobject.getString("nombre");
                    int idEquipo = jsonobject.getInt("idequipo");
                    if (String.valueOf(mailcompare).equals(String.valueOf(nEquipo))){
                        idTeam=idEquipo;
                        respuesta="existe";
                    }else{
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta;
        }

        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "onPostExecute");
            if (respuesta=="correcto"){

            }else{

            }
        }
    }

}