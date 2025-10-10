// servidorREST/ReglasREST.js
const express = require("express");

module.exports = function crearReglasREST(logica) {
  const router = express.Router();

  // .......................................................
  // POST /guardarMediciones
  // .......................................................
  router.post("/guardarMediciones", async (req, res) => {
    try {
      const datos = req.body;

      // Validación básica
      if (!datos.mac || typeof datos.valor === "undefined") {
        return res.status(400).json({
          ok: false,
          error: "Faltan campos requeridos: mac y valor",
        });
      }

      // Inserta medición directamente (sin tabla Dispositivo)
      await logica.insertarMedicion({
        mac: datos.mac,
        uuid: datos.uuid || null,
        valor: datos.valor,
        fecha: datos.fecha || new Date(),
        sensorId: datos.sensorId || null,
        rssi: datos.rssi || null,
      });

      res.status(201).json({
        ok: true,
        mensaje: "Medición guardada correctamente",
      });
    } catch (err) {
      console.error("❌ Error en POST /guardarMediciones:", err);
      res.status(500).json({
        ok: false,
        error: err.message,
      });
    }
  });

  // .......................................................
  // GET /recuperarMediciones
  // .......................................................
  router.get("/recuperarMediciones", async (req, res) => {
    try {
      const filas = await logica.buscarMediciones();
      res.json({
        ok: true,
        datos: filas,
      });
    } catch (err) {
      console.error("❌ Error en GET /recuperarMediciones:", err);
      res.status(500).json({
        ok: false,
        error: err.message,
      });
    }
  });

  return router;
};
