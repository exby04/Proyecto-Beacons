// js/index.js

const API_URL = "http://192.168.84.97:8080/api/recuperarMediciones"; 
const tablaBody = document.getElementById("tbody-medidas");
const limitSelect = document.getElementById("limit");
const errorMsg = document.getElementById("error");

async function cargarMedidas() {
  const limite = limitSelect.value;
  try {
    const resp = await fetch(`${API_URL}?limit=${limite}`);
    if (!resp.ok) throw new Error(`Error HTTP: ${resp.status}`);
    const datos = await resp.json();

    mostrarMedidas(datos);
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
    const fila = `
      <tr>
        <td>${m.id}</td>
        <td>${m.uuid}</td>
        <td>${m.sensor}</td>
        <td>${m.valor}</td>
        <td>${m.contador}</td>
        <td>${new Date(m.timestamp).toLocaleDateString()}</td>
        <td>${new Date(m.timestamp).toLocaleTimeString()}</td>
      </tr>`;
    tablaBody.insertAdjacentHTML("beforeend", fila);
  }
}

limitSelect.addEventListener("change", cargarMedidas);

// refrescar cada 5 segundos
setInterval(cargarMedidas, 5000);
cargarMedidas();
