package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author thiago.monteiro
 */
public interface OrigemOcorrenciaService extends CrudServiceEjb2 {
	/**
	 * Exclui a origem caso n�o exista uma ocorr�ncia associada.
	 * 
	 * @param model
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 */	
	public void deletarOrigemOcorrencia(IDto model, DocumentHTML document) throws ServiceException, Exception;	
	
	/**
	 * Consulta por origem da ocorr�ncia que estejam ativas (dataFim n�o nula).
	 * 
	 * @param model
	 * @param document
	 * @return
	 * @throws Exception 
	 */
	public boolean consultarOrigemOcorrenciaAtiva(OrigemOcorrenciaDTO origemOcorrencia) throws Exception;
	
}
