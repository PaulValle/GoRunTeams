/*package com.gorunteams.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResumeFragment extends Fragment {

    public ResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resume, container, false);

    }


}*/





package com.gorunteams.developer.gorunteamsandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class ResumeFragment extends Fragment {

    public ResumeFragment() {}
    String textomail;
    String textname;
    String rolUsuario;
    String celular;
    int idUsuario;
    private static final String TAG = "AsyncTaskActivity";

    public final static String path = "https://restgorun.herokuapp.com/listarRecorridos";
    public final static String path2 = "https://restgorun.herokuapp.com/actualizarUsuario";

    java.net.URL url;
    ServicioWeb2 servicio2;
    ServicioWeb3 servicio3;

    String respuesta;
    String responseText;
    StringBuffer response;
    public static String  ncelular;
    public static String  tipoRol;
    public TextView fecha0;
    public TextView distancia0;
    public TextView tiempo0;
    public TextView fecha1;
    public TextView distancia1;
    public TextView tiempo1;
    public TextView fecha2;
    public TextView distancia2;
    public TextView tiempo2;
    public Button salir;
    public Button editar;
    public String numeroApasar;
    public String rolApasar;

    public TextView tv3;
    public TextView tv4;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
             textomail = getArguments().getString("mail");
            textname = getArguments().getString("name");
            idUsuario = getArguments().getInt("id");
            rolUsuario = getArguments().getString("rol");
            celular = getArguments().getString("celular");
        }
    }

    public void asignarVariables(String ncelular, String tipoRol){
        numeroApasar=ncelular;
        rolApasar=tipoRol;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_resume, container, false);
        TextView tv = (TextView) inf.findViewById(R.id.txtMAIL);
        tv.setText(textomail);
        TextView tv2 = (TextView) inf.findViewById(R.id.txtNAME);
        tv2.setText(textname);
        tv3 = (TextView) inf.findViewById(R.id.txtID);
        tv3.setText(rolUsuario);
        tv4 = (TextView) inf.findViewById(R.id.txtcelular);
        tv4.setText("#"+celular);
        fecha0 = (TextView) inf.findViewById(R.id.txtf1);
        distancia0 = (TextView) inf.findViewById(R.id.txtd1);
        tiempo0 = (TextView) inf.findViewById(R.id.txtT1);
        fecha1 = (TextView) inf.findViewById(R.id.txtf2);
        distancia1 = (TextView) inf.findViewById(R.id.txtd2);
        tiempo1 = (TextView) inf.findViewById(R.id.txtT2);
        fecha2 = (TextView) inf.findViewById(R.id.txtf3);
        distancia2 = (TextView) inf.findViewById(R.id.txtd3);
        tiempo2 = (TextView) inf.findViewById(R.id.txtT3);
        //servicio2 = (ServicioWeb2) new ServicioWeb2().execute();
        salir = (Button) inf.findViewById(R.id.btnSalir);
        editar = (Button) inf.findViewById(R.id.btnEditar);

        editar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popuproles, null);
                final PopupWindow popupWindow = new PopupWindow(popupView ,1000,1200);
                popupWindow.setFocusable(true);


                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                Button btn2 = (Button) popupView.findViewById(R.id.btnActualizar);

                btn2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        TextView texto = (TextView) popupView.findViewById(R.id.txtphone);
                        ncelular =String.valueOf(texto.getText());
                        Log.d(TAG, "estoy cogiendo el celular "+ncelular);
                        RadioButton r1 = (RadioButton) popupView.findViewById(R.id.radio_entrenador);
                        RadioButton r2=(RadioButton) popupView.findViewById(R.id.radio_estudiante);
                        if (r1.isChecked()==true) {
                            tipoRol= String.valueOf(r1.getText().toString());
                            Log.d(TAG, "estoy cogiendo el tipo "+tipoRol);

                        }else if (r2.isChecked()==true) {
                            tipoRol= String.valueOf(r2.getText().toString());
                            Log.d(TAG, "estoy cogiendo el tipo "+tipoRol);
                        }

                        //asignarVariables(ncelular,tipoRol);
                        servicio2 = (ServicioWeb2) new ServicioWeb2().execute();
                        popupWindow.dismiss();
                    }
                });









                Log.d(TAG, "celular "+ncelular);
                Log.d(TAG, "radiobutton "+tipoRol);
                //servicio3 = (ServicioWeb3) new ServicioWeb3().execute();
            }
        });

        TextView tv5 = (TextView) inf.findViewById(R.id.advertencia);
        if(celular.equals("9999999999")){
            tv5.setText("Para usar todas las funcionalidades debes editar tu perfil");
        }else{
            tv5.setText("");

        }
        Log.d(TAG, "celular2 "+ncelular);
        Log.d(TAG, "radiobutton2 "+tipoRol);

        //lanzarPopup();







        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View inf) {


                clearApplicationData();
                Intent itemintent = new Intent(getActivity(), MainActivity.class);
                startActivity(itemintent);



               /* SharedPreferences settings = this.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);*/

            }
        });
        return inf;
    }


    /*public void onRadioButtonClicked(View view) {

        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // hacemos un case con lo que ocurre cada vez que pulsemos un botón

        switch(view.getId()) {
            case R.id.radio_estudiante:
                if (checked)
                    //
                    break;
            case R.id.radio_entrenador:
                if (checked)
                    //
                    break;
        }
    }*/





    public void clearApplicationData() {
       File cacheDirectory = getActivity().getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }

       /* try {
            // clearing app data
            String packageName = getActivity().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }








    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txtMAIL);
        textView.setText(text);
    }





    private class ServicioWeb2 extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }

        protected String getWebServiceResponseData() {


            Log.d(TAG, "entre al servicio celular"+ ncelular);
            Log.d(TAG, "entre al servicio rol "+tipoRol);
            String respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, String> stringMap = new HashMap<>();
            stringMap.put("idusuario",String.valueOf(idUsuario));
            stringMap.put("mail", String.valueOf(textomail));
            stringMap.put("nombre", String.valueOf(textname));
            stringMap.put("pass", "default");
            stringMap.put("rol", String.valueOf(tipoRol));
            stringMap.put("celular", String.valueOf(ncelular));

            String requestBody = FormRegister.Utils.buildPostParameters(stringMap);
            try {
                urlConnection = (HttpURLConnection) FormRegister.Utils.makeRequest("PUT", path2, null, "application/x-www-form-urlencoded", requestBody);
                InputStream inputStream;
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
            } catch (Exception e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return respuesta;
        }






        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);

            if(respuesta == "correcto"){
                final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.mensajesconboton, null);
                final PopupWindow popupWindow = new PopupWindow(popupView ,1000,900);
                popupWindow.setFocusable(true);
                TextView texto = (TextView) popupView.findViewById(R.id.txtmessage);
                texto.setText("Se han guardado sus datos");
                //popupWindow.showAsDropDown(popupView, 0, 0);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                Button btn = (Button) popupView.findViewById(R.id.btnAcept);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        clearApplicationData();
                        Intent itemintent = new Intent(getActivity(), MainActivity.class);
                        startActivity(itemintent);
                    }
                });
            }else{


            }

        }
    }

    private class ServicioWeb3 extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }


        protected String getWebServiceResponseData() {
            Log.d(TAG, "entre al web service 2: ");
            Log.d(TAG, "entre al web service 2: "+idUsuario);
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
                JSONArray jsonarray2 = new JSONArray();
                JSONObject jsonobject2;

                Log.d(TAG, "Aqui recibi2: " + jsonarray.length());
                int indexUsuarios = 0;
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int idPersona = jsonobject.getInt("idusuario");
                    String Strfecha = jsonobject.getString("fecha");
                    String Strkm = jsonobject.getString("distancia");
                    String StrTmp = jsonobject.getString("tiempo");
                    int cont;

                    if (idUsuario == idPersona){
                        Log.d(TAG, "leyendo array aqui" + jsonobject);
                        jsonarray2.put(jsonobject);
                        Log.d(TAG, "entro arre" + jsonarray2);
                        Log.d(TAG, "hay " + jsonarray2.length());
                        cont=jsonarray2.length();
                        for (int j=jsonarray2.length()-1; j>=0;j--){
                            Log.d(TAG, "elemento" + j);

                            jsonobject2 = jsonarray2.getJSONObject(j);
                            Log.d(TAG, "estoy recorriendo" + jsonobject2);
                            String Strfecha2 = jsonobject2.getString("fecha");
                            String Strkm2 = jsonobject2.getString("distancia");
                            String StrTmp2 = jsonobject2.getString("tiempo");
                            Log.d(TAG, "contando" + cont);
                            if(cont-1 == j){
                                Log.d(TAG, "el contador entro" + jsonobject2);
                                fecha0.setText(Strfecha2);
                                distancia0.setText(Strkm2+"m");
                                tiempo0.setText(StrTmp2);
                            }
                            if(cont-2==(j)){
                                fecha1.setText(Strfecha2);
                                distancia1.setText(Strkm2+"m");
                                tiempo1.setText(StrTmp2);

                            }
                            if(cont-3==(j)){
                                fecha2.setText(Strfecha2);
                                distancia2.setText(Strkm2+"m");
                                tiempo2.setText(StrTmp2);

                            }
                        }

                        Log.d(TAG, "mostrando ultimo elemento" + (jsonarray2.length()-1));
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

            Log.d(TAG, "onPostExecute");
            if(respuesta == "correcto"){

            }else{


            }

        }
    }




}

