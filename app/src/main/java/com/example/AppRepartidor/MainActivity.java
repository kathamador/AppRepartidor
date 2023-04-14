package com.example.AppRepartidor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String idRepartidor;
    BottomNavigationView nav;
    TextView nombre;
    Bundle bundle;
    ImageView btnverperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav=findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.menuHome);
        nombre = (TextView) findViewById(R.id.textNombre);
        btnverperfil = findViewById(R.id.imageEmpleado);

        // de la línea 32 a 37 se debe copiar en cada activity que necesite.
        // si necesita usar el nombre para otra función, tome en cuenta que la variable String nombre, tiene el valor
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        String idRepartidor = sharedPreferences.getString("idagenterepartidor", "");

        Log.e("no","incorrecto"+idRepartidor);

        if (!nombre.isEmpty()) {
            TextView nombreTextView = findViewById(R.id.textNombre);
            nombreTextView.setText(nombre);
        }
        btnverperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialogoFragmentPerfil myDialogoFragment = new MyDialogoFragmentPerfil();
                myDialogoFragment.show(getSupportFragmentManager(),"PantallaPerfil");
            }
        });
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menuHome:
                        return true;
                    case R.id.menuPedidos:
                        startActivity(new Intent(getApplicationContext(),ContenedorFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuEmpleado:
                        startActivity(new Intent(getApplicationContext(),ActivityActualizar.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
}



    public void busquedaCliente(String correo)
    {
        bundle = getIntent().getExtras();
        correo = (bundle.getString("correo").toString());

        String url = "https://finalproyect.com/Repartidor/usuario.php?texto=" + correo;
        HashMap<String, String> params = new HashMap<String, String>();

        // params.put("Image", image);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        params.put("nombre", nombre.getText().toString().toLowerCase());
                        nombre.setText(params.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
                ///AlertaDialogo("Error al modificar "+error.getMessage(),"Error");
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);


    }
}