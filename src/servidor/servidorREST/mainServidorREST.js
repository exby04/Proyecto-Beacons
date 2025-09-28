// servidorREST/mainServidorREST.js
const express = require("express");
const Logica = require("../Logica/Logica.js");   
const crearReglasREST = require("./ReglasREST.js");

async function main() {
    try {
        // Configuración de MySQL
        const laLogica = await new Promise((resolver, rechazar) => {
            const laLogica = new Logica(
                {
                    // Configuración de XAMPP
                    host: "localhost",
                    user: "root",
                    password: "",
                    database: "beacons"
                },
                (err) => {
                    if (err) {
                        rechazar(err);
                    } else {
                        resolver(laLogica);
                    }
                }
            );
        });

        // Creo servidor Express
        const app = express();
        app.use(express.json());

        // Cargo reglas REST
        app.use("/api", crearReglasREST(laLogica));

        // Arranco el servidor
        const servicio = app.listen(8080, () => {
            console.log("Servidor REST en http://localhost:8080/api");
        });

        // Capturo Ctrl+C
        process.on("SIGINT", function () {
            console.log("Terminando servidor...");
            servicio.close();
        });
    } catch (err) {
        console.error("Error al iniciar servidor:", err);
    }
}

main();
