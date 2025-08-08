<%@ page import="pe.edu.pe.parking.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario user = (Usuario) session.getAttribute("usuario");
    if (user != null) {
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta
            name="description"
            content="Vali is a responsive and free admin theme built with Bootstrap 5, SASS and PUG.js. It's fully customizable and modular."
    />
    <!-- Twitter meta-->
    <meta property="twitter:card" content="summary_large_image" />
    <meta property="twitter:site" content="@pratikborsadiya" />
    <meta property="twitter:creator" content="@pratikborsadiya" />
    <!-- Open Graph Meta-->
    <meta property="og:type" content="website" />
    <meta property="og:site_name" content="Vali Admin" />
    <meta property="og:title" content="Vali - Free Bootstrap 5 admin theme" />
    <meta
            property="og:url"
            content="http://pratikborsadiya.in/blog/vali-admin"
    />
    <meta
            property="og:image"
            content="http://pratikborsadiya.in/blog/vali-admin/hero-social.png"
    />
    <meta
            property="og:description"
            content="Vali is a responsive and free admin theme built with Bootstrap 5, SASS and PUG.js. It's fully customizable and modular."
    />
    <title>Sistema Parking</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="css/main.css" />
    <!-- Font-icon css-->
    <link
            rel="stylesheet"
            type="text/css"
            href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
    />
    <!--Para exportar-->
    <link href="datatables/datatables.min.css" rel="stylesheet" />
    <link rel="icon" type="image/png" href="images/logo.png">
</head>
<body class="app sidebar-mini">
<!-- Navbar-->
<header class="app-header">
    <a class="app-header__logo" href="/dashboard">P a r k i n g</a>
    <!-- Sidebar toggle button--><a
        class="app-sidebar__toggle"
        href="#"
        data-toggle="sidebar"
        aria-label="Hide Sidebar"
></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">
        <!--<li class="app-search">
            <input class="app-search__input" type="search" placeholder="Search" />
            <button class="app-search__button">
                <i class="bi bi-search"></i>
            </button>
        </li>-->
        <!-- User Menu-->
        <li class="dropdown">
            <a
                    class="app-nav__item"
                    href="#"
                    data-bs-toggle="dropdown"
                    aria-label="Open Profile Menu"
            ><i class="bi bi-person fs-4"></i
            ></a>
            <ul class="dropdown-menu settings-menu dropdown-menu-right">
                <li>
                    <a class="dropdown-item" href="usuario"
                    ><i class="bi bi-person-circle"></i> Profile</a
                    >
                </li>
                <li>
                    <a class="dropdown-item" href="logout"
                    ><i class="bi bi-box-arrow-right me-2 fs-5"></i> Logout</a
                    >
                </li>
            </ul>
        </li>
    </ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user">
        <img class="app-sidebar__user-avatar" src="images/<%= user.getFoto() %>" alt="User Image" />
        <div>
            <p class="app-sidebar__user-name"><%= user.getUsuario() %></p>
            <p class="app-sidebar__user-designation"><%= user.getRol() %></p>
        </div>
    </div>
    <%
    } else {
    %>
    <header class="app-header">
        <p class="app-header__user-name">Invitado</p>
    </header>
    <%
        }
    %>
    <ul class="app-menu">
        <li>
            <a class="app-menu__item" href="dashboard"
            ><i class="app-menu__icon bi bi-speedometer"></i
            ><span class="app-menu__label">Dashboard</span></a
            >
        </li>
        <li class="treeview">
            <a class="app-menu__item" href="#" data-toggle="treeview"
            ><i class="app-menu__icon bi bi-person-circle"></i
            ><span class="app-menu__label"> Perfiles</span
            ><i class="treeview-indicator bi bi-chevron-right"></i
            ></a>
            <ul class="treeview-menu">
                <li>
                    <a class="treeview-item" href="usuario"
                    ><i class="icon bi bi-circle-fill"></i> Usuario</a
                    >
                </li>
                <li>
                    <a class="treeview-item" href="estacionamiento"
                    ><i class="icon bi bi-circle-fill"></i> Estacionamiento</a
                    >
                </li>
            </ul>
        </li>
        <li class="treeview">
            <a class="app-menu__item" href="#" data-toggle="treeview"
            ><iz class="app-menu__icon bi bi-car-front-fill"></iz
            ><span class="app-menu__label">Registro</span
            ><i class="treeview-indicator bi bi-chevron-right"></i
            ></a>
            <ul class="treeview-menu">
                <li>
                    <a class="treeview-item" href="conductor"
                    ><i class="icon bi bi-circle-fill"></i> Conductores</a
                    >
                </li>
                <li>
                    <a class="treeview-item" href="vehiculo"
                    ><i class="icon bi bi-circle-fill"></i> Vehiculos</a
                    >
                </li>
            </ul>
        </li>
        <li class="treeview">
            <a class="app-menu__item" href="#" data-toggle="treeview"
            ><i class="app-menu__icon bi bi-p-circle-fill"></i
            ><span class="app-menu__label"> Ingreso Vehiculos</span
            ><i class="treeview-indicator bi bi-chevron-right"></i
            ></a>
            <ul class="treeview-menu">
                <li>
                    <a class="treeview-item" href="registro_vehiculos"
                    ><i class="icon bi bi-circle-fill"></i> Registro</a
                    >
                </li>
            </ul>
        </li>
        <li class="treeview">
            <a class="app-menu__item" href="#" data-toggle="treeview"
            ><i class="app-menu__icon bi bi-coin"></i
            ><span class="app-menu__label"> Pago</span
            ><i class="treeview-indicator bi bi-chevron-right"></i
            ></a>
            <ul class="treeview-menu">
                <li>
                    <a class="treeview-item" href="detalle"
                    ><i class="icon bi bi-circle-fill"></i> Detalle</a
                    >
                </li>
            </ul>
        </li>
    </ul>
</aside>

