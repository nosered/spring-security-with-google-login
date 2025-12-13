INSERT INTO usuario(nome, email, senha)
VALUES ('Administrador do Sistema', 'adm@mail.com', '$2a$10$F4S5IOodvG8icmKcxzDyoOEbBBu7ZSP1kwvrAuOpYly3xbQ2Y3Ji.');

INSERT INTO permissao(descricao) VALUES ('ROLE_ADMIN');
INSERT INTO permissao(descricao) VALUES ('ROLE_USER');

INSERT INTO permissoes_usuario(id_usuario, id_permissao) VALUES (1, 1);
INSERT INTO permissoes_usuario(id_usuario, id_permissao) VALUES (1, 2);