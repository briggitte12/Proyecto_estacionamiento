<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-table"></i> Ingreso Vehículos</h1>
      <p>Lista de Ingreso de Vehículos</p>
    </div>
    <ul class="app-breadcrumb breadcrumb side">
      <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
      <li class="breadcrumb-item">Tables</li>
      <li class="breadcrumb-item active"><a href="#">Vehículos</a></li>
    </ul>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <div class="tile-body">
          <div class="table-responsive">
            <a href="add_ingreso_vehiculos.jsp">
              <button class="btn btn-primary" style="position: absolute; right: 25px">Agregar</button>
            </a>
            <br />
            <table class="table table-hover table-bordered" id="sampleTable">
              <thead>
              <tr>
                <th>Id Registro</th>
                <th>Número Placa</th>
                <th>Id Tarifa</th>
                <th>Id Usuario</th>
                <th>Piso</th>
                <th>Fecha y Hora Ingreso</th>
                <th>Fecha y Hora Salida</th>
                <th>Observación</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach var="elemento" items="${lista_registro}">
                <tr>
                  <td>${elemento.id_registro}</td>
                  <td>${elemento.placa}</td>
                  <td>${elemento.id_tarifa}</td>
                  <td>${elemento.id_usuario}</td>
                  <td>${elemento.piso}</td>
                  <td>
                    <span class="fecha-ingreso" data-fecha="${elemento.fecha_ingreso}"></span>
                  </td>
                  <td id="salida-${elemento.id_registro}">
                    <c:choose>
                      <c:when test="${elemento.fecha_salida != null}">
                        <span class="fecha-salida" data-fecha="${elemento.fecha_salida}"></span>
                      </c:when>
                      <c:otherwise>En Estacionamiento</c:otherwise>
                    </c:choose>
                  </td>
                  <td>${elemento.observacion}</td>
                  <td>${elemento.estado}</td>
                  <td>
                    <a href="ActualizarFechaSalidaServlet?id=${elemento.id_registro}&fechaIngreso=${elemento.fecha_ingreso}">
                      <button class="btn btn-danger">
                        <i class="bi bi-box-arrow-right"></i>
                      </button>
                    </a>
                    <a href="EnviarDatos?fechaIngreso=${elemento.fecha_ingreso}&fechaSalida=${elemento.fecha_salida}&id_tarifa=${elemento.id_tarifa}&id_registro=${elemento.id_registro}">
                      <button class="btn btn-info">
                        <i class="bi bi-credit-card-2-back-fill"></i>
                      </button>
                    </a>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<script>
  // Formatear las fechas usando JavaScript
  document.querySelectorAll('.fecha-ingreso, .fecha-salida').forEach(function(element) {
    var fechaOriginal = element.getAttribute('data-fecha');
    if (fechaOriginal) {
      var fecha = new Date(fechaOriginal);
      var opciones = { day: '2-digit', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' };

      // Formato de la fecha
      var fechaFormateada = fecha.toLocaleString('es-PE', opciones);

      // Cambiar el texto 'de' por 'del' en el año
      fechaFormateada = fechaFormateada.replace('de 2024', 'del 2024');

      element.innerHTML = fechaFormateada;
    }
  });
</script>


<jsp:include page="footer.jsp" />
