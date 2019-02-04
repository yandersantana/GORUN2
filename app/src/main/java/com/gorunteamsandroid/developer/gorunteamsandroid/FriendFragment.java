package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class FriendFragment extends Fragment implements OnMapReadyCallback{
    public static View inf;
    public CronometroMio cronos;
    public static TextView label;
    public static boolean running;
    public TextView cronometro;
    public String nombrecronometro;        // Nombre del cronómetro
    public int segundos, minutos, horas;   // Segundos, minutos y horas que lleva activo el cronómetro
   // public Handler escribirenUI;           // Necesario para modificar la UI
    public Boolean pausado = false;                // Para pausar el cronómetro
    public String salida; // Salida formateada de los datos del cronómetro




    public static GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    double lng1 = 0.0;
    double lat1 = 0.0;
    double lng2 = 0.0;
    double lat2 = 0.0;
    public TextView resultado;
    public String calculo;
    int cont=0;



    //public static View inf;
    public Button btnMost;
    public Button btnMost2;
    public FriendFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inf = inflater.inflate(R.layout.fragment_friend, container, false);


        btnMost = (Button) inf.findViewById(R.id.btnIniciar);
        btnMost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Location location = new Location("localizacion 1");
                location.setLatitude(-2.114690);  //latitud
                location.setLongitude(-79.899495); //longitud
                Location location2 = new Location("localizacion 2");
                location2.setLatitude(-2.104645);  //latitud
                location2.setLongitude(-79.909665); //longitud
                double distance = location.distanceTo(location2);
                Log.d("ddd", "mostrando resultado"+distance);*/
                datoInicio();
                //datoFin();
            }
        });

        btnMost2 = (Button) inf.findViewById(R.id.btnParar);
        btnMost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Location location = new Location("localizacion 1");
                location.setLatitude(-2.114690);  //latitud
                location.setLongitude(-79.899495); //longitud
                Location location2 = new Location("localizacion 2");
                location2.setLatitude(-2.104645);  //latitud
                location2.setLongitude(-79.909665); //longitud
                double distance = location.distanceTo(location2);
                Log.d("ddd", "mostrando resultado"+distance);*/
                //datoInicio();
                datoFin();
            }
        });


        Button start = (Button) inf.findViewById(R.id.btnIniciar);
        Button pause = (Button) inf.findViewById(R.id.btnParar);
        cronometro = (TextView) inf.findViewById(R.id.txtTiempo);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View inf) {
                pausado = false;
                segundos = 0;
                minutos = 0;
                horas = 0;
                cronos = (FriendFragment.CronometroMio) new FriendFragment.CronometroMio().execute();
                datoInicio();


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View inf) {
                pausado = true;
                datoFin();

            }
        });




        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main_branch_map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        return inf;
    }



    private class CronometroMio extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... params) {
            try
            {
                while(Boolean.TRUE)
                {
                    Thread.sleep(1000);
                    salida = "";
                    Log.d("", "hilo "+ pausado);
                    if( !pausado )
                    {
                        segundos++;
                        if(segundos == 60)
                        {
                            segundos = 0;
                            minutos++;
                        }
                        if(minutos == 60)
                        {
                            minutos = 0;
                            horas++;
                        }
                        // Formateo la salida
                        salida += "0";
                        salida += horas;
                        salida += ":";
                        if( minutos <= 9 )
                        {
                            salida += "0";
                        }
                        salida += minutos;
                        salida += ":";
                        if( segundos <= 9 )
                        {
                            salida += "0";
                        }
                        salida += segundos;
                        cronometro.setText(salida);
                    }
                }
            }
            catch (InterruptedException e)
            {
                Log.i("Cronometro", "Error en el cronometro " + nombrecronometro + ": " + e.toString());
            }
            return 0;
        }

        protected void onPostExecute() {
            //waitSplashFinished = true;
            // finished();
        }
    }



    private void datoInicio() {

        Log.d("ddd", "mostrando latitud1"+lat1);
        Log.d("ddd", "mostrando longitud1"+lng1);
        //actualizarUbicacion();
    }


    private void datoFin(){


        Log.d("ddd", "mostrando latitud1"+lat1);
        Log.d("ddd", "mostrando longitud1"+lng1);
        mostrarResultado();

    }

    private void mostrarResultado(){
        Location location = new Location("localizacion 1");
                location.setLatitude(lat1);  //latitud

                Log.d("ddd", "Estoy mostrando resultado latitud1"+lat1);
                location.setLongitude(lng1); //longitud
                Log.d("ddd", "Estoy mostrando resultado longitud1"+lng1);
                Location location2 = new Location("localizacion 2");
                location2.setLatitude(lat2);  //latitud
                 //Log.d("ddd", "Estoy mostrando resultado latitud2"+(-2.104645));
                location2.setLongitude(lng2); //longitud
                Log.d("ddd", "Estoy mostrando resultado longitud2"+(-79.909665));
                double distance = location.distanceTo(location2);
                Log.d("ddd", "mostrando resultado de distancia"+distance/1000);
                calculo=String.format("%.2f", (distance/1000));
        resultado = (TextView) inf.findViewById(R.id.txtKm);
        Log.d("ddd", "mostrando resultado totañ"+resultado);
        resultado.setText(calculo);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();

    }

    private void agregarMarcador(double lat, double lng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador);
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) marcador.remove();
        //GoogleMap nMap;
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi posicion actual").icon(icon));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);

            guadarposicion2(lat, lng);
            if(cont==0){
                guadarposicion(lat, lng);
            }
            cont++;
        }
    }

    private void guadarposicion(double lat, double lng) {
        lng1=lng;
        lat1=lat;
    }

    private void guadarposicion2(double lat, double lng) {
        lng2=lng;
        lat2=lat;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            return;
        }
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1500,0,locationListener);
    }
}








