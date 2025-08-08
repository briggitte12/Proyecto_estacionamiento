<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" />
  <link rel="stylesheet" type="text/css" href="css/main.css" />
  <link rel="icon" type="image/png" href="images/logo.png">
  <title>Login - Parking Admin</title>
</head>
<body class="bg-light">

<section class="d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow-sm" style="width: 350px;">
    <div class="card-body">
      <h1 class="h3 text-center text-primary">Parking</h1>
      <form class="login-form" action="login" method="POST">
        <h5 class="text-center mb-4"><i class="bi bi-person me-2"></i>Iniciar Sesión</h5>
        <div class="form-group mb-3">
          <label for="txtusuario" class="form-label">Usuario</label>
          <input type="text" class="form-control" id="txtusuario" name="txtusuario" placeholder="Ingrese su usuario" required autofocus>
        </div>
        <div class="form-group mb-3">
          <label for="txtPassword" class="form-label">Contraseña</label>
          <input type="password" class="form-control" id="txtPassword" name="txtPassword" placeholder="Ingrese su contraseña" required>
        </div>
        <div class="d-grid mb-3">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-box-arrow-in-right me-2"></i>Iniciar Sesión
          </button>
        </div>
        <!--<div class="text-center">
          <a href="#" data-toggle="flip" class="text-decoration-none">¿Olvidaste tu contraseña?</a>
        </div>-->
      </form>
    </div>
  </div>
</section>

<script src="js/jquery-3.7.0.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
  $('.login-content [data-toggle="flip"]').click(function () {
    $(".login-box").toggleClass("flipped");
    return false;
  });
</script>
</body>
</html>
