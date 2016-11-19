package asee.unex.es.pizzeriamilenio;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    Button buttonBorrarDatos;
    Button buttonEnviarDatos;
    Spinner sexo;  //buscar pagina en internet
    EditText editTextEmail, editTextPassword, editTextPassword2, editTextFechaNac, editTextUsuario;

    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";

    String actualizar = IP + "/actualizar_personas.php";
    String obtenerPorID = IP + "/obtener_personas_por_id.php";
    String obtenerPorCorreo = IP + "/obtener_personas_por_correo.php";
    String insertar = IP + "/insertar_personas.php";
    String borrar = IP + "/borrar_personas.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
    }


    private void inicializarComponentes() {

        editTextPassword = (EditText) findViewById(R.id.passwordR);
        editTextPassword2 = (EditText) findViewById(R.id.passwordR2);
        editTextFechaNac = (EditText) findViewById(R.id.fecNac);
        editTextUsuario = (EditText) findViewById(R.id.usuario);
        editTextEmail = (EditText) findViewById(R.id.emailRegistro);

        //inicializarSpinner

        buttonBorrarDatos = (Button) findViewById(R.id.buttonBorrarDatos);
        buttonBorrarDatos.setOnClickListener(this);

        buttonEnviarDatos = (Button) findViewById(R.id.buttonEnviarDatos);
        buttonEnviarDatos.setOnClickListener(this);
    }


    private void borrarDatos() {

        editTextPassword.setText("");
        editTextPassword2.setText("");
        editTextFechaNac.setText("");
        editTextUsuario.setText("");
        editTextEmail.setText("");
        //inicializarSpinner
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarDatos:

                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(insertar, "4", editTextUsuario.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextFechaNac.getText().toString());

                    Intent intentPagHome = new Intent();
                    intentPagHome.setClass(Registro.this, Home.class);
                    startActivity(intentPagHome);
                 break;
            case R.id.buttonBorrarDatos:
                    borrarDatos();
                break;
            default:
                break;
        }
    }


    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";


            if (params[1] == "1") {    // Consulta de todos los usuarios

            } else if (params[1] == "2") {    // consulta por id

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON=="1"){      // hay un usuario que mostrar
                            devuelve = devuelve + respuestaJSON.getJSONObject("persona").getString("correo") + " " +
                                    respuestaJSON.getJSONObject("persona").getString("contrasena");

                        }
                        else if (resultJSON=="2"){
                            devuelve = "El usuario no existe";
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            } else if (params[1] == "3") {    // update

            } else if (params[1] == "4") {    // insert
                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ident", params[2]);
                    jsonParam.put("correo", params[3]);
                    jsonParam.put("contrasena", params[4]);
                    jsonParam.put("fechaNac", params[5]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }
                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                         //Accedemos al vector de resultados
                        String a = "{";
                        String b = "}";

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON


                        if (resultJSON == "1") {      // hay un usuario que mostrar
                            devuelve = "Usuario insertado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El usuario no pudo insertarse";
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;


            } else if (params[1] == "5") {    // delete
                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("ident", params[2]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br=new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line=br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay un alumno que mostrar
                            devuelve = "Usuario borrado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "No se ha borrado ningun usuario";
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return devuelve;
            }
            return null;
        }
    }
}

