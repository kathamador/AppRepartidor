<?php
header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Headers: *");

$conexion = new mysqli('localhost', 'root', '', 'eleconomico');

// Escapar la variable nombre para evitar ataques de inyección SQL
$nombre = mysqli_real_escape_string($conexion, $_GET["nombre"]);
$resultado = $conexion->query("SELECT nombre,latitud, longitud FROM agente_cliente WHERE nombre='$nombre'");

// Ejecutar la consulta
// Verificar si hay resultados
if ($resultado->num_rows > 0) {
    // Crear un arreglo vacío para almacenar los resultados
    $arreglo_resultados = array();

    // Recorrer los resultados y agregarlos al arreglo
    while($fila = $resultado->fetch_assoc()) {
        $arreglo_resultados[] = $fila;
    }

    // Convertir el arreglo a formato JSON
    $json_resultados = json_encode($arreglo_resultados);

    // Mostrar el resultado en formato JSON
    echo $json_resultados;
} else {
    echo "No se encontraron resultados";
}

// Cerrar la conexión
$conexion->close();
?>
