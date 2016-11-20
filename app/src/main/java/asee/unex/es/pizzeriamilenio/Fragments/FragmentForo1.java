package asee.unex.es.pizzeriamilenio.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import asee.unex.es.pizzeriamilenio.Clases.ObtenerWebService;
import asee.unex.es.pizzeriamilenio.R;


/**
 * Created by juan on 30/10/16.
 */

public class FragmentForo1 extends Fragment implements View.OnClickListener {

    private Button buttonEnviarMensaje;
    EditText editTextTemaForo, editTextMensaje;
    ObtenerWebService hiloconexion;

    //IP de mi url
    String IP = "http://aplicacionasee.esy.es";
    String insertarMensaje = IP + "/insertar_mensaje.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        View view = inflater.inflate(R.layout.fragment_foro1,container,false);

        buttonEnviarMensaje = (Button) view.findViewById(R.id.buttonEnviarMensaje);
        buttonEnviarMensaje.setOnClickListener(this);
        editTextTemaForo = (EditText) view.findViewById(R.id.editTextTemaForo);
        editTextMensaje = (EditText) view.findViewById(R.id.editTextMensaje);


        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEnviarMensaje:
                if(editTextTemaForo.getText().toString().length() !=0 && editTextMensaje.getText().toString().length()!=0){
                    hiloconexion = new ObtenerWebService();
                    hiloconexion.execute(insertarMensaje, "5", editTextTemaForo.getText().toString(), editTextMensaje.getText().toString());

                    editTextTemaForo.setText("");
                    editTextMensaje.setText("");

                    Toast toast = Toast.makeText(this.getActivity(), "Mensaje Enviado", Toast.LENGTH_SHORT);
                    toast.show();

                }else{
                    Toast toast = Toast.makeText(this.getActivity(), "Rellene todos los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            default:
                break;
        }
    }
}