package com.mcn.apwmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity{
//    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void go_to_login(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void cariWisudawanHalaman(View view){
        Intent myintent = new Intent(this, Main2Activity.class);
        startActivity(myintent);
    }

    public void cariJadwalWisuda(View view){
        Intent myintent = new Intent(this, JadwalWisudawan.class);
        startActivity(myintent);
    }
}



