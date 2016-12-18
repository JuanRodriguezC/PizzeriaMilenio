package asee.unex.es.pizzeriamilenio.Adaptadores;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import asee.unex.es.pizzeriamilenio.Fragments.FragmentPestana1;
import asee.unex.es.pizzeriamilenio.Fragments.FragmentPestana2;
import asee.unex.es.pizzeriamilenio.Fragments.FragmentPestana3;

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

        switch (position) {
            case 0:
                return new FragmentPestana1();

            case 1:
                return new FragmentPestana2();

            case 2:
                return new FragmentPestana3();

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
                return "Pizzas";
            case 1:
                return "Bocadillos";
            case 2:
                return "Varios";
            default:
                return null;
        }

    }
}
