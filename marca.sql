USE TecStoreAlejo;

DELIMITER $$
/*SE INSERTA LA MARCA*/
CREATE PROCEDURE sp_marca_insertar(
    IN p_id VARCHAR(20),
    IN p_nombre VARCHAR(100)
)
BEGIN
    INSERT INTO marca (id, nombre)
    VALUES (p_id, p_nombre);
END$$
/*SE LISTA LA MARCA*/
CREATE PROCEDURE sp_marca_listar()
BEGIN
    SELECT id, nombre
    FROM marca;
END$$
/*SE ACTUALIZA SEGUN EL ID*/
CREATE PROCEDURE sp_marca_actualizar(
    IN p_id VARCHAR(20),
    IN p_nombre VARCHAR(100)
)
BEGIN
    UPDATE marca
    SET nombre = p_nombre
    WHERE id = p_id;
END$$
/*SE CREA EL PROCEDIMIENTO DE ELIMINAR SEGUN SU ID*/
CREATE PROCEDURE sp_marca_eliminar(
    IN p_id VARCHAR(20)
)
BEGIN
    DELETE FROM marca
    WHERE id = p_id;
END$$

DELIMITER ;