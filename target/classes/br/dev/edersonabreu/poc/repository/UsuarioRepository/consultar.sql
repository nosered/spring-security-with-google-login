SELECT
	u.id_usuario,
	u.nome,
	u.email,
	u.senha,
	u.ativo,
	p.id_permissao,
	p.descricao
FROM usuario u
JOIN permissoes_usuario pu
ON u.id_usuario = pu.id_usuario
JOIN permissao p
ON p.id_permissao = pu.id_permissao
WHERE u.email = :email;