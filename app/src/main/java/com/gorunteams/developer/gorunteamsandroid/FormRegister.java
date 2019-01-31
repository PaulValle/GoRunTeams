package com.gorunteams.developer.gorunteamsandroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


import org.json.JSONArray;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FormRegister extends AppCompatActivity {
    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/guardarUsuario";

    EditText txtMail;
    EditText txtPass;
    EditText txtName;
    String respuesta;
    ServicioWeb servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_register);

        txtMail=(EditText) findViewById(R.id.txtUser);
        txtPass=(EditText) findViewById(R.id.txtPass);
        txtName=(EditText) findViewById(R.id.txtName);

        Button goresume = (Button) findViewById(R.id.login);
        goresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent itemintent = new Intent(FormRegister.this, login2.class);
               // FormRegister.this.startActivity(itemintent);
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
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, String> stringMap = new HashMap<>();
            // Obtienes el layout que contiene los EditText
            LinearLayout linearLayout = findViewById(R.id.formRegistro);

            // Obtiene el numero de EditText que contiene el layout
            int count = linearLayout.getChildCount();

            // Recorres todos los editText y si hay alguno vacio cambias el valor de la
            // variable isAllFill a false, lo que indica que aun hay editText vacios.
            boolean isAllFill = true;
            for (int i = 0; i < count; i++) {

                // En cada iteración obtienes uno de los editText que se encuentran el
                // layout.
                try {
                    EditText editText = (EditText) linearLayout.getChildAt(i);
                    // Compruebas su el editText esta vacio.
                    if (editText.getText().toString().isEmpty()) {
                        isAllFill = false;
                        break;
                    }
                }catch (Exception e){
                }
            }

            //if (isAllFill && genero != null) {
                Log.i("MainActivity", "onCreate -> else -> Todos los EditText estan llenos.");

                stringMap.put("mail", String.valueOf(txtMail.getText()));
                stringMap.put("pass", String.valueOf(txtPass.getText()));
                stringMap.put("nombre", String.valueOf(txtName.getText()));
                String requestBody = Utils.buildPostParameters(stringMap);
                try {
                    urlConnection = (HttpURLConnection) Utils.makeRequest("POST", path, null, "application/x-www-form-urlencoded", requestBody);
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
            /*} else {
                Log.i("MainActivity", "onCreate -> if -> Hay EditText vacios.");
                Bundle args = new Bundle();
                args.putString("titulo", "Advertencia");
                args.putString("texto", "Llena todos los campos");
                ProblemaConexion f=new ProblemaConexion();
                f.setArguments(args);
                f.show(getSupportFragmentManager(), "ProblemaConexión");
                servicio.cancel(true);
            }*/
            //return respuesta;
        }

        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "onPostExecute");
            if (respuesta=="correcto"){
                Intent itemintent = new Intent(FormRegister.this, login2.class);
                FormRegister.this.startActivity(itemintent);
            }else{
               // Log.d(TAG, "Registro fail:" + nombre);
                Bundle args = new Bundle();
                args.putString("titulo", "Advertencia");
                args.putString("texto", "No se pudo registrar sus datos");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getSupportFragmentManager(), "FragmentError");
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





    }
