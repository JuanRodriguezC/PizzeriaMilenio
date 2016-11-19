package asee.unex.es.pizzeriamilenio;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static asee.unex.es.pizzeriamilenio.R.id.buttonBorrarDatos;
import static asee.unex.es.pizzeriamilenio.R.id.editTextTemaForo;
import static asee.unex.es.pizzeriamilenio.R.id.item_touch_helper_previous_elevation;


/**
 * Created by juan on 30/10/16.
 */

public class FragmentForo1 extends Fragment implements View.OnClickListener {

    private Button buttonEnviarMensaje;
    EditText editTextTemaForo, editTextMensaje;
    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String insertarMensaje = IP + "/insertar_mensaje.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        View view = inflater.inflate(R.layout.fragment_foro1,container,false);

        buttonEnviarMensaje = (Button) view.findViewById(R.id.buttonEnviarMensaje);
        buttonEnviarMensaje.setOnClickListener(this);
        editTextTemaForo = (EditText) view.findViewById(R.id.editTextTemaForo);
        editTextMensaje = (EditText) view.findViewById(R.id.editTextMensaje);


        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarMensaje:
                if(editTextTemaForo.getText().toString().length() !=0 && editTextMensaje.getText().toString().length()!=0){
                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(insertarMensaje, "4", editTextTemaForo.getText().toString(), editTextMensaje.getText().toString());

                    editTextTemaForo.setText("");
                    editTextMensaje.setText("");

                    Toast toast = Toast.makeText(this.getActivity(), "Mensaje Enviado", Toast.LENGTH_SHORT);
                    toast.show();


                }else{
                    Toast toast = Toast.makeText(this.getActivity(), "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
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

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON


                        if (resultJSON=="1"){      // hay alumnos a mostrar
                            JSONArray mensajesJSON = respuestaJSON.getJSONArray("Id");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<mensajesJSON.length();i++){
                                devuelve = devuelve + mensajesJSON.getJSONObject(i).getString("Usuario") + " " +
                                        mensajesJSON.getJSONObject(i).getString("Mensaje") + " " + "\n";
                            }
                        }
                        else if (resultJSON=="2"){
                            devuelve = "No hay alumnos";
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

            } else if (params[1] == "2") {    // consulta por id

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
                    jsonParam.put("Usuario", params[2]);
                    jsonParam.put("Mensaje", params[3]);
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
                        //Accedemos al vector de resultado

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON


                        if (resultJSON == "1") {      // hay un usuario que mostrar
                            devuelve = "Mensaje insertado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "ERROR al enviar el mensaje";
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
            }
            return null;
        }
    }
}