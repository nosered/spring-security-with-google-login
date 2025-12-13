package br.dev.edersonabreu.poc.config;

import java.util.List;

import javax.sql.DataSource;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import br.dev.edersonabreu.poc.repository.UsuarioRepository;

@Configuration
public class JdbiConfig {
	
	@Bean
	public SqlObjectPlugin sqlObjectPlugin() {
		return new SqlObjectPlugin();
	}
	
	@Bean
	public Jdbi jdbi(DataSource dataSource, List<JdbiPlugin> jdbiPlugins, List<RowMapper<?>> rowMappers) {
		TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(dataSource);        
		Jdbi jdbi = Jdbi.create(proxy);
		jdbiPlugins.forEach(jdbi::installPlugin);
		rowMappers.forEach(jdbi::registerRowMapper);
		
		return jdbi;
    }
	
	@Bean
	public UsuarioRepository usuarioRepository(Jdbi jdbi) {
		return jdbi.onDemand(UsuarioRepository.class);
	}
}