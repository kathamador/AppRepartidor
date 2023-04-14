package com.example.AppRepartidor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FacturaAdapter extends ArrayAdapter<Factura> {

    public FacturaAdapter(Context context, List<Factura> facturas) {
        super(context, 0, facturas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el item actual
        Factura factura = getItem(position);

        // Reutilizar la vista si es posible, de lo contrario inflar la vista
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }

        // Obtener los elementos de la vista
        TextView tvNombre = (TextView) convertView.findViewById(R.id.text_nombre);
        TextView tvCantidad = (TextView) convertView.findViewById(R.id.text_cantidad);
        TextView tvPrecio = (TextView) convertView.findViewById(R.id.text_precio);
        TextView tvSubtotal = (TextView) convertView.findViewById(R.id.text_subtotal);

        // Establecer los valores de los elementos de la vista
        tvNombre.setText(factura.getNombreProducto());
        tvCantidad.setText("Cantidad: " + factura.getCantidad());
        tvPrecio.setText("Precio unitario: " + factura.getPrecio());
        tvSubtotal.setText("Subtotal: " + factura.getSubtotal());

        return convertView;
    }
}
