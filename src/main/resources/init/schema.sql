CREATE TABLE usuario(
	id_usuario SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	email VARCHAR(100) NOT NULL,
	senha VARCHAR(255),
	ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE permissao(
	id_permissao SERIAL PRIMARY KEY,
	descricao VARCHAR(50)
);

CREATE TABLE permissoes_usuario(
	id SERIAL PRIMARY KEY,
	id_usuario INTEGER NOT NULL,
	id_permissao INTEGER NOT NULL,
	CONSTRAINT uq_usuario_permissao UNIQUE(id_usuario, id_permissao),
	CONSTRAINT fk_usuario FOREIGN KEY(id_usuario) REFERENCES usuario(id_usuario),
	CONSTRAINT fk_permissao FOREIGN KEY(id_permissao) REFERENCES permissao(id_permissao)
);