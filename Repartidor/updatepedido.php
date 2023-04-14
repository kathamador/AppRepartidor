<?php

// Verificamos que se haya enviado el ID del pedido
if(isset($_GET['id'])){

    // Recibimos el ID del pedido desde la URL
    $id_pedido = $_GET['id'];

    // Conectamos a la base de datos
    $conn = mysqli_connect('localhost', 'root', '', 'eleconomico');

    // Verificamos si la conexión fue exitosa
    if(!$conn){
        die('Error al conectarse a la base de datos: '.mysqli_connect_error());
    }

    // Actualizamos el estado del pedido a 3
    $sql = "UPDATE pedidos SET Estado_pedido = 3 WHERE idpedidos = $id_pedido";
    if(mysqli_query($conn, $sql)){
        // Si la actualización fue exitosa, devolvemos un mensaje en formato JSON
        $response['success'] = true;
        $response['message'] = "Estado del pedido actualizado correctamente";
        echo json_encode($response);
    } else {
        // Si hubo un error en la actualización, devolvemos un mensaje en formato JSON
        $response['success'] = false;
        $response['message'] = "Error al actualizar el estado del pedido";
        echo json_encode($response);
    }

    // Cerramos la conexión a la base de datos
    mysqli_close($conn);

} else {
    // Si no se recibió el ID del pedido, devolvemos un mensaje en formato JSON
    $response['success'] = false;
    $response['message'] = "ID del pedido no recibido";
    echo json_encode($response);
}

?>
