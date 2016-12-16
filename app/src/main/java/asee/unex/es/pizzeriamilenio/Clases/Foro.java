/*package asee.unex.es.pizzeriamilenio.Clases;

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
import java.util.ArrayList;

import asee.unex.es.pizzeriamilenio.R;

public class Foro extends AppCompatActivity implements View.OnClickListener{

    private Button buttonEnviarMensaje;
    private Button buttonLeerMensaje;
    EditText editTextTemaForo, editTextMensaje;
    ObtenerWebService hiloconexion;
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

        hiloconexion = new ObtenerWebService();
        lista = (ListView) findViewById(R.id.listaMensajes);

        coleccionDatos1 = new ArrayList<Mensaje>();

        buttonEnviarMensaje = (Button) findViewById(R.id.buttonEnviarMensaje);
        buttonEnviarMensaje.setOnClickListener(this);
        buttonLeerMensaje = (Button) findViewById(R.id.buttonLeerMensaje);
        buttonLeerMensaje.setOnClickListener(this);
        editTextTemaForo = (EditText) findViewById(R.id.editTextTemaForo);
        editTextMensaje = (EditText) findViewById(R.id.editTextMensaje);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarMensaje:
               if(editTextTemaForo.getText().toString().length() !=0 && editTextMensaje.getText().toString().length()!=0){

                    hiloconexion.execute(insertarMensaje, "5", editTextTemaForo.getText().toString(), editTextMensaje.getText().toString());

                    editTextTemaForo.setText("");
                    editTextMensaje.setText("");

                    Toast toast = Toast.makeText(this, "Mensaje Enviado", Toast.LENGTH_SHORT);
                    toast.show();



                //     hiloconexion.execute(obtenerMensajes,"1");
                //      coleccionDatos1.removeAll(coleccionDatos1);
                //    hiloconexion.execute(obtenerMensajes,"1");

                }else{
                    Toast toast = Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.buttonLeerMensaje:
                    coleccionDatos1.removeAll(coleccionDatos1);
                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(obtenerMensajes,"1");
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


            if (params[1] == "1") {
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


                        if (resultJSON=="1"){      // hay usuarios a mostrar
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
        }else if (params[1] == "5") {            //INSERTAR MENSAJE
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
            }
            return null;
        }


        protected void onPostExecute(String s) {
            MensajeAdapter adapter = new MensajeAdapter(getApplicationContext(), coleccionDatos1);
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
            Foro.Mensaje m = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_user, parent, false);/////////
            }

            TextView titulo = (TextView) findViewById(R.id.titulo);
            TextView texto = (TextView) findViewById(R.id.texto);

            titulo.setText(m.titulo);
            texto.setText(m.texto);

            return convertView;
        }
    }
}
*/



package asee.unex.es.pizzeriamilenio.Clases;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import asee.unex.es.pizzeriamilenio.Adaptadores.AdaptadorViewForoPagePrincipal;
import asee.unex.es.pizzeriamilenio.R;

public class Foro extends AppCompatActivity {

    private AdaptadorViewForoPagePrincipal Adaptador_ViewForoPagePrincipal;
    private android.support.v4.view.ViewPager ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        // Iniciamos la barra de herramientas.
        Toolbar toolbar = (Toolbar) findViewById(R.id.ToolbarPrincipal);
        setSupportActionBar(toolbar);

        // Iniciamos la barra de tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayoutPrincipal);

        // Añadimos las 3 tabs de las secciones.
        // Le damos modo "fixed" para que todas las tabs tengan el mismo tamaño. También le asignamos una gravedad centrada.
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        // Iniciamos el viewPager.
        ViewPager = (ViewPager) findViewById(R.id.ViewForoPagerPrincipal);

        // Creamos el adaptador, al cual le pasamos por parámtro el gestor de Fragmentos y muy importante, el nº de tabs o secciones que hemos creado.
        Adaptador_ViewForoPagePrincipal = new AdaptadorViewForoPagePrincipal(getSupportFragmentManager(),tabLayout.getTabCount());

        // Y los vinculamos.
        ViewPager.setAdapter(Adaptador_ViewForoPagePrincipal);

        // Y por último, vinculamos el viewpager con el control de tabs para sincronizar ambos.
        tabLayout.setupWithViewPager(ViewPager);
    }
}