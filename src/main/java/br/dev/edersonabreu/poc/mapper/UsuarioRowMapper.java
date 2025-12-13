package br.dev.edersonabreu.poc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.dev.edersonabreu.poc.domain.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return Usuario.builder()
				.id(resultSet.getInt("id_usuario"))
				.nome(resultSet.getString("nome"))
				.email(resultSet.getString("email"))
				.senha(resultSet.getString("senha"))
				.ativo(resultSet.getBoolean("ativo"))
				.build();
	}
}