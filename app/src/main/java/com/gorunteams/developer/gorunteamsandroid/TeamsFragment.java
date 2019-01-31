package com.gorunteams.developer.gorunteamsandroid;

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
    java.net.URL url;
    ArrayList listaUsuarios=new ArrayList();
    String responseText;
    StringBuffer response;
    String respuesta;
    ServicioWeb servicio;
    public TextView tv;
    public static String nameEquipo;
    public static String detalleEquipo;
    public static int idTeam;

    public TeamsFragment() {}
    String textomail;
    String textname;
    int idUsuario;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            textomail = getArguments().getString("mail");
            //textomail = "ddd";
            textname = getArguments().getString("name");
            //textname = "ssss";
           idUsuario = getArguments().getInt("id");
            //idUsuario =25;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_teams, container, false);
        final TextView tv = (TextView) inf.findViewById(R.id.txtteam);
        tv.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqq");



        final TextView id = (TextView) inf.findViewById(R.id.txtiduser);
        final TextView user = (TextView) inf.findViewById(R.id.txtnombreuser);
        final TextView mail = (TextView) inf.findViewById(R.id.txtmailuser);

        id.setText(String.valueOf(idUsuario));
       user.setText(String.valueOf(textname));
       mail.setText(String.valueOf(textomail));

        Button btnNewTeam = (Button) inf.findViewById(R.id.btnTeams);
        btnNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        tv.setText(nombreEquipo.getText());
                        nameEquipo=String.valueOf(nombreEquipo.getText());

                        detalleEquipo=String.valueOf(detalle.getText());
                        Log.d(TAG, "aquiiiiiiiiiii"+nombreEquipo.getText());

                        servicio = (ServicioWeb) new ServicioWeb().execute();
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(popupView, 0, 0);

            }
        });

        return inf;
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
           String varComparar="ff";
           boolean validarEmail = true;
            if(varComparar=="existe"){

                return "logear";
            }else{
                if(validarEmail==true){
                    //Log.i("MainActivity", "onCreate -> else -> Todos los EditText estan llenos.");

                    this.getWebServiceResponseData2(nameEquipo);

                    stringMap.put("nombre", nameEquipo);
                    stringMap.put("detalle", detalleEquipo);
                    //stringMap.put("nombre", String.valueOf(txtName.getText()));
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

                }else{
                    return "ddd";
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
                    //String pass=jsonobject.getString("pass");
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