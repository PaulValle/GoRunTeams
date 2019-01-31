package com.gorunteams.developer.gorunteamsandroid;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/guardarUsuario";

    String respuesta;
    public String rsname ="" ;
    String rsname2 = "holaaaaaa";
    public String rsmail = "";
    public String rspass = "";
    ServicioWeb servicio;

    private LinearLayout prof_section;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView name, email;
    private GoogleApiClient googleApiClient;
    private login2 loginmet;
    private static final int REQ_CODE= 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gologin = (Button) findViewById(R.id.signin);
        //TextView name = (TextView) findViewById(R.id.txtName);
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemintent = new Intent(MainActivity.this, login2.class);
                MainActivity.this.startActivity(itemintent);
            }
        });

        //prof_section = (LinearLayout)findViewById(R.id.txtUser);
        SignIn = (SignInButton)findViewById(R.id.btnLog);
        SignIn.setOnClickListener(this);
        name = (TextView) findViewById(R.id.txtName);
        //prof_section.setVisibility(View.GONE);
        GoogleSignInOptions signInOptions= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLog:
                signIn();
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn()
    {

        if(isConnectedToInternet())
        {
            // Run AsyncTask
             Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);


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

    private void lanzarServicio() {
        servicio = (ServicioWeb) new ServicioWeb().execute();
    }


    private void handleResult(GoogleSignInResult result)
    {
        if(result.isSuccess())
        {
            GoogleSignInAccount account = result.getSignInAccount();
            String name2 = account.getDisplayName();
            String email = account.getEmail();
            Log.d(TAG, "Name" + name);
            Log.d(TAG, "Name" + email);
            name.setText(rsname);
            this.rsname = name2;
            rsmail = email;
            rspass = "default";
            name.setText(rsname);
            //enviarVariables(rsname,rsmail,rspass);
            String array[] = {rsname,rsmail,rspass};
            //return array;
            lanzarServicio();
            updateUI(true);

        }
        else
        {
            updateUI(false);
        }
        //return rsname;

    }

    public void enviarVariables(String var1, String var2, String var3) {
        rsname=var1;
        rsmail=var2;
        rspass=var3;

        //this.handleResult();
        Log.d(TAG, "ddddddddddddd"+rsname);
        //return rsname;
    }

    private void updateUI (boolean isLogin)
    {
        if(isLogin)
        {
            Log.d(TAG, "ddddddddddddd");
        }
        else{
            Log.d(TAG, "aaaaaaaaaaaaaaa");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        }

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

        MainActivity main ;

        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, String> stringMap = new HashMap<>();
            // Obtienes el layout que contiene los EditText

            //main.enviarVariables("sds","sdsds","sdsd");

           // name = (TextView) findViewById(R.id.txtName);
            Log.d(TAG, "por aqui entrooooooooooooooo");

            stringMap.put("mail",rsmail);
            stringMap.put("pass", rspass);
            stringMap.put("nombre", rsname);
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
               Intent itemintent = new Intent(MainActivity.this, resume.class);
                MainActivity.this.startActivity(itemintent);
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




















    /*
    Button gosingin = (Button) findViewById(R.id.signin);
        gosingin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(view) {
            Intent itemintent = new Intent(MainActivity.this, login.class);
            MainActivity.this.startActivity(itemintent);
        }
    });
    */


}
