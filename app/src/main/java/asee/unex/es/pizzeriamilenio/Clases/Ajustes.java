package asee.unex.es.pizzeriamilenio.Clases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import asee.unex.es.pizzeriamilenio.Adaptadores.AdaptadorOfertas;
import asee.unex.es.pizzeriamilenio.R;

public class Ajustes extends AppCompatActivity {

    private Button buttonGuardarDatos, buttonCerrarSesion;
    private EditText editTextPNombre, editTextPNumTfono, editTextPHora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        editTextPNombre = (EditText) findViewById(R.id.preferenciaNombre);
        editTextPNumTfono = (EditText) findViewById(R.id.prefTelefonk);
        editTextPHora = (EditText) findViewById(R.id.prefComida);

        SharedPreferences pref = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
        editTextPNombre.setText(pref.getString("nombre",""));
        editTextPNumTfono.setText(pref.getString("telefono",""));
        editTextPHora.setText(pref.getString("hora",""));

        buttonGuardarDatos = (Button) findViewById(R.id.buttonGuardarDatos);
        buttonGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("nombre", editTextPNombre.getText().toString());
                editor.putString("telefono", editTextPNumTfono.getText().toString());
                editor.putString("hora", editTextPHora.getText().toString());

                editor.commit();

                Toast toast = Toast.makeText(Ajustes.this, "Datos Guardados", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        buttonCerrarSesion = (Button) findViewById(R.id.buttonCerrarSesion);
        buttonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mail", null);
                editor.putString("password", null);
                editor.commit();

                SharedPreferences pref = getSharedPreferences("preferencia", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor2 = pref.edit();
                editor2.putString("nombre", null);
                editor2.putString("telefono", null);
                editor2.putString("hora", null);
                editor2.commit();

                startActivity(new Intent(getBaseContext(), IniciarSesion.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            }
        });
    }
}
