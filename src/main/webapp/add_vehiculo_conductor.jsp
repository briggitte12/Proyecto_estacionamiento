<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-car-front"></i> Registro de Conductor y Vehículo</h1>
      <p>Formulario para registrar un conductor y su vehículo</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <div class="tile-body">
          <form action="vehiculoConductor" method="POST">
            <!-- Datos del conductor -->
            <c:choose>
              <c:when test="${not empty conductor}">
                <div class="row mb-3">
                  <div class="col-md-6">
                    <input type="hidden" id="dniConductor" name="dniConductor" value="${conductor.dni_conductor}" />
                    <input type="hidden" id="nombreCompleto" name="nombreCompleto" value="${conductor.nombre_completo}" />
                    <input type="hidden" id="celular" name="celular" value="${conductor.celular}" />
                    <input type="hidden" id="correo" name="correo" value="${conductor.correo}" />
                  </div>
                  <div class="col-md-6">
                    <input type="hidden" id="fechaNacimiento" name="fechaNacimiento" value="${conductor.fecha_nacimiento}" />
                    <input type="hidden" id="direccion" name="direccion" value="${conductor.direccion}" />
                    <input type="hidden" id="genero" name="genero" value="${conductor.genero}" />
                  </div>
                </div>
                    <h3>Conductor: ${conductor.nombre_completo}</h3>
                    <h4>Datos del Conductor Registrado</h4>
                    <p><strong>DNI:</strong> ${conductor.dni_conductor}</p>
                    <p><strong>Celular:</strong> ${conductor.celular}</p>
                    <p><strong>Correo:</strong> ${conductor.correo}</p>
                    <p><strong>Fecha de Nacimiento:</strong> ${conductor.fecha_nacimiento}</p>
                    <p><strong>Dirección:</strong> ${conductor.direccion}</p>
                    <p><strong>Género:</strong> ${conductor.genero}</p>
              </c:when>
              <c:otherwise>
                <h3>Datos del Conductor</h3>
                <div class="row mb-3">
                  <div class="col-md-6">
                    <label for="dniConductor">DNI del Conductor:</label>
                    <input maxlength="8" type="text" class="form-control" id="dniConductor" name="dniConductor">
                  </div>
                  <div class="col-md-6">
                    <label for="nombreCompleto">Nombre Completo:</label>
                    <input type="text" class="form-control" id="nombreCompleto" name="nombreCompleto">
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col-md-6">
                    <label for="direccion">Dirección:</label>
                    <input type="text" class="form-control" id="direccion" name="direccion">
                  </div>
                  <div class="col-md-6">
                      <label for="correo">Correo Electrónico:</label>
                      <input type="email" class="form-control" id="correo" name="correo">
                  </div>
                </div>
                <div class="row mb-3">
                  <div class="col-md-4">
                    <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                    <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento"
                           max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" required>
                  </div>
                  <div class="col-md-4">
                    <label for="genero">Género:</label>
                    <select class="form-control" id="genero" name="genero">
                      <option value="Masculino">Masculino</option>
                      <option value="Femenino">Femenino</option>
                    </select>
                  </div>
                  <div class="col-md-4">
                    <label for="celular">Celular:</label>
                    <input type="text" class="form-control" id="celular" name="celular" maxlength="9">
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
            </br>
            <!-- Datos del vehículo -->
            <h3>Datos del Vehiculo</h3>
            <div class="row mb-3">
              <div class="col-md-4">
                <label for="numeroPlaca">Número de Placa:</label>
                <input placeholder="ABC-123" maxlength="15" type="text" class="form-control" id="numeroPlaca" name="numeroPlaca">
              </div>
              <div class="col-md-4">
                <label for="marca">Marca:</label>
                <input type="text" class="form-control" id="marca" name="marca">
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
                <input type="text" class="form-control" id="color" name="color">
              </div>
              <div class="col-md-6">
                <label for="addVehiculo">¿Desea registrar otro vehículo?</label>
                <select class="form-control" id="addVehiculo" name="addVehiculo">
                  <option value="no">No</option>
                  <option value="si">Sí</option>
                </select>
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

