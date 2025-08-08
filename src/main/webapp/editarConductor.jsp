<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="header.jsp" />

<main class="app-content">
    <div class="app-title">
        <div>
            <h1><i class="bi bi-car-front"></i> Editar Conductor</h1>
            <p>Formulario para editar la información del conductor</p>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="tile">
                <div class="tile-body">
                    <form action="ActualizarConductor" method="POST">
                        <!-- Datos del conductor -->
                        <h3>Datos del Conductor</h3>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="dniConductor">DNI del Conductor:</label>
                                <input type="text" class="form-control" id="dniConductor" name="dni_conductor"
                                       value="${conductor.dni_conductor}" readonly required>
                            </div>
                            <div class="col-md-6">
                                <label for="nombreCompleto">Nombre Completo:</label>
                                <input type="text" class="form-control" id="nombreCompleto" name="nombre_completo"
                                       value="${conductor.nombre_completo}" required>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label for="direccion">Dirección:</label>
                                <input type="text" class="form-control" id="direccion" name="direccion"
                                       value="${conductor.direccion}" required>
                            </div>
                            <div class="col-md-6">
                                <label for="correo">Correo Electrónico:</label>
                                <input type="email" class="form-control" id="correo" name="correo"
                                       value="${conductor.correo}" required>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-md-4">
                                <label for="fechaNacimiento">Fecha de Nacimiento:</label>
                                <input type="date" class="form-control" id="fechaNacimiento" name="fecha_nacimiento"
                                       value="${fn:substring(conductor.fecha_nacimiento, 0, 10)}" required>
                            </div>
                            <div class="col-md-4">
                                <label for="genero">Género:</label>
                                <select class="form-control" id="genero" name="genero" required>
                                    <option value="Masculino" ${conductor.genero == 'Masculino' ? 'selected' : ''}>Masculino</option>
                                    <option value="Femenino" ${conductor.genero == 'Femenino' ? 'selected' : ''}>Femenino</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="celular">Celular:</label>
                                <input type="text" class="form-control" id="celular" name="celular"
                                       value="${conductor.celular}" maxlength="9" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">Actualizar</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="footer.jsp" />
