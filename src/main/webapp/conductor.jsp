<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
      <div class="app-title">
        <div>
          <h1><i class="bi bi-table"></i> Conductor</h1>
          <p>Lista de Conductores</p>
        </div>

        <ul class="app-breadcrumb breadcrumb side">
          <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
          <li class="breadcrumb-item">Tables</li>
          <li class="breadcrumb-item active"><a href="#">Conductor</a></li>
        </ul>
      </div>
      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <div class="table-responsive">
                <a href="add_vehiculo_conductor.jsp"
                  ><button
                    class="btn btn-primary"
                    style="position: absolute; right: 25px"
                  >
                    Agregar
                  </button></a
                >
                <br />
                  <table class="table table-hover table-bordered" id="sampleTable">
                      <thead>
                      <tr>
                          <th>Dni Conductor</th>
                          <th>Nombre Completo</th>
                          <th>Fecha Nacimiento</th>
                          <th>Género</th>
                          <th>Móvil</th>
                          <th>Email</th>
                          <th>Dirección</th>
                          <th>Acciones</th>
                      </tr>
                      </thead>
                      <tbody>
                      <c:forEach var="elemento" items="${lista_conductores}">
                          <tr>
                              <td>${elemento.dni_conductor}</td>
                              <td>${elemento.nombre_completo}</td>
                              <td>${elemento.fecha_nacimiento}</td>
                              <td>${elemento.genero}</td>
                              <td>${elemento.celular}</td>
                              <td>${elemento.correo}</td>
                              <td>${elemento.direccion}</td>
                              <td>
                                  <a href="${pageContext.request.contextPath}/ObtenerConductor?dniConductor=${elemento.dni_conductor}">
                                      <button class="btn btn-warning"><i class="bi bi-pencil"></i></button></a>

                                  <a href="${pageContext.request.contextPath}/enviarDatosConductor?dniConductor=${elemento.dni_conductor}">
                                      <button class="btn btn-danger"><i class="bi bi-car-front"></i></button>
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
