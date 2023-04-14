package com.example.AppRepartidor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private Context context;
    public static ListView listViewPedidos;
    public static TextView textViewIdPedidos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_lista_pedidos, container, false);

        // Obtener la referencia al ListView
        listViewPedidos = view.findViewById(R.id.listapedidos);

        // Crear la cola de peticiones de Volley
        RequestQueue queue = Volley.newRequestQueue(getContext());

        // URL del archivo PHP que devuelve los datos en formato JSON

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nombre = sharedPreferences.getString("nombre", "");
        String idRepartidor = sharedPreferences.getString("idagenterepartidor", "");

        //Log.e("no","incorrecto"+idRepartidor);

        String url = "https://finalproyect.com/Repartidor/mispedidos.php?idAgenteRepartidor="+idRepartidor;

        // Crear una petición de tipo JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Crear un ArrayList de objetos Pedido
                            ArrayList<Pedidos> pedidos = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String estadoPedido = jsonObject.getString("Estado_pedido");
                                String fecha = jsonObject.getString("fecha");
                                String total = jsonObject.getString("total");
                                String nombre = jsonObject.getString("nombre");
                                String telefono = jsonObject.getString("telefono");
                                Integer idpedido  =  jsonObject.getInt("idpedidos");
                                Integer idcliente  =  jsonObject.getInt("idagentecliente");

                                // Validar si el estado del pedido contiene la etiqueta <br>
                                if (estadoPedido.contains("<br>")) {
                                    estadoPedido = estadoPedido.replace("<br>", "");
                                }


                                float calificacion = Float.parseFloat(jsonObject.getString("calificacion"));

                                pedidos.add(new Pedidos(idpedido,estadoPedido, calificacion,fecha,total,nombre,telefono,idcliente));
                            }

                            // Crear un adaptador personalizado para el ListView
                            PedidosAdapter pedidosAdapter = new PedidosAdapter(getActivity(), pedidos);
                            listViewPedidos.setAdapter(pedidosAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Error en la petición: " + error.getMessage());
                    }
                });

        // Agregar la petición al RequestQueue
        queue.add(jsonArrayRequest);
        view.findViewById(R.id.btn_regresar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
            }
        });
        return view;
    }


    public class PedidosAdapter extends ArrayAdapter<Pedidos> {
        private Context context;
        private ArrayList<Pedidos> pedidos;

        public PedidosAdapter(Context context, ArrayList<Pedidos> pedidos) {
            super(context, R.layout.activity_lista_pedidos, pedidos);
            this.context = context;
            this.pedidos = pedidos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_fragment_lista_p, parent, false);

            // Obtener las referencias a los elementos de la fila
            TextView textViewEstadoPedido = rowView.findViewById(R.id.estado);
            TextView txtViewFecha = rowView.findViewById(R.id.fecha);
            TextView textViewEstadoPedidoValor = rowView.findViewById(R.id.estado);
            textViewIdPedidos = rowView.findViewById(R.id.pedido);
            RatingBar ratingBarCalificacion = rowView.findViewById(R.id.ratingBar);

            // Asignar los valores a los elementos de la fila
            if(pedidos.get(position).getEstadoPedido().equals("0")){
                textViewEstadoPedidoValor.setText("Estado: En Proceso");
            }else if(pedidos.get(position).getEstadoPedido().equals("1")){
                textViewEstadoPedidoValor.setText("Estado: Confirmado");
            }else if(pedidos.get(position).getEstadoPedido().equals("2")){
                textViewEstadoPedidoValor.setText("Estado: En Camino");
            }else if(pedidos.get(position).getEstadoPedido().equals("3")){
                textViewEstadoPedidoValor.setText("Estado: Entregado");
            }

            ratingBarCalificacion.setRating(pedidos.get(position).getCalificacion());
            txtViewFecha.setText("Fecha: "+pedidos.get(position).getFecha());
            textViewIdPedidos.setText("Pedido #"+pedidos.get(position).getIdpedido());

            LinearLayout linearLayoutContent = rowView.findViewById(R.id.content);
            // Agregar el OnClickListener al TextView "Estado"
            linearLayoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Crear el Intent para la siguiente Activity y agregar los datos que deseas pasar
                    Intent intent = new Intent(getContext(), ViewPedidos.class);
                    intent.putExtra("IdPedido", pedidos.get(position).getIdpedido());
                    intent.putExtra("Total", pedidos.get(position).getTotal());
                    intent.putExtra("Nombre", pedidos.get(position).getNombre());
                    intent.putExtra("Telefono", pedidos.get(position).getTelefono());
                    intent.putExtra("IdCliente", pedidos.get(position).getIdcliente());
                    intent.putExtra("EstadoPedido", pedidos.get(position).getEstadoPedido());
                    // Iniciar la siguiente Activity
                    getContext().startActivity(intent);
                }
            });
            return rowView;
        }

    }
}