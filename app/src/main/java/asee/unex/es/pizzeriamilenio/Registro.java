package asee.unex.es.pizzeriamilenio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Registro extends AppCompatActivity {

    Button buttonBorrarDatos;
    Button buttonEnviarDatos;
    Spinner sexo;  //buscar pagina en internet
    private AutoCompleteTextView textViewEmail;
    private EditText editTextPassword, editTextPassword2, editTextFechaNac, editTextUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarComponentes();
    }


    private void inicializarComponentes(){

        editTextPassword=(EditText)findViewById(R.id.passwordR);
        editTextPassword2=(EditText)findViewById(R.id.passwordR2);
        editTextFechaNac=(EditText)findViewById(R.id.fecNac);
        editTextUsuario=(EditText)findViewById(R.id.usuario);

        textViewEmail=(AutoCompleteTextView)findViewById(R.id.emailRegistro);

        //inicializarSpinner

        buttonBorrarDatos=(Button)findViewById(R.id.buttonBorrarDatos);
        buttonBorrarDatos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                borrarDatos();
            }
        });


        buttonEnviarDatos=(Button)findViewById(R.id.buttonEnviarDatos);
        buttonEnviarDatos.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                //enviarDatosBD();
                Intent intentPagHome = new Intent();
                intentPagHome.setClass(Registro.this,Home.class);
                startActivity(intentPagHome);
            }
        });
    }


    private void borrarDatos(){

        editTextPassword.setText("");
        editTextPassword2.setText("");
        editTextFechaNac.setText("");
        editTextUsuario.setText("");
        textViewEmail.setText("");
        //inicializarSpinner
    }
}
