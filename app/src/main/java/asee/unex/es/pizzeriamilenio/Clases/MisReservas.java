package asee.unex.es.pizzeriamilenio.Clases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import asee.unex.es.pizzeriamilenio.R;


/**
 * Created by juan on 30/10/16.
 */

public class MisReservas extends AppCompatActivity {

    private String correo;
    private ListView lista;
    private ArrayList<TxtReserva> coleccionDatos1;

    MisReservas.ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String obtenerReservas = IP + "/obtener_todas_reservas.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        lista = (ListView) findViewById(R.id.listareservas);

        coleccionDatos1 = new ArrayList<TxtReserva>();

        SharedPreferences p = getSharedPreferences("datos", Context.MODE_PRIVATE);
        correo = p.getString("mail", "");

        hiloconexion = new ObtenerWebService();
        hiloconexion.execute(obtenerReservas,"1");   // Parámetros que recibe doInBackground
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_borrarR) {
            Intent intentAnularR = new Intent();
            intentAnularR.setClass(this, AnularReserva.class);
            startActivity(intentAnularR);
        }
        if (id == R.id.action_modificarR) {
            Intent intentModificarReserva = new Intent();
            intentModificarReserva.setClass(this, ModificarReserva.class);
            startActivity(intentModificarReserva);
        }
        if (id == R.id.action_menu) {
            Intent intentMenu = new Intent();
            intentMenu.setClass(this, Home.class);
            startActivity(intentMenu);
        }
        return super.onOptionsItemSelected(item);
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            if (params[1] == "1") {    // obtenerReservas
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

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON=="1"){      // hay usuarios a mostrar
                            JSONArray reservasJSON = respuestaJSON.getJSONArray("reserva");
                            for(int i=0;i<reservasJSON.length();i++){
                                if(reservasJSON.getJSONObject(i).getString("correo").equals(correo)) {
                                    coleccionDatos1.add(new TxtReserva(reservasJSON.getJSONObject(i).getString("nombre"), reservasJSON.getJSONObject(i).getString("fecha"), reservasJSON.getJSONObject(i).getString("hora"), reservasJSON.getJSONObject(i).getString("numComensales"),reservasJSON.getJSONObject(i).getString("ident")));
                                }
                            }
                       }
                        else if (resultJSON=="2") {
                            devuelve = "No hay reservas";
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
            ReservaAdapter adapter = new ReservaAdapter(getApplication(), coleccionDatos1);
            lista.setAdapter(adapter);
        }
    }

    public class TxtReserva {
        public String nombreR;
        public String fechaR;
        public String horaR;
        public String numComensalesR, identR;

        public TxtReserva(String nombre, String fecha, String hora, String numComensales, String ident) {
            this.nombreR = nombre;
            this.fechaR = fecha;
            this.horaR = hora;
            this.numComensalesR = numComensales;
            this.identR=ident;
        }
    }

    public class ReservaAdapter extends ArrayAdapter<TxtReserva> {
        public ReservaAdapter(Context context, ArrayList<TxtReserva> r) {
            super(context, 0, r);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TxtReserva r = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.disenio_ver_reserva, parent, false);
            }

            TextView nombre = (TextView) convertView.findViewById(R.id.nombreR);
            TextView fecha = (TextView) convertView.findViewById(R.id.fechaR);
            TextView hora = (TextView) convertView.findViewById(R.id.horaR);
            TextView numComensales = (TextView) convertView.findViewById(R.id.numComenR);
            TextView ident = (TextView) convertView.findViewById(R.id.identR);

            nombre.setText("Nombre Reserva: " +r.nombreR);
            fecha.setText("Fecha: " +r.fechaR);
            hora.setText("Hora: "+r.horaR);
            numComensales.setText("Comensales: "+r.numComensalesR);
            ident.setText("Identificador de la Reserva: " + r.identR);

            return convertView;
        }
    }

}