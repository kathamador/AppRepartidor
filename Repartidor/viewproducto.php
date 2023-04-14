<?php
// Conectar a la base de datos
$conexion = mysqli_connect("localhost", "root", "", "eleconomico");

// Verificar la conexión
if (mysqli_connect_errno()) {
    echo "Error al conectar a la base de datos: " . mysqli_connect_error();
    exit();
}

$idcliente = $_GET["idcliente"];
$idpedido = $_GET["idpedidos"];
// Ejecutar la consulta SELECT
$resultado = mysqli_query($conexion, "SELECT p.nombre, SUM(df.cantidad) AS total_cantidad, p.precio, (SUM(df.cantidad) * p.precio) AS subtotal, SUM(df.cantidad * p.precio) AS total_subtotal
FROM pedidos pe
INNER JOIN detalle_factura df ON pe.idpedidos = df.idfactura
INNER JOIN producto p ON df.idproducto = p.idproducto
INNER JOIN factura f ON df.idfactura = f.idfactura
INNER JOIN agente_cliente ac ON f.idagentecliente = ac.idagentecliente
WHERE f.idagentecliente = $idcliente AND pe.idpedidos = $idpedido
GROUP BY p.nombre, p.precio"
);

// Crear un arreglo para almacenar los datos
$datos = array();

// Recorrer los resultados y almacenarlos en el arreglo
while ($fila = mysqli_fetch_assoc($resultado)) {
    $datos[] = $fila;
}

// Codificar los datos en formato JSON y enviarlos de vuelta a la aplicación Android
header('Content-Type: application/json');
echo json_encode($datos);

// Cerrar la conexión
mysqli_close($conexion);
?>