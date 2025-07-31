CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre VARCHAR(100)
);

CREATE TABLE vehiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(15) UNIQUE NOT NULL,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE estancias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehiculo_id INT,
    hora_entrada DATETIME NOT NULL,
    hora_salida DATETIME,
    horas_pagadas INT NOT NULL,
    hora_limite_salida DATETIME AS (ADDDATE(hora_entrada, INTERVAL horas_pagadas HOUR)) STORED,
    FOREIGN KEY (vehiculo_id) REFERENCES vehiculos(id)
);