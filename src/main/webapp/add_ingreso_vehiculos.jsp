<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pe.edu.pe.parking.model.Vehiculo" %>
<%@ page import="pe.edu.pe.parking.model.Tarifa" %>
<%@ page import="pe.edu.pe.parking.business.VehiculoDAO" %>
<%@ page import="pe.edu.pe.parking.business.TarifaDAO" %>
<%@ page import="pe.edu.pe.parking.model.Usuario" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />

<%
  // Llamada a VehiculoDAO y TarifaDAO
  VehiculoDAO vehiculoDAO = null;
  TarifaDAO tarifaDAO = null;
  List<Vehiculo> lista_vehiculos = null;
  List<Tarifa> lista_tarifas = null;

  Usuario user = (Usuario) session.getAttribute("usuario");

  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }

  // Obtener el ID del usuario
  int idUsuario = user.getId_usuario();

  try {
    vehiculoDAO = new VehiculoDAO();
    // Obtener lista de vehículos
    lista_vehiculos = vehiculoDAO.getAllVehiculo();
    tarifaDAO = new TarifaDAO();
    // Obtener lista de tarifas
    lista_tarifas = tarifaDAO.gettAllTarifa();

    // Pasar listas como atributos
    request.setAttribute("lista_vehiculos", lista_vehiculos);
    request.setAttribute("lista_tarifas", lista_tarifas);

  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    if (vehiculoDAO != null) vehiculoDAO.close();
    if (tarifaDAO != null) tarifaDAO.close();
  }
%>

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-ui-checks"></i> Formulario Ingreso Vehículos</h1>
      <p>Detalle Ingreso Vehículos</p>
    </div>
    <ul class="app-breadcrumb breadcrumb">
      <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
      <li class="breadcrumb-item">Forms</li>
      <li class="breadcrumb-item"><a href="vehiculo">Vehículo</a></li>
    </ul>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <h3 class="tile-title">Registrar Ingreso Vehículos</h3>
        <div class="tile-body">
          <form action="ingreso_vehiculos" method="post">
            <input type="hidden" name="accion" value="registrar">

            <div class="row mb-3">
              <div class="col-md-4">
                <label class="form-label">Id Tarifa</label>
                <select class="form-control" name="id_tarifa" required>
                  <c:forEach var="tarifa" items="${lista_tarifas}">
                    <option value="${tarifa.id_tarifa}">
                        ${tarifa.tipo_vehiculo} - S/. ${tarifa.precio_por_minuto} por minuto
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-4">
                <label class="form-label">Número de Placa</label>
                <select class="form-control" name="numeroPlaca" required>
                  <c:forEach var="vehiculo" items="${lista_vehiculos}">
                    <option value="${vehiculo.numero_placa}">
                        ${vehiculo.numero_placa} - ${vehiculo.tipo_vehiculo}
                    </option>
                  </c:forEach>
                </select>
              </div>

              <div class="col-md-4">
                <label class="form-label">ID Estacionamiento</label>
                <input type="hidden" name="id_estacionamiento" value="1" />
                <input class="form-control" type="text" value="1" readonly />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label class="form-label">ID Usuario</label>
                <input name="id_usuario" id="id_usuario" class="form-control" type="text" value="<%= idUsuario %>" readonly />
              </div>

              <div class="col-md-6">
                <label class="form-label">Piso</label>
                <input class="form-control" type="number" name="piso" id="piso" placeholder="Piso del estacionamiento" min="1" max="10" required />
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label class="form-label">Observación</label>
                <input class="form-control" type="text" name="observacion" placeholder="Observaciones del vehículo" />
              </div>
            </div>
            <button type="submit" class="btn btn-primary">Registrar Ingreso</button>
            <button type="button" class="btn btn-danger" onclick="window.history.back();">Volver</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</main>

<jsp:include page="footer.jsp" />
