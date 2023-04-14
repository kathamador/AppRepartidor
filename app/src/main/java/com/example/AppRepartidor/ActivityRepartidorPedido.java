package com.example.AppRepartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.RequestQueue;

public class ActivityRepartidorPedido extends AppCompatActivity {
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repartidor_pedido);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        String id = sharedPreferences.getString("idagentecliente", "");



    }

}