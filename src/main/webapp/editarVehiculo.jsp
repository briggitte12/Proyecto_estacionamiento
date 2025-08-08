<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-car-front"></i> Editar Vehículo</h1>
      <p>Formulario para actualizar los datos del vehículo</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <div class="tile-body">

          <form action="ActualizarVehiculo" method="POST">

            <!-- Datos del vehículo -->
            <h3>Datos del Vehículo</h3>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="numero_placa">Número de Placa:</label>
                <input type="text" class="form-control" id="numero_placa" name="numero_placa" value="${vehiculo.numero_placa}" readonly />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="tipoVehiculo">Tipo de Vehículo:</label>
                <select class="form-control" id="tipo_vehiculo" name="tipo_vehiculo">
                  <option value="Carro" ${vehiculo.tipo_vehiculo == 'Carro' ? 'selected' : ''}>Carro</option>
                  <option value="MotoTaxi" ${vehiculo.tipo_vehiculo == 'MotoTaxi' ? 'selected' : ''}>MotoTaxi</option>
                  <option value="MotoLineal" ${vehiculo.tipo_vehiculo == 'MotoLineal' ? 'selected' : ''}>MotoLineal</option>
                  <option value="Camioneta" ${vehiculo.tipo_vehiculo == 'Camioneta' ? 'selected' : ''}>Camioneta</option>
                </select>
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="marca">Marca:</label>
                <input type="text" class="form-control" id="marca" name="marca" value="${vehiculo.marca}" required />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="color">Color:</label>
                <input type="text" class="form-control" id="color" name="color" value="${vehiculo.color}" required />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <input type="hidden" class="form-control" id="dni_conductor" name="dni_conductor" value="${vehiculo.dni_conductor}" required />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>

<jsp:include page="footer.jsp" />
