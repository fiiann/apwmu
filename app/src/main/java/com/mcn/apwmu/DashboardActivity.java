package com.mcn.apwmu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mcn.apwmu.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mcn.apwmu.LoginActivity.KEY_NIM;
import static com.mcn.apwmu.LoginActivity.KEY_USERNAME;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView nama;
    private TextView nim;
    private TextView nav_nama1;
    private ProgressDialog dialog;
    private TextView fakultas;
    private TextView jurusan;
    public static final String KEY_PRODI="prodi";
    public static final String KEY_FAKULTAS="fakultas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nama = (TextView) findViewById(R.id.d_nama);
        nim = (TextView) findViewById(R.id.d_nim);
        fakultas= (TextView) findViewById(R.id.d_fakultas);
        jurusan = (TextView) findViewById(R.id.d_prodi);
//        nav_nama1 = (TextView) findViewById(R.id.nav_nama);

        Intent intent = getIntent();
        String dashboard_nim = intent.getStringExtra(KEY_NIM);
        String dashboard_nama= intent.getStringExtra(KEY_USERNAME);
        String nav_nama= intent.getStringExtra(KEY_USERNAME);
        String nim1 = nim.toString();
        nama.setText(dashboard_nama);
        nim.setText(dashboard_nim);
//        nav_nama1.setText(dashboard_nama);

        String url = "http://192.168.1.24/kuliah/ppl/driver_api/detail.php?nim="+dashboard_nim;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();

                        try {
                            JSONObject body = response.getJSONObject("results");
                            String faculty = body.getString("fakultas");
                            fakultas.setText(faculty);
                            String prodi = body.getString("jurusan");
                            jurusan.setText(prodi);
//                            ipk.setText(body.getString("ipk"));
//                            text_nim.setText(body.getString("nim"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String r_fakultas= null;
                        String r_jurusan= null;

                        try {
                            JSONObject body = response.getJSONObject("results");
                            r_fakultas= body.getString("fakultas");
                            r_jurusan= body.getString("jurusan");
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("fakultas", r_fakultas); // Storing string
                        editor.putString("jurusan", r_jurusan);


                        editor.commit(); // commit changes

                        String fakultas= pref.getString("fakultas", null); // getting String
                        String jurusan= pref.getString("jurusan", null); // getting Integer
//                        Toast.makeText(DashboardActivity.this,fakultas,Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DashboardActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

                String apiKey = pref.getString("apiKey", null);
                params.put("Authorization", apiKey);
                return params;
            }
        };
        AppController.getmInstance().addToRequesQueue(jsonObjectRequest);

    }
    public void hideDialog(){
        if (dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
    }
//    public static final String key;
    public void identitasPribadi(View view){
        String namaku = nama.getText().toString().trim();
        String nimku = nim.getText().toString().trim();
        Intent myintent = new Intent(this, f_identitas_pribadi.class);
        myintent.putExtra(KEY_USERNAME, namaku);
        myintent.putExtra(KEY_NIM, nimku);
        startActivity(myintent);
    }

    public void informasiTugasAkhir(View view){

        String nimku = nim.getText().toString().trim();
        String namaku = nama.getText().toString().trim();
        String fakultasku = fakultas.getText().toString().trim();
        String prodiku= jurusan.getText().toString().trim();
        Intent myintents = new Intent(this, f_tugas_akhir.class);
        myintents.putExtra(KEY_NIM, nimku);
        myintents.putExtra(KEY_USERNAME, namaku);
        myintents.putExtra(KEY_FAKULTAS, fakultasku);
        myintents.putExtra(KEY_PRODI, prodiku);
        startActivity(myintents);
    }

    public void cariWisudawanUndip(View view){
        Intent myintent = new Intent(this, CariWisudaActivity.class);
        startActivity(myintent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            String namaku = nama.getText().toString().trim();
            String nimku= nim.getText().toString().trim();
            Intent myintent = new Intent(this, DashboardActivity.class);
            myintent.putExtra(KEY_USERNAME, namaku);
            myintent.putExtra(KEY_NIM, nimku);
            startActivity(myintent);
        } else if (id == R.id.nav_identitas) {
            String nimku = nim.getText().toString().trim();
            String namaku = nama.getText().toString().trim();
            Intent myintent = new Intent(this, f_identitas_pribadi.class);
            myintent.putExtra(KEY_NIM, nimku);
            myintent.putExtra(KEY_USERNAME, namaku);
            startActivity(myintent);
        } else if (id == R.id.nav_ta) {
            String nimku = nim.getText().toString().trim();
            String namaku = nama.getText().toString().trim();
            String fakultasku = fakultas.getText().toString().trim();
            String prodiku= jurusan.getText().toString().trim();
            Intent myintents = new Intent(this, f_tugas_akhir.class);
            myintents.putExtra(KEY_NIM, nimku);
            myintents.putExtra(KEY_USERNAME, namaku);
            myintents.putExtra(KEY_FAKULTAS, fakultasku);
            myintents.putExtra(KEY_PRODI, prodiku);
//            Toast.makeText(this,"fakultas: "+fakultasku+"jurusan "+prodiku,Toast.LENGTH_SHORT).show();
            startActivity(myintents);
        } else if (id == R.id.nav_cari) {
            Intent myintent = new Intent(this, CariWisudaActivity.class);
            startActivity(myintent);
        } else if (id == R.id.nav_jadwal) {
            Intent myintent = new Intent(this, JadwalWisudawan.class);
            startActivity(myintent);
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this,"Successfully logout . . .",Toast.LENGTH_SHORT).show();
            Intent myintent = new Intent(this, MainActivity.class);
            startActivity(myintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
