<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<style>
  .responsive-iframe {
    position: relative;
    overflow: hidden;
    padding-top: 56.25%;
  }

  .responsive-iframe iframe {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    border: 0;
  }
</style>
    <main class="app-content">
      <div class="app-title">
        <div>
          <h1><i class="bi bi-building"></i> Perfil del Estacionamiento</h1>
          <p>Detalles del estacionamiento</p>
        </div>
        <ul class="app-breadcrumb breadcrumb">
          <li class="breadcrumb-item"><i class="bi bi-house-door fs-6"></i></li>
          <li class="breadcrumb-item">
            <a href="dashboard.jsp">Dashboard</a>
          </li>
          <li class="breadcrumb-item active">Perfil Estacionamiento</li>
        </ul>
      </div>

      <div class="row">
        <div class="col-md-12">
          <div class="tile">
            <div class="tile-body">
              <div class="row">
                <div class="col-md-6">
                  <h5>Información General</h5>
                  <ul class="list-group">
                    <li class="list-group-item">
                      <strong>Identificador:</strong> ${estacionamiento.id_estacionamiento}
                    </li>
                    <li class="list-group-item">
                      <strong>Nombre:</strong> ${estacionamiento.nombre}
                    </li>
                    <li class="list-group-item">
                      <strong>Capacidad:</strong> ${estacionamiento.capacidad_total} vehiculos
                    </li>
                  </ul>
                </div>
                <div class="col-md-6">
                  <h5>Horario</h5>
                  <ul class="list-group">
                    <li class="list-group-item">
                      <strong>Número de Pisos:</strong> ${estacionamiento.nro_pisos}
                    </li>
                    <li class="list-group-item">
                      <strong>Horario de Apertura:</strong> ${estacionamiento.horario_apertura} AM
                    </li>
                    <li class="list-group-item">
                      <strong>Horario de Cierre:</strong> ${estacionamiento.horario_cierre} PM
                    </li>
                  </ul>
                </div>
              </div>
              <div class="row mt-4">
                <div class="col-md-12">
                  <h5>Ubicación</h5>
                  <div class="responsive-iframe">
                    <iframe
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15848.219709244002!2d-79.8803546128418!3d-6.763157499999991!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x904cef86d621ff73%3A0xf73b86b4c59e2a9f!2sMall%20Aventura%20Chiclayo!5e0!3m2!1ses!2spe!4v1727318125875!5m2!1ses!2spe"
                            width="1200"
                            height="450"
                            style="border: 0"
                            allowfullscreen=""
                            loading="lazy"
                            referrerpolicy="no-referrer-when-downgrade"
                    ></iframe>
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- Essential javascripts for application to work-->
    <script src="js/jquery-3.7.0.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/main.js"></script>
  </body>
</html>
