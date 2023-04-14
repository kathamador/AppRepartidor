package com.example.AppRepartidor;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

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

public class ClaseAdapterLP extends Fragment {

    public static ListView listViewPedidos;
    String estado;
    public static TextView textViewEstadoPedidoValor;
    public static String estadoPedido;
    public static TextView id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_pedidos, container, false);

        listViewPedidos = view.findViewById(R.id.listapedidos);

        // Hacer la petición al servidor
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://finalproyect.com/Repartidor/mispedidos.php";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Crear un ArrayList de objetos Pedido
                            ArrayList<Personas> pedidos = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("idpedidos");
                                 estadoPedido = jsonObject.getString("Estado_pedido");
                                String fecha = jsonObject.getString("fecha");
                                // if que consulte si estado pedido tiene un valor = o que me diga su estado en texto

                                // estado="en proceso
                                float calificacion = Float.parseFloat(jsonObject.getString("calificacion"));
                                pedidos.add(new Personas(id,estadoPedido,fecha, calificacion));
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

        return view;
    }

    public class PedidosAdapter extends ArrayAdapter<Personas> {
        private Context context;
        private ArrayList<Personas> pedidos;

        public PedidosAdapter(Context context, ArrayList<Personas> pedidos) {
            super(context, R.layout.activity_fragment_lista_p, pedidos);
            this.context = context;
            this.pedidos = pedidos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.activity_fragment_lista_p, parent, false);

            // Obtener las referencias a los elementos de la fila
            id = rowView.findViewById(R.id.pedido);
            ///TextView textViewEstadoPedido = rowView.findViewById(R.id.estado);
            textViewEstadoPedidoValor = rowView.findViewById(R.id.estado);
            TextView fecha = rowView.findViewById(R.id.fecha);
            RatingBar ratingBarCalificacion = rowView.findViewById(R.id.ratingBar);

            // Asignar los valores a los elementos de la fila
            id.setText("Pedido #"+pedidos.get(position).getId());
            textViewEstadoPedidoValor.setText(pedidos.get(position).getEstadoPedido());

            //EliminarP();
            fecha.setText(pedidos.get(position).getFecha());
            ratingBarCalificacion.setRating(pedidos.get(position).getCalificacion());

            return rowView;
        }
    }



}
