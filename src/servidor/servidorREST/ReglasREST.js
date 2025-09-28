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

        if (!datos.mac || typeof datos.valor === "undefined") {
            return res.status(400).json({
                ok: false,
                error: "Faltan campos requeridos: mac y valor",
            });
        }

        // Inserta dispositivo y luego la medición con más campos
        await logica.insertarDispositivo(datos.mac, datos.nombre || "GTI");
        await logica.insertarMedicion(datos);

        res.status(201).json({
            ok: true,
            mensaje: "Medición guardada",
        });
    } catch (err) {
        console.error("Error en POST /guardarMediciones:", err);
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
                datos: filas
            });
        } catch (err) {
            console.error("Error en GET /recuperarMediciones:", err);
            res.status(500).json({
                ok: false,
                error: err.message,
            });
        }
    });

    // .......................................................
    // GET /recuperarMediciones/:mac
    // .......................................................
    router.get("/recuperarMediciones/:mac", async (req, res) => {
        try {
            const filas = await logica.buscarMedicionesPorMac(req.params.mac);

            if (!filas || filas.length === 0) {
                return res.status(404).json({
                    ok: false,
                    error: "No se encontraron mediciones para ese dispositivo",
                });
            }

            res.json({
                ok: true,
                datos: filas
            });
        } catch (err) {
            console.error("Error en GET /recuperarMediciones/:mac:", err);
            res.status(500).json({
                ok: false,
                error: err.message,
            });
        }
    });

    // .......................................................
    // GET /dispositivos
    // .......................................................
    router.get("/dispositivos", async (req, res) => {
        try {
            const filas = await logica.buscarDispositivos();
            res.json({
                ok: true,
                datos: filas
            });
        } catch (err) {
            console.error("Error en GET /dispositivos:", err);
            res.status(500).json({
                ok: false,
                error: err.message,
            });
        }
    });

    return router;
};
