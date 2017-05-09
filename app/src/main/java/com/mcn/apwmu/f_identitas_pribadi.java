package com.mcn.apwmu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import static com.mcn.apwmu.LoginActivity.KEY_NIM;
import static com.mcn.apwmu.LoginActivity.KEY_USERNAME;

public class f_identitas_pribadi extends AppCompatActivity {
//    TODO Penambahan atribut lain
    private String nama;
    TextView text_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_identitas_pribadi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text_nama = (TextView) findViewById(R.id.d_namas);
        Intent intent = getIntent();
        String dashboard_nama= intent.getStringExtra(KEY_USERNAME);
        text_nama.setText(dashboard_nama);
//  TODO ambil data dari data_induk
    }

}
