USE TecStoreAlejo;

DELIMITER $$
/*SE INSERTA LA MARCA*/
CREATE PROCEDURE sp_marca_insertar(
    IN p_nombre VARCHAR(100)
)
BEGIN
    INSERT INTO marca (nombre)
    VALUES (p_nombre);
END$$
/*SE LISTA LA MARCA*/
CREATE PROCEDURE sp_marca_listar()
BEGIN
    SELECT id, nombre
    FROM marca;
END$$
/*SE ACTUALIZA SEGUN EL ID*/
CREATE PROCEDURE sp_marca_actualizar(
    IN p_id INT,
    IN p_nombre VARCHAR(100)
)
BEGIN
    UPDATE marca
    SET nombre = p_nombre
    WHERE id = p_id;
END$$
/*SE CREA EL PROCEDIMIENTO DE ELIMINAR SEGUN SU ID*/
CREATE PROCEDURE sp_marca_eliminar(
    IN p_id INT
)
BEGIN
    DELETE FROM marca
    WHERE id = p_id;
END$$
    /*la persona escribe el nombre de la marca; el sistema busca si ya existe; si no existe, la crea; después 
    registra el celular con el ID de esa marca
 Así no se crea una marca duplicada cada vez que registras un celular de la misma marca.*/
CREATE PROCEDURE sp_marca_buscar_por_nombre(IN p_nombre VARCHAR(100))
BEGIN
    SELECT id, nombre
    FROM marca
    WHERE LOWER(TRIM(nombre)) = LOWER(TRIM(p_nombre))
    LIMIT 1;
END$$

DELIMITER ;