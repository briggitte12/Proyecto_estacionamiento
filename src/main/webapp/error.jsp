<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <div class="alert alert-danger" role="alert">
        <h4 class="alert-heading">¡Ocurrió un error!</h4>
        <p>Lo sentimos, ha ocurrido un problema al procesar su solicitud. Por favor, intente de nuevo más tarde.</p>
        <hr>
        <p class="mb-0">Si el problema persiste, póngase en contacto con el soporte técnico.</p>
    </div>
    <a href="dashboard" class="btn btn-primary">Volver a la página principal</a>
    <a href="javascript:history.back()" class="btn btn-secondary">Volver a la página anterior</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
