/*ACA SE GUARDA EL CRUD DEL CLIENTE EN PROCEDIMIENTO PARA DESPUES MANEJARLO EN JAVA*/

USE TecStoreAlejo;

DELIMITER $$
/*SE CREAN VARIABLES QUE DESPUES TOMARAN VALOR GRACIAS A JAVA DONDE SE CREARA EL CLIENTE*/
CREATE PROCEDURE sp_cliente_insertar(
    IN p_nombre VARCHAR(100),
    IN p_cedula VARCHAR(20),
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(30)
)
BEGIN
    /*ASI MISMO SE GUARDARAN EN LAS COLUMNAS QUE CORRESPONDEN*/
    INSERT INTO cliente (nombre, cedula, correo, telefono)
    VALUES (p_nombre, p_cedula, p_correo, p_telefono);
END$$
/*ACA SE LISTA LOS CLIENTES QUE EXISTEN*/
CREATE PROCEDURE sp_cliente_listar()
BEGIN
    SELECT id, nombre, cedula, correo, telefono
    FROM cliente;
END$$
/*SE ACTUALIZA CLIENTE SEGUN EL ID SELECCIONADO*/
CREATE PROCEDURE sp_cliente_actualizar(
    IN p_id INT,
    IN p_nombre VARCHAR(100),
    IN p_cedula VARCHAR(20),
    IN p_correo VARCHAR(150),
    IN p_telefono VARCHAR(30)
)
BEGIN
    UPDATE cliente
    SET nombre = p_nombre,
        cedula = p_cedula,
        correo = p_correo,
        telefono = p_telefono
    WHERE id = p_id;
END$$
/*SE ELIMINA EL CLIENTE SEGUN EL ID SELECCIONADO*/
CREATE PROCEDURE sp_cliente_eliminar(
    IN p_id INT
)
BEGIN
    DELETE FROM cliente
    WHERE id = p_id;
END$$

DELIMITER ;