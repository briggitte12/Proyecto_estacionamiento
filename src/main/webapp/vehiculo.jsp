<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
      <div class="app-title">
        <div>
          <h1><i class="bi bi-table"></i> Vehiculos</h1>
          <p>Lista de Vehiculos</p>
        </div>

        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
          <li class="breadcrumb-item">Tables</li>
          <li class="breadcrumb-item active"><a href="#">Vehiculos</a></li>
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
                      <th>Placa</th>
                      <th>Tipo Vehiculo</th>
                      <th>Marca</th>
                      <th>Color</th>
                      <th>Dni_Conductor</th>
                      <th>Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                  <c:forEach var="elemento" items="${lista_vehiculos}">
                    <tr>
                      <td>${elemento.numero_placa}</td>
                      <td>${elemento.tipo_vehiculo}</td>
                      <td>${elemento.marca}</td>
                      <td>${elemento.color}</td>
                      <td>${elemento.dni_conductor}</td>
                      <td>
                        <a href="${pageContext.request.contextPath}/ObtenerVehiculo?numeroPlaca=${elemento.numero_placa}">
                          <button class="btn btn-warning">
                            <i class="bi bi-pencil"></i>
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
<jsp:include page="footer.jsp" />
