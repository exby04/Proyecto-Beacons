// servidorREST/mainServidorREST.js
const express = require("express");
const cors = require("cors"); 
const Logica = require("../Logica/Logica.js");
const crearReglasREST = require("./ReglasREST.js");

async function main() {
  try {
    // Conexión a MySQL en Plesk
    const laLogica = await new Promise((resolver, rechazar) => {
      const laLogica = new Logica(
        {
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

    // Crear servidor Express
    const app = express();
    app.use(cors()); 
    app.use(express.json());

    // Reglas REST
    app.use("/api", crearReglasREST(laLogica));

    // Iniciar servidor
    const puerto = process.env.PORT || 8080;
    const servicio = app.listen(puerto, () => {
      console.log(`Servidor REST en puerto ${puerto}`);
    });

    // Capturar Ctrl+C
    process.on("SIGINT", function () {
      console.log("Terminando servidor...");
      servicio.close();
    });
  } catch (err) {
    console.error("Error al iniciar servidor:", err);
  }
}

main();
