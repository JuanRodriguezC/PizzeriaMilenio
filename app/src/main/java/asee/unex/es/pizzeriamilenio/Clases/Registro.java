package asee.unex.es.pizzeriamilenio.Clases;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import asee.unex.es.pizzeriamilenio.R;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    Button buttonBorrarDatos;
    Button buttonEnviarDatos;
    EditText editTextEmail, editTextPassword, editTextPassword2, editTextUsuario;
    private static EditText editTextFechaNac;
    private static String dateString;

    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String insertar = IP + "/insertar_personas.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        editTextPassword = (EditText) findViewById(R.id.passwordR);
        editTextPassword2 = (EditText) findViewById(R.id.passwordR2);
        editTextUsuario = (EditText) findViewById(R.id.usuario);
        editTextEmail = (EditText) findViewById(R.id.emailRegistro);

        buttonBorrarDatos = (Button) findViewById(R.id.buttonBorrarDatos);
        buttonBorrarDatos.setOnClickListener(this);

        buttonEnviarDatos = (Button) findViewById(R.id.buttonEnviarDatos);
        buttonEnviarDatos.setOnClickListener(this);

        editTextFechaNac = (EditText) findViewById(R.id.fecNac);
        editTextFechaNac.setOnClickListener(this);
    }


    private void borrarDatos() {
        editTextPassword.setText("");
        editTextPassword2.setText("");
        editTextFechaNac.setText("");
        editTextUsuario.setText("");
        editTextEmail.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarDatos:
                if(camposRellenos()) {
                    if(validezPassword() && emailValido()){
                        hiloconexion = new ObtenerWebService();
                        hiloconexion.execute(insertar, "4", editTextUsuario.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextFechaNac.getText().toString());

                        Intent intentPagHome = new Intent();
                        intentPagHome.setClass(Registro.this, Home.class);
                        startActivity(intentPagHome);
                        finish();
                    }
                }else{
                    Toast toast = Toast.makeText(Registro.this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.buttonBorrarDatos:
                borrarDatos();
                break;
            case R.id.fecNac:
                editTextFechaNac.setInputType(InputType.TYPE_NULL);
                showDatePickerDialog();
                break;
            default:
                break;
        }
    }

    private void showDatePickerDialog() {
        DialogFragment dialog = new Registro.DatePickerFragment();
        dialog.show(getFragmentManager(), "datepicker");
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

    public boolean camposRellenos(){
        if(editTextUsuario.getText().toString().length() != 0 && editTextEmail.getText().toString().length() !=0 &&
                editTextPassword.getText().toString().length() != 0 && editTextPassword2.getText().toString().length() != 0 &&
                editTextFechaNac.getText().toString().length() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean validezPassword(){
        boolean bandera=false;
        if(editTextPassword.getText().toString().length() > 4){
            if(editTextPassword.getText().toString().compareTo(editTextPassword2.getText().toString()) == 0){
                bandera=true;
            }else{
                Toast toast = Toast.makeText(Registro.this, "Las contraseñas no coinciden",
                        Toast.LENGTH_SHORT);
                toast.show();
                bandera=false;
            }
        }else{
            Toast toast = Toast.makeText(Registro.this, "El tamaño de la contraseña debe ser mayor de 4 caracteres",
                    Toast.LENGTH_SHORT);
            toast.show();
                bandera=false;
        }
        return bandera;
    }

    private boolean emailValido() {
        if(editTextEmail.getText().toString().contains("@")){
            return true;
        }else{
            Toast toast = Toast.makeText(Registro.this, "Email incorrecto",
                    Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
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
            editTextFechaNac.setText(dateString);
        }
    }
}
