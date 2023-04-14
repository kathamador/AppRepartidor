package com.example.AppRepartidor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.AppRepartidor.validatedlogin.ValidarCredenciales;


public class Login extends AppCompatActivity {

    Button btnRegistroUsuario,btniniciar;
    EditText editTextCorreo,editTextContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btniniciar= (Button) findViewById(R.id.btnIniciarSesion);
        ImageButton btnVerPassword =  findViewById(R.id.btnVerPass);
        EditText txtPass = findViewById(R.id.txtPass);
        editTextCorreo = findViewById(R.id.txtUser);
        editTextContrasena = findViewById(R.id.txtPass);
       // mandarDatos();

        btniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = editTextCorreo.getText().toString();
                String contrasena = editTextContrasena.getText().toString();

                if (TextUtils.isEmpty(correo)) {
                    editTextCorreo.setError("Por favor ingrese su correo");
                    return;
                }

                if (TextUtils.isEmpty(contrasena)) {
                    editTextContrasena.setError("Por favor ingrese su contraseña");
                    return;
                }

                // Validar las credenciales en segundo plano
                ValidarCredenciales validarCredenciales = new ValidarCredenciales(getApplicationContext());
                validarCredenciales.execute(correo, contrasena);

            }
        });


        btnVerPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cursorPosition = txtPass.getSelectionStart(); // Guarda la posición del cursor

                if (txtPass.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    // Si el EditText tiene el texto oculto, muestra los caracteres
                    txtPass.setTransformationMethod(null);

                } else {
                    // Si el EditText tiene los caracteres visibles, oculta el texto
                    txtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

                txtPass.setSelection(cursorPosition); // Restaura la posición del cursor
            }
        });

    }
    public void mandarDatos()
    {
        Intent intent= new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("correo",editTextCorreo.getText());
        startActivity(intent);
    }

}