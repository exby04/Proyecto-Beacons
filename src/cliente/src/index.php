<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Proyecto Biometría Ambiental</title>
  <link rel="stylesheet" href="css/index.css">
  <script defer src="js/index.js"></script>
</head>

<body>
  <header class="topbar">
    <div class="container">
      <h1>Proyecto Biometría Ambiental</h1>
      <p class="subtitle">Lectura de medidas BLE en tiempo real</p>
    </div>
  </header>

  <main class="container">
    <h2>📊 Últimas mediciones</h2>
    <label for="limit">Filas:</label>
    <select id="limit">
      <option value="1" selected>Última</option>
      <option value="10">10</option>
      <option value="50">50</option>
    </select>

    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>MAC</th>
          <th>Sensor</th>
          <th>Valor</th>
          <th>Fecha</th>
          <th>Hora</th>
          <th>UUID</th>
        </tr>
      </thead>
      <tbody id="tbody-medidas">
        <tr><td colspan="7" class="muted">Cargando datos...</td></tr>
      </tbody>
    </table>

    <p id="error" class="error" hidden></p>
  </main>

  <footer class="footer">
    <div class="container">
      <small>© 2025 Proyecto Biometría Ambiental – Alejandro Vázquez Remes</small>
    </div>
  </footer>
</body>
</html>
