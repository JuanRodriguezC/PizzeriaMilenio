package asee.unex.es.pizzeriamilenio.Adaptadores;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import asee.unex.es.pizzeriamilenio.Fragments.FragmentForo1;
import asee.unex.es.pizzeriamilenio.Fragments.FragmentForo2;


public class AdaptadorViewForoPagePrincipal extends FragmentPagerAdapter {

    int numPestanas;

    public AdaptadorViewForoPagePrincipal(FragmentManager fm, int numPestanas) {
        super(fm);
        this.numPestanas=numPestanas;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                return new FragmentForo1();

            case 1:
                return new FragmentForo2();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numPestanas;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:
                return "Enviar Mensaje";
            case 1:
                return "Leer Mensajes";

            default:
                return null;
        }
    }
}
