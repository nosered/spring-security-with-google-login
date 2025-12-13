package br.dev.edersonabreu.poc.repository;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import br.dev.edersonabreu.poc.domain.PermissaoUsuario;
import br.dev.edersonabreu.poc.domain.Usuario;
import br.dev.edersonabreu.poc.mapper.PermissaoRowMapper;
import br.dev.edersonabreu.poc.mapper.PermissaoUsuarioRowReducer;
import br.dev.edersonabreu.poc.mapper.UsuarioRowMapper;

@UseClasspathSqlLocator
public interface UsuarioRepository {
	
	@SqlQuery
	@RegisterRowMapper(value = UsuarioRowMapper.class)
	@RegisterRowMapper(value = PermissaoRowMapper.class)
	@UseRowReducer(value = PermissaoUsuarioRowReducer.class)
	public Usuario consultar(String email);
	
	@SqlUpdate
	@GetGeneratedKeys
	public Integer salvar(@BindBean Usuario usuario);
	
	@SqlBatch
	public void salvarPermissoes(@BindBean List<PermissaoUsuario> permissaoUsuario);
	
	@SqlUpdate
	public void alterarSenha(@BindBean Usuario usuario);
}