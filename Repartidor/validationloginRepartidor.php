<?php

// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "eleconomico";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Validación de credenciales y obtención del nombre
$correo = $_POST["correo"];
$contrasena = $_POST["contrasena"];

$sql = "SELECT * FROM agente_repartidor WHERE correo = '$correo' AND contraseña = '$contrasena'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $response["success"] = 1;
    $response["message"] = "Credenciales válidas.";
    $response["nombre"] = $row["nombre"];
    $response["idagenterepartidor"] = $row["idagenterepartidor"];

} else {
    $response["success"] = 0;
    $response["message"] = "Credenciales inválidas.";
}

// Salida como JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cierre de la conexión a la base de datos
$conn->close();

?>
