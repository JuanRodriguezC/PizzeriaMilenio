package asee.unex.es.pizzeriamilenio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static android.R.attr.id;

public class Home extends AppCompatActivity {

    private Button buttonComida, buttonOfertas, buttonReserva, buttonLocalizacion, buttonForo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonComida = (Button) findViewById(R.id.buttonComida);
        buttonComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentComida = new Intent();
                intentComida.setClass(Home.this,Menu.class);  //cambiar
                startActivity(intentComida);
            }
        });
/*
        buttonReserva = (Button) findViewById(R.id.buttonReserva);
        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReserva = new Intent();
                intentReserva.setClass(Home.this,Registro.class);  //cambiar
                startActivity(intentReserva);
            }
        });
*/
        buttonOfertas = (Button) findViewById(R.id.buttonOfertas);
        buttonOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOfertas = new Intent();
                intentOfertas.setClass(Home.this,Ofertas.class);  //cambiar
                startActivity(intentOfertas);
            }
        });



        buttonLocalizacion = (Button) findViewById(R.id.buttonLocalizacion);
        buttonLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Intent intentLocalizacion = new Intent();
        //        intentLocalizacion.setClass(Home.this,Registro.class);  //cambiar
        //        startActivity(intentLocalizacion);
                Intent intentLocalizacion = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:38.863257773424785,-6.102275691347359?z=16&q=38.863257773424785,-6.102275691347359(PizzeriaMilenio)"));
                intentLocalizacion.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intentLocalizacion);
            }
        });
/*
        buttonForo = (Button) findViewById(R.id.buttonForo);
        buttonForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForo = new Intent();
                intentForo.setClass(Home.this,Registro.class);  //cambiar
                startActivity(intentForo);
            }
        });*/
    }
}
