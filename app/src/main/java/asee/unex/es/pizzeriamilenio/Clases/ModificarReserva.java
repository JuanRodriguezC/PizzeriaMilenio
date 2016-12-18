package asee.unex.es.pizzeriamilenio.Clases;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
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
import java.util.Calendar;
import java.util.Date;

import asee.unex.es.pizzeriamilenio.R;


public class ModificarReserva extends AppCompatActivity implements View.OnClickListener {

    private Button buttonBuscarReserva;
    private Button buttonModificarReservas;
    private EditText editTextNombreMR, editTextNumContactoMR, editTextNumComenMR, editTextIdentMR;
    private static EditText editTextFechaMR, editTextHoraMR;
    private String correoMR, identificador;

    private int SEVEN_DAYS = 604800000;
    private static String timeString;
    private static String dateString;
    private Date mDate;

    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String obtenerReserva = IP + "/obtener_todas_reservas.php";
    String modificarReserva = IP + "/modificar_reservas.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_reserva);

        editTextIdentMR = (EditText) findViewById(R.id.editTextIdentMR);
        editTextNombreMR = (EditText) findViewById(R.id.editTextNombre);
        editTextNumContactoMR = (EditText) findViewById(R.id.editTextNumTfono);
        editTextFechaMR = (EditText) findViewById(R.id.editTextFecha);
        editTextNumComenMR = (EditText) findViewById(R.id.editTextNumComensales);
        editTextHoraMR = (EditText) findViewById(R.id.editTextHoraReserva);

        buttonBuscarReserva = (Button) findViewById(R.id.buttonBuscarReserva);
        buttonBuscarReserva.setOnClickListener(this);

        buttonModificarReservas = (Button) findViewById(R.id.buttonModificarReserva);
        buttonModificarReservas.setOnClickListener(this);


        editTextFechaMR = (EditText) findViewById(R.id.editTextFechaMR);
        editTextFechaMR.setOnClickListener(this);

        editTextHoraMR = (EditText) findViewById(R.id.editTextHoraReservaMR);
        editTextHoraMR.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBuscarReserva:
                identificador=editTextIdentMR.getText().toString();
                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(obtenerReserva, "1");   // Parámetros que recibe doInBackground

                break;
            case R.id.buttonModificarReserva:
                if (camposRellenos()) {
                    if (numCorrecto()) {
                        if (editTextIdentMR.getText().toString().length() != 0) {
                            identificador = editTextIdentMR.getText().toString();
                            hiloconexion = new ObtenerWebService();
                            hiloconexion.execute(modificarReserva, "2", identificador, editTextNombreMR.getText().toString(), editTextNumContactoMR.getText().toString(), editTextFechaMR.getText().toString(), editTextHoraMR.getText().toString(), editTextNumComenMR.getText().toString(), correoMR);
                            crearDialogo().show();
                        }
                    }
                } else {
                    Toast toast = Toast.makeText(ModificarReserva.this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.editTextFecha:
                editTextFechaMR.setInputType(InputType.TYPE_NULL);
                showDatePickerDialog();
                break;
            case R.id.editTextHoraReserva:
                editTextHoraMR.setInputType(InputType.TYPE_NULL);
                showTimePickerDialog();
                break;
            default:
                break;
        }
    }

    public AlertDialog crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reserva Modificada");
        builder.setMessage("Reserva de " + editTextNombreMR.getText().toString() + " para el día "
                + editTextFechaMR.getText().toString() + " a la hora " + editTextHoraMR.getText().toString()
                + "\nNos Vemos Pronto!");
        builder.setNegativeButton("Volver",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentReserva = new Intent();
                intentReserva.setClass(ModificarReserva.this, MisReservas.class);
                startActivity(intentReserva);
            }
        });
        return builder.create();
    }

    public boolean camposRellenos() {
        if (editTextNombreMR.getText().toString().length() != 0 && editTextNumContactoMR.getText().toString().length() != 0 &&
                editTextFechaMR.getText().toString().length() != 0 && editTextNumComenMR.getText().toString().length() != 0 &&
                editTextHoraMR.getText().toString().length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean numCorrecto() {
        if (editTextNumContactoMR.getText().toString().length() == 9) {
            return true;
        } else {
            Toast toast = Toast.makeText(ModificarReserva.this, "Número de teléfono Incorrecto", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    private void showDatePickerDialog() {
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getFragmentManager(), "datepicker");
    }

    private void showTimePickerDialog() {
        DialogFragment dialog = new TimePickerFragment();
        dialog.show(getFragmentManager(), "timepicker");
    }


    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = day + "-" + mon + "-" + year;
    }

    private static void setTimeString(int hourOfDay, int minute) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min;
    }


    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            editTextFechaMR.setText(dateString);
        }
    }


    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute);

            editTextHoraMR.setText(timeString);
        }
    }


    public class ObtenerWebService extends AsyncTask<String, Void, String>{
        Boolean bandera = false;
        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;
            String devuelve = "";

            if (params[1] == "1") {    // consulta por ident
                bandera=true;
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){

                        InputStream in = new BufferedInputStream(connection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        String resultJSON = respuestaJSON.getString("estado");

                        if (resultJSON=="1"){
                            JSONArray reservasJSON = respuestaJSON.getJSONArray("reserva");
                            for(int i=0;i<reservasJSON.length();i++){
                                if(reservasJSON.getJSONObject(i).getString("ident").equals(identificador)) {
                                    devuelve=devuelve+reservasJSON.getJSONObject(i).getString("nombre")+"+"+reservasJSON.getJSONObject(i).getString("tfno")+"+"+reservasJSON.getJSONObject(i).getString("fecha")+"+"+reservasJSON.getJSONObject(i).getString("hora")+"+"+reservasJSON.getJSONObject(i).getString("numComensales")+"+"+reservasJSON.getJSONObject(i).getString("correo");
                                }
                            }
                            if(devuelve.length() == 0){
                                devuelve="2222";
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

            } else if (params[1] == "2") {    // update
                bandera=false;
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
                    jsonParam.put("nombre", params[3]);
                    jsonParam.put("tfno", params[4]);
                    jsonParam.put("fecha", params[5]);
                    jsonParam.put("hora", params[6]);
                    jsonParam.put("numComensales", params[7]);
                    jsonParam.put("correo", params[8]);

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
            if(bandera == true) {
                SharedPreferences p = getSharedPreferences("datos", Context.MODE_PRIVATE);
                correoMR = p.getString("mail", "");
                if (!s.equals("2222")) {
                    String[] valores = s.split("\\+");

                    if (valores[5].equals(correoMR)) {
                        editTextNombreMR = (EditText) findViewById(R.id.editTextNombreMR);
                        editTextNombreMR.setText(valores[0]);
                        editTextNumContactoMR = (EditText) findViewById(R.id.editTextNumTfonoMR);
                        editTextNumContactoMR.setText(valores[1]);
                        editTextFechaMR.setText(valores[2]);
                        editTextHoraMR.setText(valores[3]);
                        editTextNumComenMR= (EditText) findViewById(R.id.editTextNumComensalesMR);
                        editTextNumComenMR.setText(valores[4]);
                    }
                } else {
                    Toast toast = Toast.makeText(ModificarReserva.this, "Numero de Reserva Incorrecto", Toast.LENGTH_SHORT);
                    toast.show();
                    editTextIdentMR.setText("");
                    editTextNombreMR.setText("");
                    editTextNumContactoMR.setText("");
                    editTextFechaMR.setText("");
                    editTextHoraMR.setText("");
                    editTextNumComenMR.setText("");
                }
            }
        }
    }
}