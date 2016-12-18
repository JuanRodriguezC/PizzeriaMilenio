package asee.unex.es.pizzeriamilenio.Clases;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import asee.unex.es.pizzeriamilenio.Adaptadores.AdaptadorViewPagerPrincipal;
import asee.unex.es.pizzeriamilenio.R;

public class Comida extends AppCompatActivity {

    private AdaptadorViewPagerPrincipal Adaptador_ViewPagerPrincipal;
    private android.support.v4.view.ViewPager ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Iniciamos la barra de herramientas.
        Toolbar toolbar = (Toolbar) findViewById(R.id.ToolbarPrincipal);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.TabLayoutPrincipal);

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        ViewPager = (ViewPager) findViewById(R.id.ViewPagerPrincipal);

        Adaptador_ViewPagerPrincipal = new AdaptadorViewPagerPrincipal(getSupportFragmentManager(),tabLayout.getTabCount());

        ViewPager.setAdapter(Adaptador_ViewPagerPrincipal);

        tabLayout.setupWithViewPager(ViewPager);
    }
}
