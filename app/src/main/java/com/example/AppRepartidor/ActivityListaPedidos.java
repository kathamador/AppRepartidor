package com.example.AppRepartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityListaPedidos extends AppCompatActivity {
    BottomNavigationView nav;
    Button btnregresar;
    private ListView listView;

    ArrayList<String> datos;
    ClaseAdapterLP mAdapter;
    private ListView listViewPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        ImageButton btnregresar =  findViewById(R.id.btn_regresar);
        listView = (ListView) findViewById(R.id.listapedidos);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void llenar(){


    }

}