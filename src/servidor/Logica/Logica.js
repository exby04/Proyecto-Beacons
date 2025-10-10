// logica/Logica.js
const mysql = require("mysql2/promise");

// Formatea una fecha JS a formato SQL (YYYY-MM-DD HH:MM:SS)
function formatearFecha(fecha = new Date()) {
    return fecha.toISOString().slice(0, 19).replace("T", " ");
}

module.exports = class Logica {
    constructor(config, cb) {
        mysql.createConnection(config)
            .then((conn) => {
                this.laConexion = conn;
                cb(null);
            })
            .catch((err) => cb(err));
    }

    // .....................................................
    // Inserta medición asociada a un dispositivo (con UUID)
    // .....................................................
    async insertarMedicion({ mac, uuid, valor, fecha, sensorId, rssi }) {
        const fechaSQL = formatearFecha(fecha ? new Date(fecha) : new Date());
        await this.laConexion.execute(
            `INSERT INTO Medicion (mac, uuid, valor, fecha, sensorId, rssi)
             VALUES (?, ?, ?, ?, ?, ?)`,
            [mac, uuid, valor, fechaSQL, sensorId, rssi]
        );
    }

    // .....................................................
    // Devuelve todas las mediciones (ordenadas por fecha)
    // .....................................................
    async buscarMediciones() {
        const [rows] = await this.laConexion.execute(
            "SELECT * FROM Medicion ORDER BY fecha DESC"
        );
        return rows;
    }

    // .....................................................
    // Cierra la conexión con la BD
    // .....................................................
    async cerrar() {
        await this.laConexion.end();
    }
};
