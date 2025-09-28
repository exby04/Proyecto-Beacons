// logica/Logica.js
const mysql = require("mysql2/promise");

function formatearFecha(fecha = new Date()) {
    // Devuelve fecha en formato MySQL DATETIME
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
    // Inserta dispositivo (solo si no existe)
    // .....................................................
    async insertarDispositivo(mac, nombre) {
        await this.laConexion.execute(
            "INSERT IGNORE INTO Dispositivo (mac, nombre) VALUES (?, ?)",
            [mac, nombre]
        );
    }

    // .....................................................
    // Inserta medición asociada a un dispositivo
    // .....................................................
    async insertarMedicion({ mac, valor, fecha }) {
        const fechaSQL = formatearFecha(fecha ? new Date(fecha) : new Date());
        await this.laConexion.execute(
            "INSERT INTO Medicion (mac, valor, fecha) VALUES (?, ?, ?)",
            [mac, valor, fechaSQL]
        );
    }

    // .....................................................
    // Devuelve todas las mediciones
    // .....................................................
    async buscarMediciones() {
        const [rows] = await this.laConexion.execute(
            "SELECT * FROM Medicion ORDER BY fecha DESC"
        );
        return rows;
    }

    // .....................................................
    // Devuelve mediciones de un dispositivo
    // .....................................................
    async buscarMedicionesPorMac(mac) {
        const [rows] = await this.laConexion.execute(
            "SELECT * FROM Medicion WHERE mac = ? ORDER BY fecha DESC",
            [mac]
        );
        return rows;
    }

    // .....................................................
    // Devuelve todos los dispositivos
    // .....................................................
    async buscarDispositivos() {
        const [rows] = await this.laConexion.execute(
            "SELECT * FROM Dispositivo"
        );
        return rows;
    }

    // .....................................................
    // Cierra la conexión
    // .....................................................
    async cerrar() {
        await this.laConexion.end();
    }
};
