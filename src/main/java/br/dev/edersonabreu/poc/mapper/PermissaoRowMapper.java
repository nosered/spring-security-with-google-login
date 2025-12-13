package br.dev.edersonabreu.poc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import br.dev.edersonabreu.poc.domain.Permissao;

public class PermissaoRowMapper implements RowMapper<Permissao> {

	@Override
	public Permissao map(ResultSet resultSet, StatementContext statementContext) throws SQLException {
		return Permissao.builder()
				.id(resultSet.getInt("id_permissao"))
				.descricao(resultSet.getString("descricao"))
				.build();
	}
}