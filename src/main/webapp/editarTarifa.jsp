<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="header.jsp" />

<main class="app-content">
  <div class="app-title">
    <div>
      <h1><i class="bi bi-car-front"></i> Editar Tarifa </h1>
      <p>Formulario para editar Tarifa</p>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <div class="tile">
        <div class="tile-body">
            <form action="ActualizarTarifa" method="post">
            <input type="hidden" id="id_tarifa" name="id_tarifa"  value="${tarifa.id_tarifa}" />
            <div class="row mb-3">
                <div class="col-md-6">
                  <label for="precio_por_minuto">Precio por Minuto:</label>
                  <input  type="number" step="1.0" class="form-control"  id="precio_por_minuto" name="precio_por_minuto" value="${tarifa.precio_por_minuto}" />
                </div>
            </div>
              <button type="submit" class="btn btn-info">Editar</button>
            </form>
        </div>
      </div>
    </div>
  </div>
</main>