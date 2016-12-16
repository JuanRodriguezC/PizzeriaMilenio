package asee.unex.es.pizzeriamilenio.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import asee.unex.es.pizzeriamilenio.R;


/**
 * Created by juan on 30/10/16.
 */

public class FragmentForo2 extends Fragment {

    private ListView lista;
    private ArrayList<Mensaje> coleccionDatos1;
    ObtenerWebService hiloconexion;
    private Button buttonActualizar;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String obtenerMensajes = IP + "/obtener_todos_mensajes.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foro2,container,false);

        lista = (ListView) view.findViewById(R.id.listaMensajes);

        coleccionDatos1 = new ArrayList<Mensaje>();

        hiloconexion = new ObtenerWebService();
        hiloconexion.execute(obtenerMensajes,"1");

        buttonActualizar = (Button) view.findViewById(R.id.buttonActualizartabla);
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                coleccionDatos1.removeAll(coleccionDatos1);
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(obtenerMensajes,"1");
            }
        });

        return view;
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";


            if (params[1] == "1") {    // Consulta de todos los usuarios
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

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

                        if (resultJSON=="1"){      // hay mensajes a mostrar
                            JSONArray mensajesJSON = respuestaJSON.getJSONArray("foros");   // estado es el nombre del campo en el JSON


                            for(int i=0;i<mensajesJSON.length();i++){
                                coleccionDatos1.add(new Mensaje(mensajesJSON.getJSONObject(i).getString("Usuario"), mensajesJSON.getJSONObject(i).getString("Mensaje")));
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
            }
            return null;
        }


        protected void onPostExecute(String s) {
            MensajeAdapter adapter = new MensajeAdapter(getActivity(), coleccionDatos1);
            lista.setAdapter(adapter);
        }
    }

    public class Mensaje {
        public String titulo;
        public String texto;

        public Mensaje(String titulo, String texto) {
            this.titulo = titulo;
            this.texto = texto;
        }
    }

    public class MensajeAdapter extends ArrayAdapter<Mensaje> {
        public MensajeAdapter(Context context, ArrayList<Mensaje> m) {
            super(context, 0, m);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Mensaje m = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_user, parent, false);
            }

            TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
            TextView texto = (TextView) convertView.findViewById(R.id.texto);

            titulo.setText(m.titulo);
            texto.setText(m.texto);

            return convertView;
        }
    }
}