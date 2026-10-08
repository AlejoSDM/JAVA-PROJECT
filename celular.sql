USE TecStoreAlejo;

DELIMITER $$
/*SE CREA EL CELULAR*/
CREATE PROCEDURE sp_celular_insertar(
    IN p_id VARCHAR(20),
    IN p_marca_id VARCHAR(20),
    IN p_modelo VARCHAR(100),
    IN p_sistema_operativo VARCHAR(20),
    IN p_gama VARCHAR(20),
    IN p_precio DOUBLE,
    IN p_stock INT,
    IN p_stock_minimo INT
)
BEGIN
    INSERT INTO celular (
        id, marca_id, modelo, sistema_operativo, gama,
        precio, stock, stock_minimo
    )
    VALUES (
        p_id, p_marca_id, p_modelo, p_sistema_operativo, p_gama,
        p_precio, p_stock, p_stock_minimo
    );
END$$
/*SE LISTAN LOS CELULARES Y SU MARCA*/
CREATE PROCEDURE sp_celular_listar()
BEGIN
    SELECT c.id,
           c.marca_id,
           m.nombre AS marca_nombre,
           c.modelo,
           c.sistema_operativo,
           c.gama,
           c.precio,
           c.stock,
           c.stock_minimo
    FROM celular c
    INNER JOIN marca m ON c.marca_id = m.id;
END$$
/*SE ACTUALIZA EL CELULAR*/
CREATE PROCEDURE sp_celular_actualizar(
    IN p_id VARCHAR(20),
    IN p_marca_id VARCHAR(20),
    IN p_modelo VARCHAR(100),
    IN p_sistema_operativo VARCHAR(20),
    IN p_gama VARCHAR(20),
    IN p_precio DOUBLE,
    IN p_stock INT,
    IN p_stock_minimo INT
)
BEGIN
    UPDATE celular
    SET marca_id = p_marca_id,
        modelo = p_modelo,
        sistema_operativo = p_sistema_operativo,
        gama = p_gama,
        precio = p_precio,
        stock = p_stock,
        stock_minimo = p_stock_minimo
    WHERE id = p_id;
END$$
/*ES ELIMINA EL CELULAR*/
CREATE PROCEDURE sp_celular_eliminar(
    IN p_id VARCHAR(20)
)
BEGIN
    DELETE FROM celular
    WHERE id = p_id;
END$$

DELIMITER ;