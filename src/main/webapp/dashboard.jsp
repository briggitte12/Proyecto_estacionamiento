<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-speedometer"></i> Dashboard</h1>
      <p>Bienvenido, <b>${sessionScope.usuario != null ? sessionScope.usuario.getUsuario() : "Invitado"}</b>, al Panel de Administración</p>
    </div>
    <ul class="app-breadcrumb breadcrumb">
      <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
      <li class="breadcrumb-item"><a href="#">Dashboard</a></li>
    </ul>
  </div>

  <div class="row">
    <div class="col-md-6 col-lg-3">
      <div class="widget-small primary coloured-icon">
        <i class="icon bi bi-people fs-1"></i>
        <div class="info">
          <h4>Usuarios</h4>
          <p><b>${cantidad_usuario}</b></p>
        </div>
      </div>
    </div>
    <div class="col-md-6 col-lg-3">
      <div class="widget-small warning coloured-icon">
        <i class="icon bi bi-p-square-fill"></i>
        <div class="info">
          <h4>Total de Ganancias</h4>
          <p><b>S/${total_ganancias}</b></p>
        </div>
      </div>
    </div>
    <div class="col-md-6 col-lg-3">
      <div class="widget-small info coloured-icon">
        <i class="icon bi bi-person-circle"></i>
        <div class="info">
          <h4>Conductores</h4>
          <p><b>${cantidad}</b></p>
        </div>
      </div>
    </div>
    <div class="col-md-6 col-lg-3">
      <div class="widget-small danger coloured-icon">
        <i class="icon bi bi-car-front-fill"></i>
        <div class="info">
          <h4>Vehículos</h4>
          <p><b>${cantidad_vehiculos}</b></p>
        </div>
      </div>
    </div>
  </div>

  <!-- Mostrar la lista de tarifas -->
  <jsp:include page="tarifa.jsp" />
</main>

<jsp:include page="footer.jsp" />
