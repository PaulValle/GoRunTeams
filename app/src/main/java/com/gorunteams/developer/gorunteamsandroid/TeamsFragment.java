package com.gorunteams.developer.gorunteamsandroid;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
    java.net.URL url;
    String responseText;
    StringBuffer response;
    String respuesta;
    Integer respuesta2;
    ServicioWeb servicio;
    ServicioWeb2 servicio2;
    ServicioWeb3 servicio3;
    public TextView tv;
    public static String nameEquipo;
    public static String detalleEquipo;
    public static int idTeam;
    public static int counter;

    public TeamsFragment() {}
    String textomail;
    String textname;
    public static int idUsuario;
    Integer idEq;
    public LinearLayout layoutBotones;
    public LinearLayout.LayoutParams lp;
    public static View inf;
    public ArrayList listaUsuarios=new ArrayList();

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
        final TextView tv = (TextView) inf.findViewById(R.id.txtteam);
        tv.setText("qqqqqqqqqqqqqqqqqqqqqqqqqqq");




        final TextView id = (TextView) inf.findViewById(R.id.txtiduser);
        final TextView user = (TextView) inf.findViewById(R.id.txtnombreuser);
        final TextView mail = (TextView) inf.findViewById(R.id.txtmailuser);
        id.setText(String.valueOf(idUsuario));
        user.setText(String.valueOf(textname));
        mail.setText(String.valueOf(textomail));
        servicio3 = (ServicioWeb3) new ServicioWeb3(inf).execute();
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

        //consultarEquipos(idUsuario);
        Log.d(TAG, "listaAFUERA" +listaUsuarios );
        return inf;
    }
    /*public void crearBotones(String textoBotones){
        //onCreateView(new Bundle());


        //Obtenemos el linear layout donde colocar los botones
        layoutBotones = (LinearLayout) inf.findViewById(R.id.layoutTeams);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT );


        Log.d(TAG, "databotones" + textoBotones);

        Button button;
        button = new Button(getContext() );
        //Asignamos propiedades de layout al boton
        button.setLayoutParams(lp);
        //Asignamos Texto al botón
        button.setText("Boton "+String.format("%02d", 1 ));
        //Añadimos el botón a la botonera
        layoutBotones.addView(button);
    }*/
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
            //String nequipo2= dato;
            responseText = response.toString();








            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int idUserRow = jsonobject.getInt("idusuario");

                    int idEquipoRow = jsonobject.getInt("idequipo");


                    if (idUserRow == dato){
                        //idTeam=idEquipo;

                        this.buscarNombredeEquipo(idEquipoRow);

                        //respuesta2= idTeam;
                        //servicio2 = (ServicioWeb2) new ServicioWeb2().execute();
                        Log.d(TAG, "buscandoid" + this.buscarNombredeEquipo(idEquipoRow) );


                        //Obtenemos el linear layout donde colocar los botones
                        //layoutBotones = (LinearLayout) inf.findViewById(R.id.layoutTeams);

                        //Creamos las propiedades de layout que tendrán los botones.
                        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
                        //lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                               // LinearLayout.LayoutParams.WRAP_CONTENT );



                        listaUsuarios.add(this.buscarNombredeEquipo(idEquipoRow) );
                        Log.d(TAG, "lista de usuarios" +listaUsuarios );
                        Button btn = (Button) inf.findViewById(R.id.btn1);
                        btn.setText(this.buscarNombredeEquipo(idEquipoRow));

                        //crearBotones(this.buscarNombredeEquipo(idEquipoRow));






                        /*Button button = new Button(this.vista);
                        //Asignamos propiedades de layout al boton
                        button.setLayoutParams(lp);
                        //Asignamos Texto al botón
                        button.setText("Boton "+String.format("%02d", 1 ));
                        //button.setText("ssss");
                        //Añadimos el botón a la botonera
                        layoutBotones.addView(button);*/





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
                //Log.d(TAG, "ServerData: " + path2);
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
            //String nequipo2= dato;
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
                        //idTeam=idEquipo;
                        //respuesta2= idTeam;
                        //servicio2 = (ServicioWeb2) new ServicioWeb2().execute();
                        //Log.d(TAG, "buscandoid" + idEquipoRow );
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