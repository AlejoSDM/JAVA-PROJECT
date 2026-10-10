DELIMITER //

CREATE PROCEDURE sp_reporte_celulares()
BEGIN
    SELECT c.id, m.nombre AS marca, c.modelo, c.stock
    FROM celular c
    INNER JOIN marca m ON m.id = c.marca_id;
END //

CREATE PROCEDURE sp_reporte_detalles()
BEGIN
    SELECT dv.id_venta, dv.id_celular, m.nombre AS marca,
           c.modelo, dv.cantidad
    FROM detalle_ventas dv
    INNER JOIN celular c ON c.id = dv.id_celular
    INNER JOIN marca m ON m.id = c.marca_id;
END //

CREATE PROCEDURE sp_reporte_ventas()
BEGIN
    SELECT v.id, v.fecha, v.total,
           c.nombre AS cliente,
           e.nombre AS empleado
    FROM ventas v
    INNER JOIN cliente c ON c.id = v.id_cliente
    INNER JOIN empleado e ON e.id = v.id_empleado
    ORDER BY v.fecha, v.id;
END //

DELIMITER ;