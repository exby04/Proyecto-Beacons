// .....................................................................
// ReglasREST.js
// .....................................................................

module.exports.cargar = function (servidorExpress, laLogica) {

    // .......................................................
    // GET /prueba
    // .......................................................
    servidorExpress.get("/prueba", function (peticion, respuesta) {
        console.log(" * GET /prueba ");
        respuesta.send("¡Funciona!");
    });

    // .......................................................
    // GET /usuario/:id
    // .......................................................
    servidorExpress.get("/usuario/:id", async function (peticion, respuesta) {
        console.log(" * GET /usuario/:id ");

        try {
            const id = peticion.params.id;
            const res = await laLogica.buscarUsuarioPorID(id);

            if (!res || res.length !== 1) {
                respuesta.status(404).send("No encontré usuario con id: " + id);
                return;
            }

            respuesta.send(JSON.stringify(res[0]));
        } catch (error) {
            console.error("Error en GET /usuario/:id:", error);
            respuesta.status(500).send("Error interno del servidor");
        }
    });

    // .......................................................
    // POST /usuario
    // .......................................................
    servidorExpress.post("/usuario", async function (peticion, respuesta) {
        console.log(" * POST /usuario ");

        try {
            const datos = peticion.body;

            if (!datos.nombre || !datos.edad) {
                respuesta.status(400).send("Faltan campos requeridos (nombre, edad)");
                return;
            }

            await laLogica.insertarUsuario(datos);

            respuesta.status(201).send("Usuario insertado correctamente");
        } catch (error) {
            console.error("Error en POST /usuario:", error);
            respuesta.status(500).send("Error interno del servidor");
        }
    });

    // .......................................................
    // DELETE /usuario/:id
    // .......................................................
    servidorExpress.delete("/usuario/:id", async function (peticion, respuesta) {
        console.log(" * DELETE /usuario/:id ");

        try {
            const id = peticion.params.id;
            const borrados = await laLogica.borrarUsuarioPorID(id);

            if (borrados === 0) {
                respuesta.status(404).send("No encontré usuario con id: " + id);
                return;
            }

            respuesta.send("Usuario borrado correctamente");
        } catch (error) {
            console.error("Error en DELETE /usuario/:id:", error);
            respuesta.status(500).send("Error interno del servidor");
        }
    });
};
