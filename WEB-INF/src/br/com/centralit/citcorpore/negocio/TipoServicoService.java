package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.TipoServicoDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author leandro.viana
 * 
 */
public interface TipoServicoService extends CrudServiceEjb2 {

	/**
	 * Verifica se tipo servi�o.
	 * 
	 * @param tipoServicoDTO
	 * @return true - existe; false - n�o existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeTipoServicoExiste(TipoServicoDTO tipoServicoDto) throws PersistenceException, br.com.citframework.excecao.ServiceException;

}
