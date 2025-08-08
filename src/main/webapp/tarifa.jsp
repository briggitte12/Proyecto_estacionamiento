<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<h3>Nuestras Tarifas</h3>
<div class="row">
  <div class="col-md-12">
    <div class="tile">
      <div class="tile-body">
        <div class="table-responsive">
          <br />
          <table
                  class="table table-hover table-bordered"
                  id="sampleTable"
          >
            <thead>
            <tr>
              <th>Id Tarifa</th>
              <th>Tipo Vehiculo</th>
              <th>Precio Por Minuto</th>
              <th>Acciones</th>
            </tr>
            </thead>
              <tbody>
              <c:forEach var="elemento" items="${lista_tarifa}">
              <tr>
                <td>${elemento.id_tarifa}</td>
                <td>${elemento.tipo_vehiculo}</td>
                <td>S/${elemento.precio_por_minuto}</td>
                <td>
                  <a href="${pageContext.request.contextPath}/ObtenerTarifa?id_tarifa=${elemento.id_tarifa}">
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


