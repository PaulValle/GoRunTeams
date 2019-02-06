package com.gorunteams.developer.gorunteamsandroid;

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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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


    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/guardarRecorrido";
    ServicioWeb servicio;
    String respuesta;



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
    public Button btnMost;
    public Button btnMost2;
    public  String date;

    String textomail;
    String textname;
    public static int idUsuario;



    public FriendFragment() {
    }


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inf = inflater.inflate(R.layout.fragment_friend, container, false);

        pausado = false;


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


        date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.d("ddd", "La fecha de hoy es"+date);

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
                    }else{
                        cronos.cancel(true);
                        return 0;
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
        location.setLongitude(lng1); //longitud
        Location location2 = new Location("localizacion 2");
        location2.setLatitude(lat2);  //latitud
        location2.setLongitude(lng2); //longitud
        double distance = location.distanceTo(location2);
        calculo=String.format("%.3f", (distance/1000));
        resultado = (TextView) inf.findViewById(R.id.txtKm);
        resultado.setText(calculo);
        servicio = (ServicioWeb) new ServicioWeb().execute();

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









    ///////////////////////////////////////////

    private class ServicioWeb extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("idusuario", String.valueOf(idUsuario));
            stringMap.put("fecha", String.valueOf(date));
            stringMap.put("tiempo", String.valueOf(cronometro.getText()));
            stringMap.put("distancia", String.valueOf(calculo));

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



        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);

            Log.d(TAG, "onPostExecute");
            if (respuesta=="correcto"){

                Log.d(TAG, "si inserto en la base");

            }else{
                Log.d(TAG, "no inserto en la base");

            }

        }
    }
}








