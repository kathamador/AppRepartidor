<?php
// Conectar a la base de datos
$conexion = mysqli_connect("localhost", "root", "", "eleconomico");

// Verificar la conexión
if (mysqli_connect_errno()) {
    echo "Error al conectar a la base de datos: " . mysqli_connect_error();
    exit();
}

$idagenterepartidor = $_GET["idAgenteRepartidor"];
// Ejecutar la consulta SELECT
$resultado = mysqli_query($conexion, "SELECT pedidos.idpedidos, pedidos.Estado_pedido, factura.fecha, pedidos.calificacion, pedidos.total,agente_cliente.nombre,agente_cliente.idagentecliente,agente_cliente.telefono
FROM pedidos 
INNER JOIN detalle_factura ON pedidos.iddetalle_factura = detalle_factura.iddetalle_factura 
INNER JOIN factura ON detalle_factura.idfactura = factura.idfactura 
INNER JOIN agente_cliente ON factura.idagentecliente = agente_cliente.idagentecliente
WHERE pedidos.idagente_repartidor = $idagenterepartidor ORDER BY pedidos.idpedidos DESC");

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