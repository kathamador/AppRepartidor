package com.example.AppRepartidor.validatedlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.AppRepartidor.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ValidarCredenciales extends AsyncTask<String, Void, JSONObject> {

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_NOMBRE = "nombre";

    private Context mContext;

    public ValidarCredenciales(){
    }
    public ValidarCredenciales(Context context) {
        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        String correo = args[0];
        String contrasena = args[1];

        try {
            // Construir la URL de la solicitud HTTP POST
            URL url = new URL("https://finalproyect.com/Repartidor/validationloginRepartidor.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Construir los parámetros de la solicitud
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String params = "correo=" + correo + "&contrasena=" + contrasena;
            writer.write(params);
            writer.flush();
            writer.close();
            os.close();

            // Leer la respuesta del servidor
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            conn.disconnect();

            // Analizar la respuesta como JSON
            JSONObject json = new JSONObject(sb.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        try {
            int success = json.getInt(TAG_SUCCESS);
            String message = json.getString(TAG_MESSAGE);
            if (success == 1) {
                // Las credenciales son válidas, guardar el nombre y abrir la actividad principal
                String nombre = json.getString("nombre");
                String id = json.getString("idagenterepartidor");
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("nombre", nombre);
                editor.putString("idagenterepartidor", id);
                editor.apply();

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);



            }else {
                // Las credenciales son inválidas, mostrar un mensaje de error
                Log.e("no","incorrecto");
                Toast.makeText(mContext.getApplicationContext(), "Datos Incorrectos", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
