package asee.unex.es.pizzeriamilenio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by juan on 30/10/16.
 */

public class AdaptadorViewPagerPrincipal extends FragmentPagerAdapter {

    int numPestanas;

    public AdaptadorViewPagerPrincipal(FragmentManager fm, int numPestanas) {
        super(fm);
        this.numPestanas=numPestanas;
    }

    @Override
    public Fragment getItem(int position) {
        // recibimos la posición por parámetro y en función de ella devolvemos el Fragment correspondiente a esa sección.
        switch (position) {

            case 0: // siempre empieza desde 0
                return new FragmentPestana1();

            case 1:
                return new FragmentPestana2();

            case 2:
                return new FragmentPestana3();


            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }
    }

    /*devuelve el numero de pestañas dato que recibiremos cuando instanciemos el adaptador
        desde nuestra actividad principal */
    @Override
    public int getCount() {
        return numPestanas;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        // recibimos la posición por parámetro y en función de ella devolvemos el titulo correspondiente.
        switch (position) {

            case 0: // siempre empieza desde 0, la primera Tab es 0
                return "Pizzas";
            case 1:
                return "Bocadillos";
            case 2:
                return "Varios";

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }

    }
}
