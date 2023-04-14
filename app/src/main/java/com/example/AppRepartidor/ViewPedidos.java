package com.example.AppRepartidor;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewPedidos extends AppCompatActivity implements LocationListener, OnMapReadyCallback {
    private RequestQueue mQueue;
    double acumsubtotal=0;
    private ListView listView;
    private List<Factura> facturas;
   private Button btnEntregarPedido;
    public static String nombre;
    public static String lo, la;
    private LocationManager locationManager;
    private GoogleMap mMap;

    double lat;
    double lon;
    Button mapa;
    int idPedido;

    private LatLng ubicacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pedidos);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        // Obtener los datos pasados como extras
        idPedido = getIntent().getIntExtra("IdPedido", 0);
        int IdCliente = getIntent().getIntExtra("IdCliente", 0);
        String total = getIntent().getStringExtra("Total");
        nombre = getIntent().getStringExtra("Nombre");
        String telefono = getIntent().getStringExtra("Telefono");
        String EstadoPedido = getIntent().getStringExtra("EstadoPedido");

        btnEntregarPedido = findViewById(R.id.btnEntregarPedido);
        Busqueda(nombre);

        if (EstadoPedido.equals("2")) {
            btnEntregarPedido.setVisibility(View.VISIBLE);
        } else if (EstadoPedido.equals("3")) {
            btnEntregarPedido.setBackgroundColor(Color.GREEN);
            btnEntregarPedido.setText("Entregado");
            btnEntregarPedido.setVisibility(View.GONE);
        } else {
            btnEntregarPedido.setVisibility(View.GONE);
        }

        TextView txtPedido = findViewById(R.id.txtPedidos);
        TextView txtTotal = findViewById(R.id.txtTotal);
        TextView txtNombreCliente = findViewById(R.id.txtNombreCliente);
        TextView txtTelefono = findViewById(R.id.txtTelefono);
        TextView txtSubtotalSuma = findViewById(R.id.txtSubtotal);  //subtotal suma

        txtPedido.setText("Pedido #"+String.valueOf(idPedido));
        txtTotal.setText(total);
        txtNombreCliente.setText(nombre);
        txtTelefono.setText(telefono);

        mapa = (Button) findViewById(R.id.ShowOnMap);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String geoUri = "http://maps.google.com/maps?q=loc:" + la + "," + lo + " (" + "Destino" + ")";
                Uri location = Uri.parse(geoUri);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            }
        });

        findViewById(R.id.btnregresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ContenedorFragment.class);
                startActivity(in);
            }
        });
        mQueue = Volley.newRequestQueue(this);
        btnEntregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://finalproyect.com/Repartidor/updatepedido.php?id=" + idPedido;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // actualizar tabla pedidos aquí
                                Toast.makeText(ViewPedidos.this, "Pedido entregado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ViewPedidos.this, "Error al entregar pedido", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ViewPedidos.this, "Error de respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewPedidos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

                mQueue.add(jsonObjectRequest);
            }
        });
                listView = findViewById(R.id.listProductos);
                facturas = new ArrayList<>();

                // Crear la cola de peticiones
                RequestQueue queue = Volley.newRequestQueue(this);

                // URL del servicio web
                String url = "https://finalproyect.com/Repartidor/viewproducto.php?idcliente="+IdCliente+"&idpedidos="+idPedido;

                // Crear la petición GET
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    // Parsear el JSON
                                    JSONArray jsonArray = new JSONArray(response);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        String nombreProducto = jsonObject.getString("nombre");
                                        int cantidad = jsonObject.getInt("total_cantidad");
                                        double precio = jsonObject.getDouble("precio");
                                        double subtotal = jsonObject.getDouble("subtotal");
                                        double totalsub = jsonObject.getDouble("total_subtotal");


                                        // Crear un objeto Factura con los datos obtenidos
                                        Factura factura = new Factura(nombreProducto, cantidad, precio, subtotal,totalsub);
                                        facturas.add(factura);

                                        acumsubtotal+=totalsub;
                                        txtSubtotalSuma.setText(String.valueOf(acumsubtotal));
                                    }

                                    // Crear un adapter para el ListView
                                    FacturaAdapter adapter = new FacturaAdapter(ViewPedidos.this, facturas);
                                    listView.setAdapter(adapter);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ViewPedidos.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Agregar la petición a la cola de peticiones
                queue.add(stringRequest);
    }
    private void Busqueda(String nombre) {
        String nom = getIntent().getStringExtra("Nombre");
        String url = "https://finalproyect.com/Repartidor/Mostrar.php?nombre=" + nom+ "";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        lo = (jsonObject.getString("longitud"));
                        la =(jsonObject.getString("latitud"));
                        lat = Double.parseDouble(la);
                        lon = Double.parseDouble(lo);
                        ubicacion = new LatLng(lat, lon);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (ubicacion != null) {
                        mMap.addMarker(new MarkerOptions().position(ubicacion).title("Ubicacion de "+nom));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(), "Error de conexion al buscar", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ActivityMapa.this, ""+lat+" l"+lon, Toast.LENGTH_SHORT).show();
                error.printStackTrace(); // Agregar esta línea para imprimir la pila de errores
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    ///mapi
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Busqueda(nombre);
        //Toast.makeText(this, "z"+ubicacion, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
    }
}
