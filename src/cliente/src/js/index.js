// js/index.js

const API_URL = "http://localhost:8080/api/recuperarMediciones";
const tablaBody = document.getElementById("tbody-medidas");
const limitSelect = document.getElementById("limit");
const errorMsg = document.getElementById("error");

async function cargarMedidas() {
  const limite = limitSelect.value;

  try {
    const resp = await fetch(`${API_URL}?limit=${limite}`);
    if (!resp.ok) throw new Error(`Error HTTP: ${resp.status}`);
    const json = await resp.json();

    console.log("Respuesta API:", json); // 👈 importante para ver en consola

    const medidas = json.datos || []; // 👈 tu array está dentro de "datos"
    mostrarMedidas(medidas);
    errorMsg.hidden = true;

  } catch (error) {
    console.error("Error al cargar medidas:", error);
    errorMsg.textContent = "Error al conectar con el servidor o cargar datos.";
    errorMsg.hidden = false;
  }
}

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
        <td>${m.mac || "-"}</td>
        <td>${traducirSensor(m.sensorId)}</td>
        <td>${m.valor}</td>
        <td>${m.contador}</td>
        <td>${fecha.toLocaleDateString()}</td>
        <td>${fecha.toLocaleTimeString()}</td>
      </tr>`;
    tablaBody.insertAdjacentHTML("beforeend", fila);
  }
}

function traducirSensor(id) {
  switch (id) {
    case 11: return "CO₂";
    case 12: return "Temperatura";
    case 13: return "Ruido";
    default: return id;
  }
}

limitSelect.addEventListener("change", cargarMedidas);
setInterval(cargarMedidas, 5000);
cargarMedidas();
