USE TecStoreAlejo;

DELIMITER $$
/*SE INSERTA LOS DATOS A EMPLEADO*/
CREATE PROCEDURE sp_empleado_insertar(
    IN p_id VARCHAR(20),
    IN p_nombre VARCHAR(100),
    IN p_telefono VARCHAR(30)
)
BEGIN
    INSERT INTO empleado (id, nombre, telefono)
    VALUES (p_id, p_nombre, p_telefono);
END$$

/*SE LISTAN LOS EMPLEADOS*/
CREATE PROCEDURE sp_empleado_listar()
BEGIN
    SELECT id, nombre, telefono
    FROM empleado;
END$$

/*SE ACTUALIZAN LOS DATO DEL EMPLEADOS SEGUN SU ID*/
CREATE PROCEDURE sp_empleado_actualizar(
    IN p_id VARCHAR(20),
    IN p_nombre VARCHAR(100),
    IN p_telefono VARCHAR(30)
)
BEGIN
    UPDATE empleado
    SET nombre = p_nombre,
        telefono = p_telefono
    WHERE id = p_id;
END$$
/*SE ELIMINA EL EMPLEADO DE LA BASE DE DATOS*/
DROP PROCEDURE IF EXISTS sp_empleado_eliminar$$
CREATE PROCEDURE sp_empleado_eliminar(
    IN p_id VARCHAR(20)
)
BEGIN
    DELETE FROM empleado
    WHERE id = p_id;
END$$

DELIMITER ;