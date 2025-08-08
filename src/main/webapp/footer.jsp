<!-- Essential javascripts for application to work-->
<script src="js/jquery-3.7.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/main.js"></script>
<!-- Botones para pdf , excel-->
<script type="text/javascript" src="datatables/datatables.js"></script>
<script type="text/javascript" src="datatables/exportar.js"></script>

<!-- Page specific javascripts-->
<link
        rel="stylesheet"
        href="https://cdn.datatables.net/v/bs5/dt-1.13.4/datatables.min.css"
/>

<script
        type="text/javascript"
        src="js/plugins/dataTables.bootstrap.min.js"
></script>

<!-- Google analytics script-->
<script type="text/javascript">
    if (document.location.hostname == "pratikborsadiya.in") {
        (function (i, s, o, g, r, a, m) {
            i["GoogleAnalyticsObject"] = r;
            (i[r] =
                i[r] ||
                function () {
                    (i[r].q = i[r].q || []).push(arguments);
                }),
                (i[r].l = 1 * new Date());
            (a = s.createElement(o)), (m = s.getElementsByTagName(o)[0]);
            a.async = 1;
            a.src = g;
            m.parentNode.insertBefore(a, m);
        })(
            window,
            document,
            "script",
            "//www.google-analytics.com/analytics.js",
            "ga"
        );
        ga("create", "UA-72504830-1", "auto");
        ga("send", "pageview");
    }
</script>

<script>
    // Cargar el header desde un archivo externo
    fetch("menu.html")
        .then((response) => response.text())
        .then((data) => {
            document.getElementById("menu").innerHTML = data;
        })
        .catch((error) => console.error("Error al cargar el header:", error));
</script>
</body>
</html>