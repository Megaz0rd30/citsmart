package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author Fl�vio.santana
 *
 */
public interface PostService extends CrudServiceEjb2 {
	
	public Collection listNotNull() throws Exception;
}
