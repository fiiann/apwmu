package com.mcn.apwmu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class f_tugas_akhir extends AppCompatActivity {

    private String nim;
    private String nama;
    private ProgressDialog dialog;
    private TextView judul_ta;
    private TextView dosbing;
    private TextView ipk;
    private TextView d_nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_tugas_akhir);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        judul_ta = (TextView) findViewById(R.id.d_judul_ta);
        dosbing = (TextView) findViewById(R.id.d_pembimbing_satu);
        ipk= (TextView) findViewById(R.id.d_ipk);
        d_nama = (TextView) findViewById(R.id.d_namas);

//        text_nim= (TextView) findViewById(R.id.t_nim);
//        imageView = (NetworkImageView) findViewById(R.id.Image_view);
//        dialog = new ProgressDialog(this);
//        dialog.setMessage("Loading...");
//        dialog.show();

        Intent intent = getIntent();
        String dashboard_nim = intent.getStringExtra(KEY_NIM);
        String dashboard_nama= intent.getStringExtra(KEY_USERNAME);
//        String nim1 = nim.toString();
        d_nama.setText(dashboard_nama);
//         nim.setText(dashboard_nim);

        nim= getIntent().getExtras().getString(KEY_NIM);
//        nama= getIntent().getExtras().getString("nama");
//        d_nama.setText();
        Toast.makeText(f_tugas_akhir.this,nim,Toast.LENGTH_LONG).show();
//        Toast.makeText(TugasAkhirActivity.this,ipk.toString(),Toast.LENGTH_LONG).show();
        String url = "http://192.168.1.24/kuliah/ppl/api/mhs.php?nim="+nim.toString();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();

                        try {
                            JSONObject body = response.getJSONObject("results");
                            judul_ta.setText(body.getString("judul_ta"));
                            dosbing.setText(body.getString("pembimbing_ta"));
                            ipk.setText(body.getString("ipk"));
//                            text_nim.setText(body.getString("nim"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(f_tugas_akhir.this,error.toString(),Toast.LENGTH_LONG).show();
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

}
