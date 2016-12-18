package asee.unex.es.pizzeriamilenio.Clases;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import asee.unex.es.pizzeriamilenio.R;

public class AnularReserva extends AppCompatActivity {

    private Button buttonAnularR;
    private EditText editTextNombre;
    private EditText editTextIdentR;
    private String correoMR, identificador;
    asee.unex.es.pizzeriamilenio.Clases.ObtenerWebService hiloconexion;
    AnularReserva.ObtenerWebService hiloconexion2;

    private String IP = "http://aplicacionasee.esy.es";
    private String obtenerReserva = IP + "/obtener_todas_reservas.php";
    private String borrar = IP + "/borrar_reserva.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anular_reserva);

        editTextNombre = (EditText) findViewById(R.id.editTextNombreAnulaR);
        editTextIdentR = (EditText) findViewById(R.id.editTextIdentAnularReserva);

        buttonAnularR = (Button) findViewById(R.id.buttonAnularReserva);
        buttonAnularR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                identificador = editTextIdentR.getText().toString();
                hiloconexion2 = new ObtenerWebService();
                hiloconexion2.execute(obtenerReserva, "1");   // Parámetros que recibe doInBackground
            }
        });
    }

    public AlertDialog crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reserva Anulada");
        builder.setMessage("Su reserva ha sido anulada correctamente.");
        builder.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentReserva = new Intent();
                intentReserva.setClass(AnularReserva.this, MisReservas.class);
                startActivity(intentReserva);
            }
        });
        return builder.create();
    }


    public class ObtenerWebService extends AsyncTask<String, Void, String> {
        Boolean bandera = false;

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;
            String devuelve = "";

            if (params[1] == "1") {    // consulta por ident
                bandera = true;
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
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay usuarios a mostrar
                            JSONArray reservasJSON = respuestaJSON.getJSONArray("reserva");
                            for (int i = 0; i < reservasJSON.length(); i++) {
                                if (reservasJSON.getJSONObject(i).getString("ident").equals(identificador)) {
                                    devuelve = reservasJSON.getJSONObject(i).getString("correo");
                                }
                            }
                            if (devuelve.length() == 0) {
                                devuelve = "2222";
                            }
                        } else if (resultJSON == "2") {
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

        @Override
        protected void onPostExecute(String s) {
                SharedPreferences p = getSharedPreferences("datos", Context.MODE_PRIVATE);
                correoMR = p.getString("mail", "");
                if (!s.equals("2222")) {
                    if (s.equals(correoMR)) {
                        hiloconexion = new asee.unex.es.pizzeriamilenio.Clases.ObtenerWebService();
                        hiloconexion.execute(borrar, "6", editTextIdentR.getText().toString());   // Parámetros que recibe doInBackground

                        crearDialogo().show();
                    }
                } else {
                    Toast toast = Toast.makeText(AnularReserva.this, "Numero de Reserva Incorrecto", Toast.LENGTH_SHORT);
                    toast.show();
            }
        }
    }
}