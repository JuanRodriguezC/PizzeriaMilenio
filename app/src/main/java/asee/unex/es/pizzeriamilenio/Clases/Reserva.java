package asee.unex.es.pizzeriamilenio.Clases;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import asee.unex.es.pizzeriamilenio.R;


public class Reserva extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEnviarReserva;
    private Button buttonMisReservas;
    private EditText editTextNombre, editTextNumContacto, editTextNumComen;
    private static EditText editTextFecha, editTextHora;
    private String correo;

    private static String timeString;
    private static String dateString;
    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String insertarReserva = IP + "/insertar_reserva.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextNumContacto = (EditText) findViewById(R.id.editTextNumTfono);
        editTextNumComen = (EditText) findViewById(R.id.editTextNumComensales);

        SharedPreferences p = getSharedPreferences("datos", Context.MODE_PRIVATE);
        correo = p.getString("mail", "");

        buttonEnviarReserva = (Button) findViewById(R.id.buttonEnviarReserva);
        buttonEnviarReserva.setOnClickListener(this);

        buttonMisReservas = (Button) findViewById(R.id.buttonMisReservas);
        buttonMisReservas.setOnClickListener(this);

        editTextFecha = (EditText) findViewById(R.id.editTextFecha);
        editTextFecha.setOnClickListener(this);

        editTextHora = (EditText) findViewById(R.id.editTextHoraReserva);
        editTextHora.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarReserva:
                if (camposRellenos()) {
                    if (numCorrecto()) {

                        hiloconexion = new ObtenerWebService();
                        hiloconexion.execute(insertarReserva, "7", editTextNombre.getText().toString(), editTextNumContacto.getText().toString(), editTextFecha.getText().toString(), editTextHora.getText().toString(), editTextNumComen.getText().toString(), correo.toString());

                        crearDialogo().show();
                        borrarDatos();
                    }
                } else {
                    Toast toast = Toast.makeText(Reserva.this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.buttonMisReservas:

                Intent intentVerReservas = new Intent();
                intentVerReservas.setClass(Reserva.this, MisReservas.class);
                startActivity(intentVerReservas);

                break;
            case R.id.editTextFecha:
                editTextFecha.setInputType(InputType.TYPE_NULL);
                showDatePickerDialog();
                break;
            case R.id.editTextHoraReserva:
                editTextHora.setInputType(InputType.TYPE_NULL);
                showTimePickerDialog();
                break;
            default:
                break;
        }
    }


    private void borrarDatos() {

        editTextNombre.setText("");
        editTextNumContacto.setText("");
        editTextFecha.setText("");
        editTextHora.setText("");
        editTextNumComen.setText("");
    }

    public AlertDialog crearDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reserva Realizada");
        builder.setMessage("Reserva de " + editTextNombre.getText().toString() + " para el día "
                + editTextFecha.getText().toString() + " a la hora " + editTextHora.getText().toString()
                + "\nNos Vemos Pronto!");
        builder.setNegativeButton("Volver", null);
        return builder.create();
    }

    public boolean camposRellenos() {
        if (editTextNombre.getText().toString().length() != 0 && editTextNumContacto.getText().toString().length() != 0 &&
                editTextFecha.getText().toString().length() != 0 && editTextNumComen.getText().toString().length() != 0 &&
                editTextHora.getText().toString().length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean numCorrecto() {
        if (editTextNumContacto.getText().toString().length() == 9) {
            return true;
        } else {
            Toast toast = Toast.makeText(Reserva.this, "Número de teléfono Incorrecto", Toast.LENGTH_SHORT);
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

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            editTextFecha.setText(dateString);
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

            editTextHora.setText(timeString);
        }
    }

}