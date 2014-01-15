package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.HashMap;

import br.com.centralit.citcorpore.metainfo.bean.TableSearchDTO;
import br.com.citframework.service.CrudServicePojo;

/**
 * Service respons�vel pelas consultas das telas que s�o DinamicViews.
 * 
 */
@SuppressWarnings("rawtypes")
public interface TableSearchService extends CrudServicePojo {

	/**
	 * Realiza consulta nas Telas que s�o DinamicViews.
	 * 
	 * @param parm
	 * @param tableVinc
	 * @param map
	 * @return String com os resultados da consulta.
	 * @throws Exception
	 */
	public String findItens(TableSearchDTO parm, boolean tableVinc, HashMap map) throws Exception;

	public String getInfoMatriz(TableSearchDTO parm, boolean tableVinc, HashMap map) throws Exception;
}
