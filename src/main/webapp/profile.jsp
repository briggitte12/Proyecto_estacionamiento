
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-person"></i> Perfil de Usuario</h1>
      <p>Detalles del perfil</p>
    </div>
    <ul class="app-breadcrumb breadcrumb">
      <li class="breadcrumb-item"><i class="bi bi-house-door"></i></li>
      <li class="breadcrumb-item">
        <a href="dashboard.jsp">Dashboard</a>
      </li>
      <li class="breadcrumb-item active">Perfil</li>
    </ul>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <h3 class="tile-title">Información Personal</h3>
        <div class="tile-body">
          <div class="row">
            <div class="col-lg-4 d-flex justify-content-center">
              <img
                      class="rounded-circle img-thumbnail"
                      src="images/${usuario.foto}"
                      alt="Foto de ${usuario.nombre_completo}"
                      style="width: 200px"
              />
            </div>
            <div class="col-lg-8">
              <table class="table table-bordered">
                <tr>
                  <th>Nombre Completo:</th>
                  <td>${usuario.nombre_completo}</td>
                </tr>
                <tr>
                  <th>Usuario:</th>
                  <td>${usuario.usuario}</td>
                </tr>
                <tr>
                  <th>Correo:</th>
                  <td>${usuario.correo}</td>
                </tr>
                <tr>
                  <th>Rol:</th>
                  <td>${usuario.rol}</td>
                </tr>
                <tr>
                  <th>Teléfono:</th>
                  <td>${usuario.telefono}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<script src="js/jquery-3.7.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/main.js"></script>
</body>
</html>
