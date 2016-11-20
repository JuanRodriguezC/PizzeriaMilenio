package asee.unex.es.pizzeriamilenio.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import asee.unex.es.pizzeriamilenio.Clases.CuadrosOfertas;
import asee.unex.es.pizzeriamilenio.R;

/**
 * Created by juan on 20/11/16.
 */
public class AdaptadorOfertas extends RecyclerView.Adapter<AdaptadorOfertas.CubeViewHolder> {
    private List<CuadrosOfertas> items;

    public static class CubeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;

        public CubeViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);

        }
    }

    public AdaptadorOfertas(List<CuadrosOfertas> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public CubeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_ofertas, viewGroup, false);
        return new CubeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CubeViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
    }
}