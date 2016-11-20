package asee.unex.es.pizzeriamilenio.Clases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import asee.unex.es.pizzeriamilenio.Adaptadores.AdaptadorOfertas;
import asee.unex.es.pizzeriamilenio.R;

public class Ofertas extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        List<CuadrosOfertas> items = new ArrayList<>();

        items.add(new CuadrosOfertas(R.drawable.obocadillo));
        items.add(new CuadrosOfertas(R.drawable.obebida));
        items.add(new CuadrosOfertas(R.drawable.osandwich));
        items.add(new CuadrosOfertas(R.drawable.omenu_converted));
        items.add(new CuadrosOfertas(R.drawable.opizzas));
        items.add(new CuadrosOfertas(R.drawable.descuento10));

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new AdaptadorOfertas(items);
        recycler.setAdapter(adapter);
    }
}
