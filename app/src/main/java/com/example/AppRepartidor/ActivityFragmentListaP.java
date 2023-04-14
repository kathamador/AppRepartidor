package com.example.AppRepartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityFragmentListaP extends AppCompatActivity {
    private RatingBar ratingBar;
    TextView fecha;
    String hora,fechaA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_lista_p);

        ratingBar = findViewById(R.id.ratingBar);
        fecha = (TextView)findViewById(R.id.fecha);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        fechaA = dateFormat.format(calendar.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        hora = timeFormat.format(calendar.getTime());

        fecha.setText("Fecha: " + fechaA+" Hora: "+hora);
    }
}