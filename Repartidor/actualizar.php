<?php
header('Access-Control-Allow-Origin: *');
header("Access-Control-Allow-Headers: *");
$conexion = new mysqli('localhost', 'root', '', 'eleconomico');

$postdata = file_get_contents("php://input");
$request = json_decode($postdata);

$id = $request->idagenterepartidor;
$contra = $request->contraseña;

$sql_query = "UPDATE agente_repartidor SET  contraseña='$contra' WHERE idagenterepartidor='$id'";

$result = $conexion->query($sql_query);

if ($result) {
    $resultado = array('estado' => true);
    echo json_encode($resultado);
} else {
    $resultado = array('estado' => false);
    echo json_encode($resultado);
}
?>