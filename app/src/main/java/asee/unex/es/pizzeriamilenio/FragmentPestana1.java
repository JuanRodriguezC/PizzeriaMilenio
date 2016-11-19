package asee.unex.es.pizzeriamilenio;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by juan on 30/10/16.
 */

public class FragmentPestana1 extends Fragment {

    private Button buttonAnchoas, buttonAtun, buttonBacon, buttonCarbonara, buttonCarne, button4Quesos;
    private Button buttonEmbutido, buttonFrankfurt, buttonIberica, buttonMargarita, buttonMarinera;
    private Button buttonMilenio, buttonSalami, buttonTropical, buttonVegetal, buttonYork;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        View view = inflater.inflate(R.layout.fragment_pestana1,container,false);

        buttonAnchoas = (Button)view.findViewById(R.id.buttonAnchoas);
        buttonAnchoas.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Atún, Anchoas y Orégano");
                builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


        buttonAtun = (Button)view.findViewById(R.id.buttonAtun);
        buttonAtun.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Atún, Pimientos y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


        buttonBacon = (Button)view.findViewById(R.id.buttonBacon);
        buttonBacon.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Bacon, Champiñones y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


        buttonCarbonara = (Button)view.findViewById(R.id.buttonCarbonara);
        buttonCarbonara.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Salsa de queso, Bacon, Cebolla y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


        buttonCarne = (Button)view.findViewById(R.id.buttonCarne);
        buttonCarne.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Carne y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        button4Quesos = (Button)view.findViewById(R.id.button4Quesos);
        button4Quesos.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Cheddar, Sansoe, Mozzarella, Roquefort y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonEmbutido = (Button)view.findViewById(R.id.buttonEmbutido);
        buttonEmbutido.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Salami, Bacon, Chorizo, York y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonFrankfurt = (Button)view.findViewById(R.id.buttonFrankfurt);
        buttonFrankfurt.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Bacon, Salchichas frankfurt y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonIberica = (Button)view.findViewById(R.id.buttonIberica);
        buttonIberica.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Queso de oveja, York, Jamón ibérico, Lomo ibérico, " +
                        "Chorizo ibérico y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonMargarita = (Button)view.findViewById(R.id.buttonMargarita);
        buttonMargarita.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonMarinera = (Button)view.findViewById(R.id.buttonMarinera);
        buttonMarinera.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Atún, Gambas, Surimi, Mejillones y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonMilenio = (Button)view.findViewById(R.id.buttonMilenio);
        buttonMilenio.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Bacon, Salami, Atún, Cahmpiñones, Anchoas y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonSalami = (Button)view.findViewById(R.id.buttonSalami);
        buttonSalami.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Salami y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonTropical = (Button)view.findViewById(R.id.buttonTropical);
        buttonTropical.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, York, Piña y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonVegetal = (Button)view.findViewById(R.id.buttonVegetal);
        buttonVegetal.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, Champiñones, Alcachofas, ESpárragos, Pimientos y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        buttonYork = (Button)view.findViewById(R.id.buttonYork);
        buttonYork.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ingredientes");
                builder.setMessage("Tomate, Mozzarella, York' y Orégano");
                        builder.setNegativeButton("Volver",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}