package asee.unex.es.pizzeriamilenio.Clases;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import asee.unex.es.pizzeriamilenio.R;

public class Foro extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEnviarMensaje;
    EditText editTextTemaForo, editTextMensaje;
    asee.unex.es.pizzeriamilenio.Clases.ObtenerWebService hiloconexion;
    ObtenerWebService hiloconexion2;
    private ListView lista;
    private ArrayList<Mensaje> coleccionDatos1;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String insertarMensaje = IP + "/insertar_mensaje.php";
    String obtenerMensajes = IP + "/obtener_todos_mensajes.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        lista = (ListView) findViewById(R.id.listaMensajes);
        coleccionDatos1 = new ArrayList<Mensaje>();

        buttonEnviarMensaje = (Button) findViewById(R.id.buttonEnviarMensaje);
        buttonEnviarMensaje.setOnClickListener(this);
        editTextTemaForo = (EditText) findViewById(R.id.editTextTemaForo);
        editTextMensaje = (EditText) findViewById(R.id.editTextMensaje);

        hiloconexion = new asee.unex.es.pizzeriamilenio.Clases.ObtenerWebService();
        hiloconexion2 = new ObtenerWebService();
        hiloconexion2.execute(obtenerMensajes, "1");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarMensaje:
                if (editTextTemaForo.getText().toString().length() != 0 && editTextMensaje.getText().toString().length() != 0) {

                    hiloconexion.execute(insertarMensaje, "5", editTextTemaForo.getText().toString(), editTextMensaje.getText().toString());

                    editTextTemaForo.setText("");
                    editTextMensaje.setText("");

                    Toast toast = Toast.makeText(this, "Mensaje Enviado", Toast.LENGTH_SHORT);
                    toast.show();

                    hiloconexion2=new ObtenerWebService();
                    coleccionDatos1.removeAll(coleccionDatos1);
                    hiloconexion2.execute(obtenerMensajes, "1");

                } else {
                    Toast toast = Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT);
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

            if (params[1] == "1") {             //Obtener mensajes
                 try {
                   url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");

                        if (resultJSON == "1") {      // hay usuarios a mostrar
                            JSONArray mensajesJSON = respuestaJSON.getJSONArray("foros");   // estado es el nombre del campo en el JSON
                            for (int i = 0; i < mensajesJSON.length(); i++) {
                                coleccionDatos1.add(new Mensaje(mensajesJSON.getJSONObject(i).getString("Usuario"), mensajesJSON.getJSONObject(i).getString("Mensaje")));
                            }


                        } else if (resultJSON == "2") {
                            devuelve = "No hay mensajes";
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
            MensajeAdapter adapter = new MensajeAdapter(getApplication(), coleccionDatos1);
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
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_user, parent, false);
            }

            TextView titulo = (TextView) convertView.findViewById(R.id.titulo);
            TextView texto = (TextView) convertView.findViewById(R.id.texto);

            titulo.setText(m.titulo);
            texto.setText(m.texto);

            return convertView;
        }
    }
}