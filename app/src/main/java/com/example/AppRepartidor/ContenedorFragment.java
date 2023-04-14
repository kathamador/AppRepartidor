package com.example.AppRepartidor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ContenedorFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_fragment);

        // Obtener el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Iniciar una transacción de fragmentos
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Agregar el fragmento PedidosFragment al contenedor
        PedidosFragment pedidosFragment = new PedidosFragment();
        fragmentTransaction.add(R.id.fragment_container, pedidosFragment);

        // Terminar la transacción de fragmentos
        fragmentTransaction.commit();
    }
}