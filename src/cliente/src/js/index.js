const API_URL = "http://localhost:8080/api/recuperarMediciones";
const tablaBody = document.getElementById("tbody-medidas");
const limitSelect = document.getElementById("limit");
const errorMsg = document.getElementById("error");


// Cargar datos desde la API
async function cargarMedidas() {
  const limite = limitSelect.value;

  try {
    const resp = await fetch(`${API_URL}?limit=${limite}`, { cache: "no-store" });
    if (!resp.ok) throw new Error(`HTTP ${resp.status}`);

    const json = await resp.json();
    const medidas = json.datos || []; // 👈 tu array real está en json.datos

    mostrarMedidas(medidas);
    errorMsg.hidden = true;
  } catch (err) {
    console.error("❌ Error al cargar mediciones:", err);
    errorMsg.textContent = "Error al conectar con el servidor o cargar datos.";
    errorMsg.hidden = false;
  }
}

// Mostrar datos en la tabla
function mostrarMedidas(medidas) {
  tablaBody.innerHTML = "";

  if (!medidas.length) {
    tablaBody.innerHTML = `<tr><td colspan="7" class="muted">Sin datos disponibles</td></tr>`;
    return;
  }

  for (const m of medidas) {
    const fecha = new Date(m.fecha);
    const fila = `
      <tr>
        <td>${m.id}</td>
        <td>${m.mac}</td>
        <td>${traducirSensor(m.sensorId)}</td>
        <td>${m.valor}</td>
        <td>${fecha.toLocaleDateString()}</td>
        <td>${fecha.toLocaleTimeString()}</td>
        <td>${m.uuid || '-'}</td> <!--nueva celda con UUID -->
      </tr>`;
    tablaBody.insertAdjacentHTML("beforeend", fila);
  }
}


// Traducción del ID del sensor
function traducirSensor(id) {
  switch (id) {
    case 11: return "CO₂";
    case 12: return "Temperatura";
    case 13: return "Ruido";
    default: return id;
  }
}

// Eventos
limitSelect.addEventListener("change", cargarMedidas);
setInterval(cargarMedidas, 5000);
cargarMedidas();
