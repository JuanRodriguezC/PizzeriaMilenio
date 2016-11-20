package asee.unex.es.pizzeriamilenio.Clases;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import asee.unex.es.pizzeriamilenio.R;

public class Reserva extends AppCompatActivity{

    private Button buttonReserva;
    private EditText editTextNombre, editTextNumContacto, editTextFecha, editTextNumComen, editTextHora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextNumContacto = (EditText) findViewById(R.id.editTextNumTfono);
        editTextFecha = (EditText) findViewById(R.id.editTextFecha);
        editTextNumComen = (EditText) findViewById(R.id.editTextNumComensales);
        editTextHora = (EditText) findViewById(R.id.editTextHoraReserva);

        buttonReserva = (Button) findViewById(R.id.buttonEnviarReserva);
        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(camposRellenos()) {
                    if(numCorrecto()) {
                        crearDialogo().show();
                        borrarDatos();
                    }
                }else{
                    Toast toast = Toast.makeText(Reserva.this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    private void borrarDatos() {

        editTextNombre.setText("");
        editTextNumContacto.setText("");
        editTextFecha.setText("");
        editTextHora.setText("");
        editTextNumComen.setText("");
    }

    public AlertDialog crearDialogo(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reserva Realizada");
        builder.setMessage("Reserva de " + editTextNombre.getText().toString() + " para el día "
                + editTextFecha.getText().toString() + " a la hora " + editTextHora.getText().toString()
                + "\nNos Vemos Pronto!");
        builder.setNegativeButton("Volver",null);
        return builder.create();
    }

    public boolean camposRellenos(){
        if(editTextNombre.getText().toString().length() != 0 && editTextNumContacto.getText().toString().length() !=0 &&
                editTextFecha.getText().toString().length() != 0 && editTextNumComen.getText().toString().length() != 0 &&
                editTextHora.getText().toString().length() != 0){
            return true;
        }else{
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
}
