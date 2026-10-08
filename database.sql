/*el InnoDB es el motor de almacenamiento de MySQL. Se encarga de guardar y administrar las tablas. 
En tu script se usa porque permite, entre otras cosas, crear claves foráneas y
 manejar transacciones. Para que las relaciones entre tablas funcionen*/

CREATE DATABASE IF NOT EXISTS TecStoreAlejo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_spanish_ci;

USE TecStoreAlejo;

CREATE TABLE IF NOT EXISTS marca (
    id VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS celular (
    id VARCHAR(20) NOT NULL,
    marca_id VARCHAR(20) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    sistema_operativo ENUM('IOS', 'ANDROID', 'KAIOS') NOT NULL,
    gama ENUM('ALTA', 'MEDIA', 'BAJA') NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    stock_minimo INT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_celular_marca
        FOREIGN KEY (marca_id) REFERENCES marca(id),

    CONSTRAINT chk_celular_precio
        CHECK (precio >= 0),

    CONSTRAINT chk_celular_stock
        CHECK (stock >= 0 AND stock_minimo >= 0)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS cliente (
    id VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    cedula VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(30) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (cedula)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS empleado (
    id VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(30) NOT NULL,

    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DOUBLE NOT NULL,
    id_empleado VARCHAR(20) NULL,
    id_cliente VARCHAR(20) NOT NULL,

    CONSTRAINT fk_venta_empleado
        FOREIGN KEY (id_empleado) REFERENCES empleado(id),

    CONSTRAINT fk_venta_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id),

    CONSTRAINT chk_venta_total
        CHECK (total >= 0)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS detalle_ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_celular VARCHAR(20) NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    subtotal DOUBLE NOT NULL,

    CONSTRAINT fk_detalle_venta
        FOREIGN KEY (id_venta) REFERENCES ventas(id),

    CONSTRAINT fk_detalle_celular
        FOREIGN KEY (id_celular) REFERENCES celular(id),

    CONSTRAINT chk_detalle_cantidad
        CHECK (cantidad > 0),

    CONSTRAINT chk_detalle_precios
        CHECK (precio_unitario >= 0 AND subtotal >= 0)
) ENGINE = InnoDB;