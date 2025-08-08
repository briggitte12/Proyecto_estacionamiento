<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-car-front"></i> Registro de Vehículo</h1>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <div class="tile-body">
          <form action="registroVehiculoSolo" method="POST">
            <input type="hidden" name="dniConductor" value="${param.dniConductor}" />

            <h3>Datos del Vehículo</h3>
            <div class="row mb-3">
              <div class="col-md-4">
                <label for="numeroPlaca">Número de Placa:</label>
                <input placeholder="ABC-123" maxlength="15" type="text" class="form-control" id="numeroPlaca" name="numeroPlaca" required>
              </div>
              <div class="col-md-4">
                <label for="marca">Marca:</label>
                <input type="text" class="form-control" id="marca" name="marca" required>
              </div>
              <div class="col-md-4">
                <label for="tipoVehiculo">Tipo de Vehículo:</label>
                <select class="form-control" id="tipoVehiculo" name="tipoVehiculo">
                  <option value="Carro">Carro</option>
                  <option value="MotoTaxi">MotoTaxi</option>
                  <option value="MotoLineal">MotoLineal</option>
                  <option value="Camioneta">Camioneta</option>
                </select>
              </div>
            </div>
            <div class="row mb-3">
              <div class="col-md-6">
                <label for="color">Color:</label>
                <input type="text" class="form-control" id="color" name="color" required>
              </div>
            </div>

            <button type="submit" class="btn btn-primary">Registrar Vehículo</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>

<jsp:include page="footer.jsp" />
