package br.dev.edersonabreu.poc.mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.jdbi.v3.core.result.RowReducer;
import org.jdbi.v3.core.result.RowView;

import br.dev.edersonabreu.poc.domain.Permissao;
import br.dev.edersonabreu.poc.domain.Usuario;

public class PermissaoUsuarioRowReducer implements RowReducer<Map<Integer, Usuario>, Usuario> {

	@Override
	public Map<Integer, Usuario> container() {
		return new HashMap<Integer, Usuario>();
	}

	@Override
	public void accumulate(Map<Integer, Usuario> container, RowView rowView) {
		int idUsuario = rowView.getColumn("id_usuario", Integer.class);
		Usuario usuario = container.computeIfAbsent(idUsuario, id -> rowView.getRow(Usuario.class));
		
		Permissao permissao = rowView.getRow(Permissao.class);
		if(permissao.getId() != 0) {
			usuario.getPermissoes().add(permissao);
		}
	}

	@Override
	public Stream<Usuario> stream(Map<Integer, Usuario> container) {
		return container.values().stream();
	}	
}