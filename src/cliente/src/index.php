<?php
// ux/src/index.php
// Interfaz web del proyecto Biometría Ambiental
// Este archivo solo genera HTML (no ejecuta PHP real todavía)
?>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Proyecto Biometría – Medidas</title>

  <!-- 🧩 CSS principal -->
  <link rel="stylesheet" href="css/index.css" />

  <!-- ⚙️ Script principal -->
  <script defer src="js/index.js"></script>
</head>

<body>
  <!-- ========================================= -->
  <!-- ENCABEZADO -->
  <!-- ========================================= -->
  <header class="topbar">
    <div class="container">
      <h1>Proyecto Biometría Ambiental</h1>
      <p class="subtitle">Lectura de medidas BLE en tiempo real</p>
    </div>
  </header>

  <!-- ========================================= -->
  <!-- CONTENIDO PRINCIPAL -->
  <!-- ========================================= -->
  <main class="container">
    <section class="panel">
      <div class="panel-header">
        <h2>📊 Últimas mediciones</h2>
        <div class="controls">
          <label for="limit">Filas:</label>
          <select id="limit">
            <option value="1" selected>Última medida</option>
            <option value="10">10</option>
            <option value="50">50</option>
            <option value="100">100</option>
            <option value="300">300</option>
          </select>
        </div>
      </div>

      <!-- ========================================= -->
      <!-- TABLA DE MEDIDAS -->
      <!-- ========================================= -->
      <div class="table-wrap">
        <table class="table" id="tabla-medidas">
          <thead>
            <tr>
              <th>ID</th>
              <th>UUID</th>
              <th>Sensor</th>
              <th>Valor</th>
              <th>Contador</th>
              <th>Fecha</th>
              <th>Hora</th>
            </tr>
          </thead>
          <tbody id="tbody-medidas">
            <!-- Mensaje inicial -->
            <tr><td colspan="7" class="muted">Cargando datos...</td></tr>
          </tbody>
        </table>
      </div>

      <!-- Mensaje de error -->
      <p id="error" class="error" hidden></p>
    </section>
  </main>

  <!-- ========================================= -->
  <!-- PIE DE PÁGINA -->
  <!-- ========================================= -->
  <footer class="footer">
    <div class="container">
      <small>© 2025 Proyecto Biometría Ambiental – Desarrollado por Alejandro Vázquez Remes</small>
    </div>
  </footer>
</body>
</html>
