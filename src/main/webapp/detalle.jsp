<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
      <div class="app-title">
        <div>
          <h1><i class="bi bi-table"></i> Pago</h1>
          <p>Lista</p>
        </div>

        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
          <li class="breadcrumb-item">Tables</li>
          <li class="breadcrumb-item active"><a href="#">Pago</a></li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <div class="table-responsive">
                <table
                  class="table table-hover table-bordered"
                  id="sampleTable"
                >
                  <thead>
                    <tr>
                      <th>Id Cobro</th>
                      <th>Id Registro</th>
                      <th>Nombre Completo</th>
                      <th>Dni Conductor</th>
                      <th>Placa</th>
                      <th>Tipo Vehiculo</th>
                      <th>Minutos Estacionado</th>
                      <th>Monto Total</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="elemento" items="${lista_cobro}">
                    <tr>
                      <td>${elemento.id_cobro}</td>
                      <td>${elemento.id_registro}</td>
                      <td>${elemento.nombre_conductor}</td>
                      <td>${elemento.dni_conductor}</td>
                      <td>${elemento.placa_vehiculo}</td>
                      <td>${elemento.tipo_vehiculo}</td>
                      <td>${elemento.minutos_estacionado}</td>
                      <td>${elemento.monto_total}</td>
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
<jsp:include page="footer.jsp" />