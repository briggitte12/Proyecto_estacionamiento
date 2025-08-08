<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-ui-checks"></i> Formulario Pago</h1>
      <p>Detalle del pago calculado</p>
    </div>
    <ul class="app-breadcrumb breadcrumb">
      <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
      <li class="breadcrumb-item">Forms</li>
      <li class="breadcrumb-item"><a href="/detalle">detalle</a></li>
    </ul>
  </div>
  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <h3 class="tile-title">Registrar Pago</h3>
        <div class="tile-body">
          <form action="RegistrarCobro" method="post">


            <input type="hidden" name="fechaIngreso" value="${param.fechaIngreso}">
            <input type="hidden" name="fechaSalida" value="${param.fechaSalida}">
            <input type="hidden" name="id_tarifa" value="${param.id_tarifa}">


            <div class="row mb-3">
              <div class="col-md-6">
                <label class="form-label">Tarifa Seleccionada (ID)</label>
                <input class="form-control" type="text" name="tarifaSeleccionada" id="tarifaSeleccionada" value="${param.id_tarifa}" readonly />
              </div>
              <div class="col-md-6">
                <label class="form-label">Registro (ID)</label>
                <input class="form-control" type="text" name="id_registro" id="id_registro" value="${param.id_registro}" readonly />
              </div>
            </div>
            <!-- Minutos Estacionados Calculados -->
            <div class="row mb-3">
              <div class="col-md-6">
                <label class="form-label">Minutos Estacionado</label>
                <input class="form-control" type="text" id="minutosEstacionado" name="minutosEstacionado" value="<%= request.getAttribute("minutosEstacionado") != null ? request.getAttribute("minutosEstacionado") : "0" %>" readonly />
              </div>

              <!-- Monto Total Calculado -->
              <div class="col-md-6">
                <label class="form-label">Monto Total</label>
                <input class="form-control" type="text" id="montoTotal" name="montoTotal" value="S/. <%= request.getAttribute("montoTotal") != null ? String.format("%.2f", (Double) request.getAttribute("montoTotal")) : "0.00" %>" readonly />
              </div>
            </div>

            <button type="submit" class="btn btn-primary">Registrar</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>

<jsp:include page="footer.jsp" />
