package asee.unex.es.pizzeriamilenio.Clases;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_READ_CONTACTS = 0;


    // UI references.
    private AutoCompleteTextView textViewEmail;
    private EditText editTextPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button buttonEntrar, buttonRegistrarse;
    private String mail, p;
    private SharedPreferences preferencias;
    ObtenerWebService hiloconexion;

    String IP = "http://aplicacionasee.esy.es";
    String obtenerPorCorreo = IP + "/obtener_personas_por_correo.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_iniciar_sesion);
        // Set up the login form.
        textViewEmail = (AutoCompleteTextView) findViewById(R.id.email);

        editTextPassword = (EditText) findViewById(R.id.password);
        editTextPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
              //      attemptLogin();
                    return true;
                }
                return false;
            }
        });

        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonEntrar.setOnClickListener(this);

        buttonRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);
        buttonRegistrarse.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        textViewEmail.setText(preferencias.getString("mail",""));
        editTextPassword.setText(preferencias.getString("password",""));

        mail = preferencias.getString("mail",null);
        p = preferencias.getString("password",null);

        if(mail != null && p != null){
            Intent intentPagHome = new Intent();
            intentPagHome.setClass(IniciarSesion.this, Home.class);
            startActivity(intentPagHome);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEntrar:

                if (textViewEmail.getText().toString().length() !=0 && editTextPassword.getText().toString().length()!=0) {
                    if (emailValido() && passwordValida()) {
                            hiloconexion = new ObtenerWebService();
                            String sentencia = obtenerPorCorreo + "?correo=" + textViewEmail.getText().toString();
                            hiloconexion.execute(sentencia, "2");   // Parámetros que recibe doInBackground
                    }
                }else{
                       Toast toast = Toast.makeText(IniciarSesion.this, "Rellene todos los campos", Toast.LENGTH_SHORT);
                       toast.show();
                }
                break;
            case R.id.buttonRegistrarse:
                Intent intentRegistro = new Intent();
                intentRegistro.setClass(IniciarSesion.this,Registro.class);
                startActivity(intentRegistro);
                break;
            default:
                break;
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             //   populateAutoComplete();
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean emailValido() {
        if(textViewEmail.getText().toString().contains("@")){
            return true;
        }else{
            Toast toast = Toast.makeText(IniciarSesion.this, "Email incorrecto",
                    Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    public SharedPreferences getPreferencias() {
        return this.preferencias;
    }

    public boolean passwordValida(){
        if(editTextPassword.getText().toString().length() > 4){
            return true;
        }else{
            Toast toast = Toast.makeText(IniciarSesion.this, "El tamaño de la contraseña debe ser mayor de 4 caracteres",
                    Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    public class ObtenerWebService extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "";


            if (params[1] == "2") {    // consulta por correo

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

                        String resultJSON = respuestaJSON.getString("estado");

                        if (resultJSON=="1"){      // hay un usuario que mostrar
                            devuelve = devuelve + respuestaJSON.getJSONObject("persona").getString("contrasena");
                        }
                        else if (resultJSON=="2"){
                            devuelve = "El usuario no existe";
                            Toast toast = Toast.makeText(IniciarSesion.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT);
                            toast.show();
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

            TextView contrasenia = (TextView) findViewById(R.id.password);
            if(s.equals(contrasenia.getText().toString())){
                Intent intentPagHome = new Intent();
                intentPagHome.setClass(IniciarSesion.this, Home.class);
                startActivity(intentPagHome);

                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("mail", textViewEmail.getText().toString());
                editor.putString("password", editTextPassword.getText().toString());

                editor.commit();
                finish();
            }else{
                Toast toast = Toast.makeText(IniciarSesion.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}

