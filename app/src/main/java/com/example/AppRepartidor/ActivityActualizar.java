package com.example.AppRepartidor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ActivityActualizar extends AppCompatActivity {
    EditText contra;
    String contraseña;
    ImageView btnverperfil;
    String idagenterepartidor;
    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        ImageButton btnVerPassword = findViewById(R.id.btnVerPass);
        nav=findViewById(R.id.bottomNavigationView);
        nav.setSelectedItemId(R.id.menuEmpleado);
        contra = findViewById(R.id.txtPass);
        SharedPreferences shared = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        idagenterepartidor = shared.getString("idagenterepartidor", "");
        btnverperfil = findViewById(R.id.imageView2);
        //mostrarFoto(idagenterepartidor);
        Busqueda(idagenterepartidor);

        findViewById(R.id.btnsalvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });
        btnVerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cursorPosition = contra.getSelectionStart(); // Guarda la posición del cursor

                if (contra.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    // Si el EditText tiene el texto oculto, muestra los caracteres
                    contra.setTransformationMethod(null);

                } else {
                    // Si el EditText tiene los caracteres visibles, oculta el texto
                    contra.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

                contra.setSelection(cursorPosition); // Restaura la posición del cursor
            }
        });

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menuEmpleado:
                        return true;
                    case R.id.menuPedidos:
                        startActivity(new Intent(getApplicationContext(),ContenedorFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.menuHome:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
    private void Busqueda(String id) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        final String ida = sharedPreferences.getString("idagenterepartidor", "");
        String url = "https://finalproyect.com/Repartidor/mostrarAct.php?idagenterepartidor=" + ida+ "";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        contraseña =(jsonObject.getString("contraseña"));
                        contra.setText(contraseña);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace(); // Agregar esta línea para imprimir la pila de errores
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void Actualizar()
    {
        String url = "https://finalproyect.com/Repartidor/actualizar.php";

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("idagenterepartidor", idagenterepartidor);
        params.put("contraseña", contra.getText().toString().toLowerCase());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"Se ha Modificado exitosamente",Toast.LENGTH_SHORT).show();
                        contra.setText("");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", "Error: " + error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

}