package com.example.AppRepartidor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialogoFragmentPerfil extends DialogFragment {

    Button btncerar,btnubicacion;
    TextView nombre;
    ImageView btnverperfil;
    String idagentecliente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.popup_enviar, container, false);
        btnubicacion = (Button) view.findViewById(R.id.ubicacion);
        btncerar = (Button) view.findViewById(R.id.cerrarsesion);
        nombre = (TextView) view.findViewById(R.id.nombre);
        ImageButton regresar = view.findViewById(R.id.regresar);

        SharedPreferences shared = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        idagentecliente = shared.getString("idagentecliente", "");
        btnverperfil = view.findViewById(R.id.imageView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        if (!nombre.isEmpty()) {
            TextView nombreTextView = view.findViewById(R.id.nombre);
            nombreTextView.setText(nombre);
        }
        btnubicacion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ActivityMapa.class);
                        startActivity(intent);
                    }
                }
        );
        regresar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }
        );
        btncerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cierre de Sesion");
                builder.setMessage("¿Quieres cerrar la app?");

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(getContext(),Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}