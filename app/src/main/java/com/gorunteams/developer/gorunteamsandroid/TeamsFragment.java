package com.gorunteams.developer.gorunteamsandroid;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public final static String path6 = "https://restgorun.herokuapp.com/listarRecorridos";
    java.net.URL url;
    String responseText;
    StringBuffer response;
    String respuesta;
    Integer respuesta2;
    ServicioWeb servicio;
    ServicioWeb2 servicio2;
    ServicioWeb3 servicio3;
    ServicioWeb4 servicio4;
    ServicioWeb5 servicio5;
    ServicioWeb6 servicio6;
    public TextView tv;
    public TextView detalleEquipoRow;
    public TextView nombreEquipoRow;
    public TextView jugador1;
    public TextView jugador2;
    public TextView jugador3;
    public TextView jugador4;
    public TextView jugador5;

    public TextView tiempo1;
    public TextView tiempo2;
    public TextView tiempo3;
    public TextView tiempo4;
    public TextView tiempo5;

    public TextView distancia1;
    public TextView distancia2;
    public TextView distancia3;
    public TextView distancia4;
    public TextView distancia5;

    public TextView top1Distancia;
    public TextView top2Distancia;
    public TextView top3Distancia;

    public TextView top1Nombre;
    public TextView top2Nombre;
    public TextView top3Nombre;

    public Integer top1;
    public Integer top2;
    public Integer top3;
    public String ntop1;
    public String ntop2;
    public String ntop3;

    public ArrayList<Persona> personasTop = new ArrayList<Persona>();



    public TextView txtEmail;
    public TextView mensaje3;
    public String txtEmail2;
    public TextView mensajitoError;

    public static String nameEquipo;
    public static String detalleEquipo;
    public static int idTeam;
    public static int idTeam2;
    public static int counter;

    public static String nombreEquipoSolicitado;
    public static String detalleEquipoSolicitado;
    public static int idEquipoSolicitado;
    public static String nombreEquipoSeleccionado;
    public String equipoABuscar;

    String textomail;
    String textname;
    public String tipoUsuario;
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
    public TeamsFragment teamsFr;

    public String date;
    public TextView fechaRecorridos;


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
            tipoUsuario = getArguments().getString("rol");
        }
    }

    private void llenarTxtEquipos() {
        btnequipo1.setText("UNIRTE A EQUIPO");
        //btnequipo1.setBackgroundColor(Color.parseColor("#4B6F63"));
        btnequipo2.setText("UNIRTE A EQUIPO");

        //btnequipo2.setBackgroundColor(Color.parseColor("#4B6F63"));
        btnequipo3.setText("UNIRTE A EQUIPO");
        //btnequipo3.setBackgroundColor(Color.parseColor("#4B6F63"));
        btnequipo4.setText("UNIRTE A EQUIPO");
        //btnequipo4.setBackgroundColor(Color.parseColor("#4B6F63"));
        btnequipo5.setText("UNIRTE A EQUIPO");
        //btnequipo5.setBackgroundColor(Color.parseColor("#4B6F63"));

    }

    private void cambiarColor() {
        if(btnequipo1.getText().equals("UNIRTE A EQUIPO")){
            btnequipo1.setBackground(getResources().getDrawable(R.drawable.buttonbackgroundequipo));
        }
        if(btnequipo2.getText().equals("UNIRTE A EQUIPO")){
            btnequipo2.setBackground(getResources().getDrawable(R.drawable.buttonbackgroundequipo));
        }
        if(btnequipo3.getText().equals("UNIRTE A EQUIPO")){
            btnequipo3.setBackground(getResources().getDrawable(R.drawable.buttonbackgroundequipo));
        }
        if(btnequipo4.getText().equals("UNIRTE A EQUIPO")){
            btnequipo4.setBackground(getResources().getDrawable(R.drawable.buttonbackgroundequipo));
        }
        if(btnequipo5.getText().equals("UNIRTE A EQUIPO")){
            btnequipo5.setBackground(getResources().getDrawable(R.drawable.buttonbackgroundequipo));
        }

    }

    private void verificarRol() {
        Log.d(TAG, "entre a verificar rol");
        Log.d(TAG, "entre a verificar rol"+tipoUsuario);

        if(tipoUsuario.equals("Entrenador") ){

        }else if(tipoUsuario.equals("Estudiante")){
            Log.d(TAG, "si entre en el if");
            llenarTxtEquipos();
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inf = inflater.inflate(R.layout.fragment_teams, container, false);

        date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        Log.d("ddd", "La fecha de hoy es"+date);
        btnequipo1 = (Button) inf.findViewById(R.id.btn1);
        btnequipo2 = (Button) inf.findViewById(R.id.btn2);
        btnequipo3 = (Button) inf.findViewById(R.id.btn3);
        btnequipo4 = (Button) inf.findViewById(R.id.btn4);
        btnequipo5 = (Button) inf.findViewById(R.id.btn5);

        btnequipo1.setEnabled(false);
        btnequipo2.setEnabled(false);
        btnequipo3.setEnabled(false);
        btnequipo4.setEnabled(false);
        btnequipo5.setEnabled(false);

        verificarRol();

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
                }else if(btnequipo1.getText().equals("UNIRTE A EQUIPO")){


                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.unirseaequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btnclose);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnagregar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView equipo = (TextView) popupView.findViewById(R.id.txtnameEquipo);
                            equipoABuscar = String.valueOf(equipo.getText());
                            servicio6= (ServicioWeb6) new ServicioWeb6().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);



                }else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    fechaRecorridos = (TextView) popupView.findViewById(R.id.txtFecha);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    jugador4 = (TextView) popupView.findViewById(R.id.txtNombre4);
                    jugador5 = (TextView) popupView.findViewById(R.id.txtNombre5);

                    tiempo1 = (TextView) popupView.findViewById(R.id.txtTiempo1);
                    tiempo2 = (TextView) popupView.findViewById(R.id.txtTiempo2);
                    tiempo3 = (TextView) popupView.findViewById(R.id.txtTiempo3);
                    tiempo4 = (TextView) popupView.findViewById(R.id.txtTiempo4);
                    tiempo5 = (TextView) popupView.findViewById(R.id.txtTiempo5);

                    distancia1 = (TextView) popupView.findViewById(R.id.txtPuntaje1);
                    distancia2 = (TextView) popupView.findViewById(R.id.txtPuntaje2);
                    distancia3 = (TextView) popupView.findViewById(R.id.txtPuntaje3);
                    distancia4 = (TextView) popupView.findViewById(R.id.txtPuntaje4);
                    distancia5 = (TextView) popupView.findViewById(R.id.txtPuntaje5);

                    top1Distancia = (TextView) popupView.findViewById(R.id.barraJugador1);
                    top2Distancia = (TextView) popupView.findViewById(R.id.barraJugador2);
                    top3Distancia = (TextView) popupView.findViewById(R.id.barraJugador3);

                    top1Nombre = (TextView) popupView.findViewById(R.id.rankJugador1);
                    top2Nombre = (TextView) popupView.findViewById(R.id.rankJugador2);
                    top3Nombre = (TextView) popupView.findViewById(R.id.rankJugador3);


                    nombreEquipoSeleccionado = btnequipo1.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();

                    fechaRecorridos.setText(date);
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
                }else if(btnequipo2.getText().equals("UNIRTE A EQUIPO")){


                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.unirseaequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btnclose);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnagregar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView equipo = (TextView) popupView.findViewById(R.id.txtnameEquipo);
                            equipoABuscar = String.valueOf(equipo.getText());
                            servicio6= (ServicioWeb6) new ServicioWeb6().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);



                }
                else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    fechaRecorridos = (TextView) popupView.findViewById(R.id.txtFecha);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    jugador4 = (TextView) popupView.findViewById(R.id.txtNombre4);
                    jugador5 = (TextView) popupView.findViewById(R.id.txtNombre5);

                    tiempo1 = (TextView) popupView.findViewById(R.id.txtTiempo1);
                    tiempo2 = (TextView) popupView.findViewById(R.id.txtTiempo2);
                    tiempo3 = (TextView) popupView.findViewById(R.id.txtTiempo3);
                    tiempo4 = (TextView) popupView.findViewById(R.id.txtTiempo4);
                    tiempo5 = (TextView) popupView.findViewById(R.id.txtTiempo5);

                    distancia1 = (TextView) popupView.findViewById(R.id.txtPuntaje1);
                    distancia2 = (TextView) popupView.findViewById(R.id.txtPuntaje2);
                    distancia3 = (TextView) popupView.findViewById(R.id.txtPuntaje3);
                    distancia4 = (TextView) popupView.findViewById(R.id.txtPuntaje4);
                    distancia5 = (TextView) popupView.findViewById(R.id.txtPuntaje5);

                    top1Distancia = (TextView) popupView.findViewById(R.id.barraJugador1);
                    top2Distancia = (TextView) popupView.findViewById(R.id.barraJugador2);
                    top3Distancia = (TextView) popupView.findViewById(R.id.barraJugador3);

                    top1Nombre = (TextView) popupView.findViewById(R.id.rankJugador1);
                    top2Nombre = (TextView) popupView.findViewById(R.id.rankJugador2);
                    top3Nombre = (TextView) popupView.findViewById(R.id.rankJugador3);
                    nombreEquipoSeleccionado = btnequipo2.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    fechaRecorridos.setText(date);

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
                                }

                            });

                        }

                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                    Log.d(TAG, "presentando respuesta112"+resAgreIntegrante);
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
                }else if(btnequipo3.getText().equals("UNIRTE A EQUIPO")){


                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.unirseaequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btnclose);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnagregar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView equipo = (TextView) popupView.findViewById(R.id.txtnameEquipo);
                            equipoABuscar = String.valueOf(equipo.getText());
                            servicio6= (ServicioWeb6) new ServicioWeb6().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);



                }
                else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    fechaRecorridos = (TextView) popupView.findViewById(R.id.txtFecha);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    jugador4 = (TextView) popupView.findViewById(R.id.txtNombre4);
                    jugador5 = (TextView) popupView.findViewById(R.id.txtNombre5);

                    tiempo1 = (TextView) popupView.findViewById(R.id.txtTiempo1);
                    tiempo2 = (TextView) popupView.findViewById(R.id.txtTiempo2);
                    tiempo3 = (TextView) popupView.findViewById(R.id.txtTiempo3);
                    tiempo4 = (TextView) popupView.findViewById(R.id.txtTiempo4);
                    tiempo5 = (TextView) popupView.findViewById(R.id.txtTiempo5);

                    distancia1 = (TextView) popupView.findViewById(R.id.txtPuntaje1);
                    distancia2 = (TextView) popupView.findViewById(R.id.txtPuntaje2);
                    distancia3 = (TextView) popupView.findViewById(R.id.txtPuntaje3);
                    distancia4 = (TextView) popupView.findViewById(R.id.txtPuntaje4);
                    distancia5 = (TextView) popupView.findViewById(R.id.txtPuntaje5);

                    top1Distancia = (TextView) popupView.findViewById(R.id.barraJugador1);
                    top2Distancia = (TextView) popupView.findViewById(R.id.barraJugador2);
                    top3Distancia = (TextView) popupView.findViewById(R.id.barraJugador3);

                    top1Nombre = (TextView) popupView.findViewById(R.id.rankJugador1);
                    top2Nombre = (TextView) popupView.findViewById(R.id.rankJugador2);
                    top3Nombre = (TextView) popupView.findViewById(R.id.rankJugador3);
                    nombreEquipoSeleccionado = btnequipo3.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    fechaRecorridos.setText(date);

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
                                }

                            });

                        }

                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                    Log.d(TAG, "presentando respuesta112"+resAgreIntegrante);
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
                }else if(btnequipo4.getText().equals("UNIRTE A EQUIPO")){


                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.unirseaequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btnclose);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnagregar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView equipo = (TextView) popupView.findViewById(R.id.txtnameEquipo);
                            equipoABuscar = String.valueOf(equipo.getText());
                            servicio6= (ServicioWeb6) new ServicioWeb6().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);



                }
                else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    fechaRecorridos = (TextView) popupView.findViewById(R.id.txtFecha);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    jugador4 = (TextView) popupView.findViewById(R.id.txtNombre4);
                    jugador5 = (TextView) popupView.findViewById(R.id.txtNombre5);

                    tiempo1 = (TextView) popupView.findViewById(R.id.txtTiempo1);
                    tiempo2 = (TextView) popupView.findViewById(R.id.txtTiempo2);
                    tiempo3 = (TextView) popupView.findViewById(R.id.txtTiempo3);
                    tiempo4 = (TextView) popupView.findViewById(R.id.txtTiempo4);
                    tiempo5 = (TextView) popupView.findViewById(R.id.txtTiempo5);

                    distancia1 = (TextView) popupView.findViewById(R.id.txtPuntaje1);
                    distancia2 = (TextView) popupView.findViewById(R.id.txtPuntaje2);
                    distancia3 = (TextView) popupView.findViewById(R.id.txtPuntaje3);
                    distancia4 = (TextView) popupView.findViewById(R.id.txtPuntaje4);


                    top1Distancia = (TextView) popupView.findViewById(R.id.barraJugador1);
                    top2Distancia = (TextView) popupView.findViewById(R.id.barraJugador2);
                    top3Distancia = (TextView) popupView.findViewById(R.id.barraJugador3);

                    top1Nombre = (TextView) popupView.findViewById(R.id.rankJugador1);
                    top2Nombre = (TextView) popupView.findViewById(R.id.rankJugador2);
                    top3Nombre = (TextView) popupView.findViewById(R.id.rankJugador3);
                    nombreEquipoSeleccionado = btnequipo4.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    fechaRecorridos.setText(date);

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
                                }

                            });

                        }

                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                    Log.d(TAG, "presentando respuesta112"+resAgreIntegrante);
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
                }else if(btnequipo5.getText().equals("UNIRTE A EQUIPO")){


                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.unirseaequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    Button btn = (Button) popupView.findViewById(R.id.btnclose);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                    Button btn2 = (Button) popupView.findViewById(R.id.btnagregar);
                    btn2.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            TextView equipo = (TextView) popupView.findViewById(R.id.txtnameEquipo);
                            equipoABuscar = String.valueOf(equipo.getText());
                            servicio6= (ServicioWeb6) new ServicioWeb6().execute();
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);



                }
                else{
                    final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.integrantesequipo, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    fechaRecorridos = (TextView) popupView.findViewById(R.id.txtFecha);
                    nombreEquipoRow = (TextView) popupView.findViewById(R.id.txtNomnbreEquipo);
                    detalleEquipoRow = (TextView) popupView.findViewById(R.id.txtDetalleEquipo);
                    jugador1 = (TextView) popupView.findViewById(R.id.txtNombre1);
                    jugador2 = (TextView) popupView.findViewById(R.id.txtNombre2);
                    jugador3 = (TextView) popupView.findViewById(R.id.txtNombre3);
                    jugador4 = (TextView) popupView.findViewById(R.id.txtNombre4);
                    jugador5 = (TextView) popupView.findViewById(R.id.txtNombre5);

                    tiempo1 = (TextView) popupView.findViewById(R.id.txtTiempo1);
                    tiempo2 = (TextView) popupView.findViewById(R.id.txtTiempo2);
                    tiempo3 = (TextView) popupView.findViewById(R.id.txtTiempo3);
                    tiempo4 = (TextView) popupView.findViewById(R.id.txtTiempo4);
                    tiempo5 = (TextView) popupView.findViewById(R.id.txtTiempo5);

                    distancia1 = (TextView) popupView.findViewById(R.id.txtPuntaje1);
                    distancia2 = (TextView) popupView.findViewById(R.id.txtPuntaje2);
                    distancia3 = (TextView) popupView.findViewById(R.id.txtPuntaje3);
                    distancia4 = (TextView) popupView.findViewById(R.id.txtPuntaje4);
                    distancia5 = (TextView) popupView.findViewById(R.id.txtPuntaje5);

                    top1Distancia = (TextView) popupView.findViewById(R.id.barraJugador1);
                    top2Distancia = (TextView) popupView.findViewById(R.id.barraJugador2);
                    top3Distancia = (TextView) popupView.findViewById(R.id.barraJugador3);

                    top1Nombre = (TextView) popupView.findViewById(R.id.rankJugador1);
                    top2Nombre = (TextView) popupView.findViewById(R.id.rankJugador2);
                    top3Nombre = (TextView) popupView.findViewById(R.id.rankJugador3);
                    nombreEquipoSeleccionado = btnequipo5.getText().toString();

                    servicio4 = (ServicioWeb4) new ServicioWeb4().execute();
                    fechaRecorridos.setText(date);

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
                                }

                            });

                        }

                    });
                    popupWindow.showAsDropDown(popupView, 0, 0);
                    Log.d(TAG, "presentando respuesta112"+resAgreIntegrante);
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
                            //jugador1.setText(nombrePersona);
                            String dist1 = this.getWebServiceResponseData4(idPersonaBuscando);
                            //distancia1.setText(dist1);
                            Persona p1 = new Persona(idPersonaBuscando,nombrePersona, Integer.valueOf(dist1));
                            personasTop.add(p1);
                        }
                        if(indexUsuarios == 2){
                            //jugador2.setText(nombrePersona);
                            String dist2 = this.getWebServiceResponseData4(idPersonaBuscando);
                           // distancia2.setText(dist2);
                            Persona p2 = new Persona(idPersonaBuscando,nombrePersona, Integer.valueOf(dist2));
                            //Log.d(TAG, "TROLOLO: " + p2.getNombre());
                           // Log.d(TAG, "TROLOLO: " + p2.getTotal());
                            personasTop.add(p2);
                        }
                        if(indexUsuarios == 3){
                            //jugador3.setText(nombrePersona);
                            String dist3 = this.getWebServiceResponseData4(idPersonaBuscando);
                           // distancia3.setText(dist3);
                            Persona p3 = new Persona(idPersonaBuscando,nombrePersona, Integer.valueOf(dist3));
                            //Log.d(TAG, "TROLOLO: " + p3.getNombre());
                            //Log.d(TAG, "TROLOLO: " + p3.getTotal());
                            personasTop.add(p3);
                        }
                        if(indexUsuarios == 4){
                            //jugador4.setText(nombrePersona);
                            String dist4 = this.getWebServiceResponseData4(idPersonaBuscando);
                            //distancia4.setText(dist4);
                            Persona p4 = new Persona(idPersonaBuscando,nombrePersona, Integer.valueOf(dist4));
                            //Log.d(TAG, "TROLOLO: " + p4.getNombre());
                            //Log.d(TAG, "TROLOLO: " + p4.getTotal());
                            personasTop.add(p4);
                        }
                        if(indexUsuarios == 5){
                            //jugador5.setText(nombrePersona);
                            String dist5 = this.getWebServiceResponseData4(idPersonaBuscando);
                            //distancia5.setText(dist5);
                            Persona p5 = new Persona(idPersonaBuscando,nombrePersona, Integer.valueOf(dist5));
                            personasTop.add(p5);

                        }

                    }else{

                    }
                }

                for(int i = 0; i < personasTop.size() - 1; i++)

                {

                    for(int j = 0; j < personasTop.size() - 1; j++)

                    {

                        if (personasTop.get(j).getTotal() < personasTop.get(j + 1).getTotal())

                        {
                            int indexPlus = j +1;
                            Persona tmp = new Persona(personasTop.get(j + 1));

                            personasTop.set(j+1, personasTop.get(j));

                            personasTop.set(j, tmp);

                        }

                    }

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }



        protected String getWebServiceResponseData4(int idPersonaBuscada) {
            try {
                url=new URL(path6);
                Log.d(TAG, "ServerData: " + path6);
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
            int totalRecorrido = 0;
            int totalTiempo = 0;
            try {

                JSONArray jsonarray = new JSONArray(responseText);
                Log.d(TAG, "LLUVIA" + jsonarray.length());

                for (int i=0;i<jsonarray.length();i++){

                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    int idRow = jsonobject.getInt("idusuario");
                    String fechaRow = jsonobject.getString("fecha");
                    String tiempoRow = jsonobject.getString("tiempo");
                    String distanciaRow = jsonobject.getString("distancia");

                    //int idEquipo = jsonobject.getInt("idequipo");

                    Log.d(TAG, "TRUENO DISTANCIA " + distanciaRow);
                    Log.d(TAG, "TRUENO ENVIADO" + idPersonaBuscada);
                    Log.d(TAG, "TRUENO DEFILAS" + idRow);
                    Log.d(TAG, "+++++++++++++++++++++++++++" + idRow);
                    Log.d(TAG, "TRUENO ACT" + date);
                    Log.d(TAG, "TRUENO FIL" + tiempoRow);
                    Log.d(TAG, "-------------------------" + tiempoRow);
                    if (idPersonaBuscada == idRow && fechaRow.equals(date)){
                        Log.d(TAG, "TORRENTE" + totalRecorrido);
                        totalRecorrido = totalRecorrido + Integer.valueOf(distanciaRow);
                    }else{

                    }
                }
                //Log.d(TAG, "RELAMPAGO" + String.valueOf(totalRecorrido);
                return String.valueOf(totalRecorrido);
                //this.getWebServiceResponseData(idEquipoSolicitado);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return String.valueOf(totalRecorrido);
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

                for(int i = 0; i < personasTop.size(); i++) {
                    if(i == 0){
                        top1Nombre.setText(personasTop.get(i).getNombre());
                        top1Distancia.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                        jugador1.setText(personasTop.get(i).getNombre());
                        distancia1.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                    }

                    if(i == 1){
                        top2Nombre.setText(personasTop.get(i).getNombre());
                        top2Distancia.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                        Log.d(TAG, "mostrando distancia " + String.valueOf(personasTop.get(i).getTotal()+"m"));
                        jugador2.setText(personasTop.get(i).getNombre());
                        distancia2.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                        Log.d(TAG, "mostrando distancia 2 " + personasTop.get(i).getTotal());
                    }
                    if(i == 2){
                        top3Nombre.setText(personasTop.get(i).getNombre());
                        top3Distancia.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                        jugador3.setText(personasTop.get(i).getNombre());
                        distancia3.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                    }

                    if(i == 3){
                        jugador4.setText(personasTop.get(i).getNombre());
                        distancia4.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                    }

                    if(i == 4){
                        jugador5.setText(personasTop.get(i).getNombre());
                        distancia5.setText(String.valueOf(personasTop.get(i).getTotal()+"m"));
                    }
                }
                personasTop.clear();

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
            cambiarColor();
            btnequipo1.setEnabled(true);
            btnequipo2.setEnabled(true);
            btnequipo3.setEnabled(true);
            btnequipo4.setEnabled(true);
            btnequipo5.setEnabled(true);
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
                args.putString("titulo", "Mensaje");
                args.putString("texto", "Se registro su jugador");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getFragmentManager(), "FragmentError");


            }else{

                Bundle args = new Bundle();
                args.putString("titulo", "Mensaje");
                args.putString("texto", "No se pudo registrar su jugador");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getFragmentManager(), "FragmentError");

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
            stringMap.put("identrenador", String.valueOf(idUsuario));

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
                Bundle args = new Bundle();
                args.putString("titulo", "Mensaje");
                args.putString("texto", "Se ha registrado su nuevo equipo");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getFragmentManager(), "FragmentError");


                servicio3 = (ServicioWeb3) new ServicioWeb3(inf).execute();





            }else{
                Bundle args = new Bundle();
                args.putString("titulo", "Mensaje");
                args.putString("texto", "Error al registrar su equipo");
                FragmentError f=new FragmentError();
                f.setArguments(args);
                f.show(getFragmentManager(), "FragmentError");

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



    private class ServicioWeb6 extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            return getWebServiceResponseData();
        }


        protected String getWebServiceResponseData() {
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
            String equipoBuscado= equipoABuscar;
            responseText = response.toString();
            Log.d(TAG, "data:" + responseText);
            try {
                JSONArray jsonarray = new JSONArray(responseText);
                for (int i=0;i<jsonarray.length();i++){
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String nEquipo = jsonobject.getString("nombre");
                    int idEquipo = jsonobject.getInt("idequipo");
                    if (String.valueOf(equipoABuscar).equals(String.valueOf(nEquipo))){
                        idTeam2=idEquipo;
                        this.getWebServiceResponseData2();
                        //respuesta="existe";
                    }else{
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return respuesta;
        }



        protected String getWebServiceResponseData2() {
            respuesta="";

            HttpURLConnection urlConnection = null;
            Map<String, Integer> intMap = new HashMap<>();
            //this.getWebServiceResponseData2(nameEquipo);
            intMap.put("idusuario", idUsuario);
            intMap.put("idequipo", idTeam2);
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




        @Override
        protected void onPostExecute(String respuesta) {
            super.onPostExecute(respuesta);
            Log.d(TAG, "onPostExecute");
            final View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.mensajesequipo, null);
            final PopupWindow popupWindow = new PopupWindow(popupView ,1000,600);
            popupWindow.setFocusable(true);
            TextView texto = (TextView) popupView.findViewById(R.id.txtmessage);
            //popupWindow.showAsDropDown(popupView, 0, 0);
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            servicio3 = (ServicioWeb3) new ServicioWeb3(inf).execute();
            if (respuesta=="correcto"){
                texto.setText("Felicidades has sido agregado al equipo "+equipoABuscar);
            }else{
                texto.setText("Error al registrar");
            }
        }
    }

}

class Persona{
    private String nombre;
    private int id;
    private int total;

    Persona(int id, String nombre, int total){
        this.id = id;
        this.nombre = nombre;
        this.total = total;
    }
    Persona(Persona p1){
        this.id = p1.getId();
        this.nombre = p1.getNombre();
        this.total = p1.getTotal();
    }


    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getTotal() {
        return total;
    }
}