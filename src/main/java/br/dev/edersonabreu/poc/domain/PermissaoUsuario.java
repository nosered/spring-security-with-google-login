package br.dev.edersonabreu.poc.domain;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PermissaoUsuario {
	
	@ColumnName(value = "id")
	private Integer id;
	
	@ColumnName(value = "id_usuario")
	private Integer idUsuario;
	
	@ColumnName(value = "id_permissao")
	private Integer idPermissao;
}