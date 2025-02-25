INSERT INTO tb_category(id, name) VALUES (UUID(), 'Frutas');
INSERT INTO tb_category(id,name) VALUES (UUID(), 'Doces');
INSERT INTO tb_category(id, name) VALUES (UUID(), 'Carnes');

INSERT INTO tb_product(id, name, price, description, img_url) VALUES (UUID(), 'Maçã verde', 6.0, 'Doce e fresca', 'www.img.com.br');
INSERT INTO tb_product(id, name, price, description, img_url) VALUES (UUID(), 'Bolo de chocolate', 20.0, 'Caseiro', 'www.img.com.br');
INSERT INTO tb_product(id, name, price, description, img_url) VALUES (UUID(), 'Picanha', 60.4, 'De primeira qualidade', 'www.img.com.br');
INSERT INTO tb_product(id, name, price, description, img_url) VALUES (UUID(), 'Filé mignon', 40.90, 'De primeira qualidade', 'www.img.com.br');

SET @category1_id(SELECT id FROM tb_category WHERE name = 'Frutas');
SET @category2_id(SELECT id FROM tb_category WHERE name = 'Doces');
SET @category3_id(SELECT id FROM tb_category WHERE name = 'Carnes');

SET @product1_id(SELECT id FROM tb_product WHERE name = 'Maçã verde');
SET @product2_id(SELECT id FROM tb_product WHERE name = 'Bolo de chocolate');
SET @product3_id(SELECT id FROM tb_product WHERE name = 'Picanha');
SET @product4_id(SELECT id FROM tb_product WHERE name = 'Filé mignon');

INSERT INTO tb_product_category(product_id, category_id) VALUES (@product1_id, @category1_id);
INSERT INTO tb_product_category(product_id, category_id) VALUES (@product2_id, @category2_id);
INSERT INTO tb_product_category(product_id, category_id) VALUES (@product3_id, @category3_id);
INSERT INTO tb_product_category(product_id, category_id) VALUES (@product4_id, @category3_id);

INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Admin');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Manager');
INSERT INTO tb_role(id, authority) VALUES (UUID(), 'Seller');

INSERT INTO tb_user(id, first_name, last_name, email, password) VALUES (UUID(), 'Vanessa', 'Aparecida', 'vanessa@gmail.com', '1234567');
INSERT INTO tb_user(id, first_name, last_name, email, password) VALUES (UUID(), 'Maria', 'Silva', 'maria@gmail.com', '1234567');
INSERT INTO tb_user(id, first_name, last_name, email, password) VALUES (UUID(), 'Gustavo', 'Barros', 'gustavo@gmail.com', '1234567');
INSERT INTO tb_user(id, first_name, last_name, email, password) VALUES (UUID(), 'Pedro', 'Barros', 'pedro@gmail.com', '1234567');

SET @role1_id(SELECT id FROM tb_role WHERE authority = 'Admin');
SET @role2_id(SELECT id FROM tb_role WHERE authority = 'Manager');
SET @role3_id(SELECT id FROM tb_role WHERE authority = 'Seller');

SET @user1_id(SELECT id FROM tb_user WHERE first_name = 'Vanessa');
SET @user2_id(SELECT id FROM tb_user WHERE first_name = 'Maria');
SET @user3_id(SELECT id FROM tb_user WHERE first_name = 'Gustavo');
SET @user4_id(SELECT id FROM tb_user WHERE first_name = 'Pedro');

INSERT INTO tb_user_role(user_id, role_id) VALUES (@user1_id, @role1_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user2_id, @role2_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user3_id, @role3_id);
INSERT INTO tb_user_role(user_id, role_id) VALUES (@user4_id, @role3_id);


