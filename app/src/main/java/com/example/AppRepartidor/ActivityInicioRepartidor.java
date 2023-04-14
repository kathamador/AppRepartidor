package com.example.AppRepartidor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class ActivityInicioRepartidor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_repartidor);

        ImageSlider imageSlider = findViewById(R.id.slider);
        ArrayList<SlideModel> images = new ArrayList<>();

        images.add(new SlideModel(R.drawable.deliviry,null));
        images.add(new SlideModel(R.drawable.bebe,null));
        images.add(new SlideModel(R.drawable.carnes,null));
        images.add(new SlideModel(R.drawable.coca,null));
        images.add(new SlideModel(R.drawable.corona,null));
        images.add(new SlideModel(R.drawable.frutas_verduras,null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

    }
}