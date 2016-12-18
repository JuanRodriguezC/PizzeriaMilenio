package asee.unex.es.pizzeriamilenio.Clases;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Menu;

import asee.unex.es.pizzeriamilenio.R;


public class Home extends AppCompatActivity {

    private Button buttonComida, buttonOfertas, buttonReserva, buttonLocalizacion, buttonForo;
    private AlertDialog alert = null;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonComida = (Button) findViewById(R.id.buttonComida);
        buttonComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentComida = new Intent();
                intentComida.setClass(Home.this,Comida.class);
                startActivity(intentComida);
            }
        });

        buttonReserva = (Button) findViewById(R.id.buttonReserva);
        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReserva = new Intent();
                intentReserva.setClass(Home.this,Reserva.class);
                startActivity(intentReserva);
            }
        });

        buttonOfertas = (Button) findViewById(R.id.buttonOfertas);
        buttonOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOfertas = new Intent();
                intentOfertas.setClass(Home.this,Ofertas.class);
                startActivity(intentOfertas);
            }
        });



        buttonLocalizacion = (Button) findViewById(R.id.buttonLocalizacion);
        buttonLocalizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(isOnline()) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    alertGPSDesactivado();
                } else {
                    Intent intentLocalizacion = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:38.863257773424785,-6.102275691347359?z=16&q=38.863257773424785,-6.102275691347359(PizzeriaMilenio)"));
                    intentLocalizacion.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intentLocalizacion);
                }
            }else{
                alertTraficoDatosDesactivado();
            }
            }
        });

        buttonForo = (Button) findViewById(R.id.buttonForo);
        buttonForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForo = new Intent();
                intentForo.setClass(Home.this,Foro.class);  //cambiar
                startActivity(intentForo);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Context context = getApplicationContext();
        CharSequence text = null;
        int duration = Toast.LENGTH_SHORT;

        if (id == R.id.action_settings) {
            text="Opcion Settings";
            Intent intentAjustes = new Intent();
            intentAjustes.setClass(Home.this,Ajustes.class);
            startActivity(intentAjustes);
        }
        return super.onOptionsItemSelected(item);
    }

    private void alertGPSDesactivado(){
        final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS está desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(@SuppressWarnings("unusued") final DialogInterface dialog,@SuppressWarnings("unusued")final int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        })
                .setNegativeButton("No",new DialogInterface.OnClickListener(){
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unusued") final int id){
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    private void alertTraficoDatosDesactivado(){
        final  AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El tráfico de datos está desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unusued") final DialogInterface dialog,@SuppressWarnings("unusued")final int id) {
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener(){
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unusued") final int id){
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
