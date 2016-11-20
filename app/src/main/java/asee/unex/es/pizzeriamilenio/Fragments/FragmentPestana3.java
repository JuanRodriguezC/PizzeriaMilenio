package asee.unex.es.pizzeriamilenio.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import asee.unex.es.pizzeriamilenio.R;

/**
 * Created by juan on 30/10/16.
 */

public class FragmentPestana3 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //  "Inflamos" el archivo XML correspondiente a esta sección.
        return inflater.inflate(R.layout.fragment_pestana3,container,false);
    }

}