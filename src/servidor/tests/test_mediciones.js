// tests/test_mediciones.js
const chai = require("chai");
const expect = chai.expect;
const request = require("supertest");
const express = require("express");
const crearReglasREST = require("../servidorREST/ReglasREST.js");
const Logica = require("../Logica/Logica.js");

describe("Tests de API REST / Base de Datos", function () {
  let app;
  let logica;

  before(async function () {
    this.timeout(5000);

    // Crear conexión a la BD de pruebas (ajusta si usas otra)
    logica = await new Promise((resolver, rechazar) => {
      const l = new Logica(
        {
          host: "localhost",
          user: "root",
          password: "",
          database: "beacons",
        },
        (err) => (err ? rechazar(err) : resolver(l))
      );
    });

    // Montar servidor Express en memoria
    app = express();
    app.use(express.json());
    app.use("/api", crearReglasREST(logica));
  });

  after(async function () {
    await logica.cerrar();
  });

  // .................................................
  it("debería insertar una medición correctamente", async function () {
    const respuesta = await request(app)
      .post("/api/guardarMediciones")
      .send({
        mac: "AA:BB:CC:DD:EE:FF",
        uuid: "UUID-TEST",
        valor: 123,
        sensorId: 11,
        rssi: -45,
      });

    expect(respuesta.status).to.equal(201);
    expect(respuesta.body.ok).to.be.true;
  });

  // .................................................
  it("debería devolver una lista de mediciones", async function () {
    const respuesta = await request(app).get("/api/recuperarMediciones");
    expect(respuesta.status).to.equal(200);
    expect(respuesta.body.ok).to.be.true;
    expect(respuesta.body.datos).to.be.an("array");
  });
});
