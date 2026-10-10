USE TecStoreAlejo;

DELIMITER //

-- Consulta el precio y stock de un celular.
CREATE PROCEDURE sp_venta_obtener_celular(
    IN p_id_celular INT
)
BEGIN
    SELECT id, precio, stock
    FROM celular
    WHERE id = p_id_celular;
END //

CREATE PROCEDURE sp_venta_insertar(
    IN p_total DOUBLE,
    IN p_id_empleado INT,
    IN p_id_cliente INT,
    OUT p_id_venta INT
)
BEGIN
    INSERT INTO ventas (total, id_empleado, id_cliente)
    VALUES (p_total, p_id_empleado, p_id_cliente);

    SET p_id_venta = LAST_INSERT_ID();
END //

-- Inserta un celular en el detalle de la venta.
-- El trigger descontará el stock automáticamente.
CREATE PROCEDURE sp_venta_detalle_insertar(
    IN p_id_venta INT,
    IN p_id_celular INT,
    IN p_cantidad INT,
    IN p_precio_unitario DOUBLE
)
BEGIN
    INSERT INTO detalle_ventas
        (id_venta, id_celular, cantidad, precio_unitario)
    VALUES
        (p_id_venta, p_id_celular, p_cantidad, p_precio_unitario);
END //

-- Calcula el total de los detalles más el 19 % de IVA.
-- Se llama después de insertar todos los detalles.
CREATE PROCEDURE sp_venta_calcular_total(
    IN p_id_venta INT
)
BEGIN
    UPDATE ventas
    SET total = (
        SELECT ROUND(COALESCE(SUM(subtotal), 0) * 1.19, 2)
        FROM detalle_ventas
        WHERE id_venta = p_id_venta
    )
    WHERE id = p_id_venta;
END //

-- Descuenta el stock al insertar cada detalle.
CREATE TRIGGER descontar_stock_al_insertar_detalle
BEFORE INSERT ON detalle_ventas
FOR EACH ROW
BEGIN
    DECLARE filas_actualizadas INT DEFAULT 0;

    IF NEW.cantidad <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La cantidad debe ser mayor que cero';
    END IF;

    UPDATE celular
    SET stock = stock - NEW.cantidad
    WHERE id = NEW.id_celular
      AND stock >= NEW.cantidad;

    SET filas_actualizadas = ROW_COUNT();

    IF filas_actualizadas = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'No hay stock suficiente o el celular no existe';
    END IF;
END //

DELIMITER ;