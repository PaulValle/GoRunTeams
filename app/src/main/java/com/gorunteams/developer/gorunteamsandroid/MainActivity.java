package com.gorunteams.developer.gorunteamsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/guardarUsuario";

    private ProgressBar progressBar;


    public final static String path2 = "https://restgorun.herokuapp.com/listarUsuarios";
    java.net.URL url;
    ArrayList listaUsuarios=new ArrayList();
    String responseText;
    StringBuffer response;

    String respuesta;
    public String rsname ="" ;
    public String rsmail = "";
    public String rspass = "";
    public String celular = "";
    public String rol = "";
    public String passUser = "";
    ServicioWeb servicio;
    //ServicioWeb2 servicio2;
    int idUser;

    private LinearLayout prof_section;
    private Button SignOut;
    private SignInButton SignIn;
    private TextView name, email;
    private GoogleApiClient googleApiClient;
    private login2 loginmet;
    private static final int REQ_CODE= 9001;

/*
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {}

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10);
        Button gologin = (Button) findViewById(R.id.signin);


        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                Intent itemintent = new Intent(MainActivity.this, login2.class);
                startActivity(itemintent);
                progressBar.setVisibility(View.GONE);


                /*PackageManager pm=getPackageManager();
                try {

                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";

                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    waIntent.setPackage("com.whatsapp");

                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));

                } catch (PackageManager.NameNotFoundException e) {

                }*/





            }
        });

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
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
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
            // Log.d(TAG, "Name" + name);
            // Log.d(TAG, "Name" + email);
           // name.setText(rsname);
            this.rsname = name2;
            rsmail = email;
            rspass = "default";
            //name.setText(rsname);
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
            String varComparar=this.getWebServiceResponseData2(rsmail);



            if(varComparar=="existe"){

                return "logear";
            }else{


                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("mail",rsmail);
                stringMap.put("pass", rspass);
                stringMap.put("nombre", rsname);
                stringMap.put("rol", "Estudiante");
                stringMap.put("celular", "9999999999");
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
                Log.d(TAG, "LOCALIZANDO"+jsonarray);
                for (int i=0;i<jsonarray.length();i++){


                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String mail = jsonobject.getString("mail");
                    int id = jsonobject.getInt("idusuario");
                    String rolRow = jsonobject.getString("rol");
                    String celularRow = jsonobject.getString("celular");
                    String nombreRow = jsonobject.getString("nombre");
                    String passRow = jsonobject.getString("pass");



                    Log.d(TAG, "*****************************");
                    Log.d(TAG, "CORRECIONES"+id);
                    Log.d(TAG, "CORRECIONES"+nombreRow);
                    Log.d(TAG, "CORRECIONES"+mail);
                    Log.d(TAG, "CORRECIONES"+passRow);
                    Log.d(TAG, "CORRECIONES"+rolRow);
                    Log.d(TAG, "CORRECIONES"+celularRow);
                    if (String.valueOf(mailcompare).equals(String.valueOf(mail))){
                        rsname =nombreRow ;
                        rsmail = mail;
                        rspass = passRow;
                        celular = celularRow;
                        rol = rolRow;
                        idUser = id;


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
            if (respuesta=="correcto" || respuesta=="logear"){
                //servicio2 = (ServicioWeb2) new ServicioWeb2().execute();


                Intent itemintent = new Intent(MainActivity.this, resume.class);
                progressBar.setVisibility(View.GONE);
                itemintent.putExtra("mail" , rsmail);
                itemintent.putExtra("name" , rsname);
                itemintent.putExtra("id" , idUser);
                itemintent.putExtra("pass" , rspass);

                if(rol == null){
                    itemintent.putExtra("celular" , "9999999999");
                    itemintent.putExtra("rol" , "Estudiante");
                }else{
                    itemintent.putExtra("celular" , celular);
                    itemintent.putExtra("rol" , rol);
                }


                Log.d(TAG, "*****************************");
                Log.d(TAG, "CORRECIONES333"+idUser);
                Log.d(TAG, "CORRECIONES333"+rsname);
                Log.d(TAG, "CORRECIONES333"+rsmail);
                Log.d(TAG, "CORRECIONES333"+rspass);
                Log.d(TAG, "CORRECIONES333"+celular);
                Log.d(TAG, "CORRECIONES333"+rol);
                startActivity(itemintent);

            }else{
                Bundle args = new Bundle();
                args.putString("titulo", "Advertencia");
                args.putString("texto", "No se pudo registrar sus datos");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getSupportFragmentManager(), "FragmentError");
            }

/*
            try {
                PackageManager packageManager = context.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
                i.setPackage("com.whatsapp"); i.setData(Uri.parse(url));
                if (i.resolveActivity(packageManager) != null) {
                    context.startActivity(i);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
*/


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




