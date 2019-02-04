package com.gorunteamsandroid.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class resume extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    public Bundle datos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView nav = (BottomNavigationView) findViewById(R.id.navigationView);
        nav.setOnNavigationItemSelectedListener(this);
        //setInitialFragment();
        Bundle parametros = this.getIntent().getExtras();
        datos = parametros;

        Fragment tmpfragment = null;
        tmpfragment = new ResumeFragment();
        tmpfragment.setArguments(datos);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, tmpfragment );
        fragmentTransaction.commit();





    }
    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.friends:
                fragment= new FriendFragment();
                fragment.setArguments(datos);
                break;
            case R.id.resume:
                fragment= new ResumeFragment();
                fragment.setArguments(datos);
                break;
            case R.id.teams:
                fragment = new TeamsFragment();
                //fragment= new TeamsFragment();
                fragment.setArguments(datos);
                break;

        };
        replaceFragment(fragment);
        return true;
    }

    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainFrame, new ResumeFragment());
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
    /*
    @Override
    //Menu superior
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menusuperior, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.configuracion:
                Intent intent = new Intent(getApplicationContext(), configuracion.class);
                startActivity(intent);
                return true;
            case R.id.salir:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    */
}