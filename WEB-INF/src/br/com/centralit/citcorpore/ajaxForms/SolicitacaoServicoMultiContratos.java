package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CategoriaServicoService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ConhecimentoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmailSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EventoMonitConhecimentoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ImportanciaConhecimentoGrupoService;
import br.com.centralit.citcorpore.negocio.ItemCfgSolicitacaoServService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeSolicitacoesService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoEvtMonService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class SolicitacaoServicoMultiContratos extends AjaxFormAction {

	private String calcularDinamicamente;

	ContratoDTO contratoDtoAux = new ContratoDTO();

	private PrioridadeSolicitacoesService prioridadeSolicitacoesService;

	private Boolean acao = false;

	private ProblemaService problemaService;

	private ConhecimentoSolicitacaoService conhecimentoSolicitacaoService;

	private RequisicaoMudancaService requisicaoMudancaService;

	public boolean validaParametrosUpload() {
		String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
		if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equals("")) {
			return false;
		}
		File pastaGed = new File(PRONTUARIO_GED_DIRETORIO);
		if (!pastaGed.exists()) {
			return false;
		}
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if (DISKFILEUPLOAD_REPOSITORYPATH == null || DISKFILEUPLOAD_REPOSITORYPATH.trim().equals("")) {
			return false;
		}
		File pastaUpload = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if (!pastaUpload.exists()) {
			return false;
		}
		return true;
	}

	public void chamaComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.preencherComboOrigem(document, request, response);
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("parametrosUploadValidos", validaParametrosUpload());

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.executeScript("habilitaBotaoGravar()");

		/**
		 * Adicionado para fazer limpeza do upload que est� na sess�o .
		 * 
		 * @author maycon.fernandes
		 * @since 28/10/2013 08:21
		 */
		request.getSession(true).setAttribute("colUploadsGED2", null);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		// COMBO IMPACTO E COMBO URG�NCIA
		this.carregarCombosImpactoUrgente(document, request);

		Collection<GrupoDTO> listGrupoDoEmpregadoLogado = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

		if (listGrupoDoEmpregadoLogado != null) {
			boolean isAbertura = false;
			boolean isEncerramento = false;
			boolean isAndamento = false;
			for (GrupoDTO grupoDto : listGrupoDoEmpregadoLogado) {

				if (isAbertura && isEncerramento && isAndamento) {
					break;
				} else {
					if (grupoDto.getAbertura() != null && grupoDto.getAbertura().trim().equals("S") && !isAbertura) {
						document.getElementById("enviaEmailCriacao").setDisabled(true);
						document.executeScript("$('#uniform-enviaEmailCriacao').addClass('disabled')");
						isAbertura = true;
					}
					if (grupoDto.getEncerramento() != null && grupoDto.getEncerramento().trim().equals("S") && !isEncerramento) {
						document.getElementById("enviaEmailFinalizacao").setDisabled(true);
						document.executeScript("$('#uniform-enviaEmailFinalizacao').addClass('disabled')");
						isEncerramento = true;
					}
					if (grupoDto.getAndamento() != null && grupoDto.getAndamento().trim().equals("S") && isAndamento) {
						document.getElementById("enviaEmailAcoes").setDisabled(true);
						document.executeScript("$('#uniform-enviaEmailAcoes').addClass('disabled')");
						isAndamento = true;
					}
				}
			}
		}

		request.getSession(true).setAttribute("dados_solicit_quest", null);

		// COMBO ORIGEM
		this.preencherComboOrigem(document, request, response);

		// COMBO TIPO DEMANDA
		this.carregarComboTipoDemanda(document, request);

		// COMBO GRUPO ATUAL
		this.carregarComboGrupoAtual(document, request);

		document.executeScript("document.getElementById(\"divCategoriaServico\").style.display = 'none'");

		// COMBO CAUSA
		this.carregarComboCausa(document, request);

		// COMBO CATEGORIA SOLU��O
		this.carregarComboCategoriaSolucao(document, request);

		// COMBO UNIDADE
		this.carregaUnidade(document, request, response);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {

			this.restore(document, request, response);

			solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		} else {
			document.getElementById("quantidadeAnexos").setValue("0");
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("");
			document.executeScript("$('#liNovasolicitacao').addClass('inativo')");
		}
		/*
		 * Desenvolvedor: Thiago Matias - Data: 08/11/2013 - Hor�rio: 09:30 - ID Citsmart: 123357 - Motivo/Coment�rio: Verificando a variavel editar da URL est� setada com N, pois se estiver � para
		 * setar no objeto e sdesabilitar os campos abaixo
		 */
		if (solicitacaoServicoDto != null && request.getParameter("editar") != null && request.getParameter("editar").equalsIgnoreCase("N")) {
			solicitacaoServicoDto.setEditar("N");
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
			document.getElementById("idOrigem").setDisabled(true);
			document.getElementById("solicitante").setDisabled(true);
			document.getElementById("telefonecontato").setDisabled(true);
			document.getElementById("ramal").setDisabled(true);
			document.getElementById("idUnidade").setDisabled(true);
			document.getElementById("idLocalidade").setDisabled(true);
			document.getElementById("emailcontato").setDisabled(true);
			document.getElementById("descricao").setDisabled(true);
			document.getElementById("idCausaIncidente").setDisabled(true);
			document.getElementById("idCategoriaSolucao").setDisabled(true);
			document.getElementById("solucaoTemporaria").setDisabled(true);
			document.executeScript("document.getElementById('divBotoes').style.display = 'none';");
			document.executeScript("bloqueiaBotoesVisualizacao()");
			document.executeScript("$('#uniform-gravaSolucaoRespostaBaseConhecimento').addClass('disabled')");
			document.getElementById("gravaSolucaoRespostaBaseConhecimento").setDisabled(true);
			document.executeScript("desabilitaSituacao()");
			document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");
			document.getElementById("enviaEmailCriacao").setDisabled(true);
			document.executeScript("$('#uniform-enviaEmailCriacao').addClass('disabled')");
			document.getElementById("enviaEmailFinalizacao").setDisabled(true);
			document.executeScript("$('#uniform-enviaEmailFinalizacao').addClass('disabled')");
			document.getElementById("enviaEmailAcoes").setDisabled(true);
			document.executeScript("$('#uniform-enviaEmailAcoes').addClass('disabled')");
			document.executeScript("$('#addProblema').attr('disabled', 'disabled')");

		} else if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("S")) {
			document.executeScript("$('#liNovasolicitacao').removeClass('inativo')");
			document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");
		}

		// COMBO CONTRATO
		this.carregarComboContrato(document, usuario, solicitacaoServicoDto);

		Integer idSolicitacaoRelacionada = null;
		if (request.getParameter("idSolicitacaoRelacionada") != null && !request.getParameter("idSolicitacaoRelacionada").equalsIgnoreCase("")) {
			idSolicitacaoRelacionada = Integer.parseInt(request.getParameter("idSolicitacaoRelacionada"));

			Integer idContrato = null;

			SolicitacaoServicoDTO solicitacaoServico = new SolicitacaoServicoDTO();
			SolicitacaoServicoDTO solicitacaoServicoInformacoesContato = new SolicitacaoServicoDTO();

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

			if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
				idContrato = Integer.parseInt(request.getParameter("idContrato"));

				if (idContrato != null) {
					solicitacaoServico.setIdContrato(idContrato);
				}
			}

			solicitacaoServico.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);

			this.verificaGrupoExecutor(document, request, response);
			this.verificaImpactoUrgencia(document, request, response);

			if (idContrato != null) {
				document.executeScript("adicionarIdContratoNaLookup(" + idContrato + ")");
			}

			solicitacaoServico = (SolicitacaoServicoDTO) solicitacaoServicoService.restoreAll(idSolicitacaoRelacionada);

			if (solicitacaoServico != null) {
				solicitacaoServicoInformacoesContato.setIdSolicitante(solicitacaoServico.getIdSolicitante());
				solicitacaoServicoInformacoesContato.setSolicitante(solicitacaoServico.getSolicitante());
				solicitacaoServicoInformacoesContato.setNomecontato(solicitacaoServico.getNomecontato());
				solicitacaoServicoInformacoesContato.setTelefonecontato(solicitacaoServico.getTelefonecontato());
				solicitacaoServicoInformacoesContato.setEmailcontato(solicitacaoServico.getEmailcontato());
				solicitacaoServicoInformacoesContato.setRamal(solicitacaoServico.getRamal());
				solicitacaoServicoInformacoesContato.setObservacao(solicitacaoServico.getObservacao());
				solicitacaoServicoInformacoesContato.setIdUnidade(solicitacaoServico.getIdUnidade());
				solicitacaoServicoInformacoesContato.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);

				this.preencherComboLocalidade(document, request, response);
			}

			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() == null && solicitacaoServicoDto.getIdSolicitacaoRelacionada() != null) {
				((SolicitacaoServicoDTO) document.getBean()).setIdSolicitante(solicitacaoServico.getIdSolicitante());
				this.renderizaHistoricoSolicitacoesEmAndamentoUsuario(document, request, response);
			}

			document.getForm("form").setValues(solicitacaoServicoInformacoesContato);
		}

		String acaoFluxo = request.getParameter("acaoFluxo");
		if (acaoFluxo != null && acaoFluxo.equalsIgnoreCase("E")) {
			document.executeScript("mostrarPassoQuatroExecucaoTarefa()");
		}

		String tarefaAssociada = "N";
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdTarefa() != null) {
			tarefaAssociada = "S";
		}

		/**
		 * RECLASSIFICAR SOLICITACAO Visualiza o 3 passo do cadastro de solicita��o de servi�o, mostrando apenas campos especificos para a reclassifica��o da solicita��o
		 **/
		String visualizarPasso = request.getParameter("visualizarPasso");
		visualizarPasso = UtilStrings.nullToVazio(visualizarPasso);
		if (visualizarPasso != null && visualizarPasso.equalsIgnoreCase("C")) {
			document.executeScript("visualizaCollapse3()");
			document.getElementById("reclassicarSolicitacao").setValue("S");
			tarefaAssociada = "N";
		} else {
			document.getElementById("reclassicarSolicitacao").setValue("N");
		}

		request.setAttribute("tarefaAssociada", tarefaAssociada);

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getUrgencia() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getUrgencia())) {
			document.getElementById("urgencia").setValue(solicitacaoServicoDto.getUrgencia().trim());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getImpacto() != null && StringUtils.isNotBlank(solicitacaoServicoDto.getImpacto())) {
			document.getElementById("impacto").setValue(solicitacaoServicoDto.getImpacto().trim());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
			document.getElementById("idContrato").setValue("" + solicitacaoServicoDto.getIdContrato());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdServicoContrato() != null) {
			document.getElementById("idServico").setValue("" + solicitacaoServicoDto.getIdServico());
		}

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
			verificaGrupoExecutor(document, request, response);
			document.getSelectById("idGrupoAtual").setValue("" + solicitacaoServicoDto.getIdGrupoAtual());
		}

		if (acao && idSolicitacaoRelacionada == null) {
			if (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
				this.verificaGrupoExecutor(document, request, response);
				this.verificaImpactoUrgencia(document, request, response);
				this.carregaUnidade(document, request, response);
			}

		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");

		if (request.getParameter("idEmpregado") == null) {
			document.executeScript("parent.fecharJanelaAguarde();");
		} else {
			solicitacaoServicoDto.setIsIframe("true");
			document.getElementById("isIframe").setValue("true");
		}

		if (request.getParameter("idEmpregado") != null && !request.getParameter("idEmpregado").equals("") && !request.getParameter("idEmpregado").equals("NaN")) {
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			empregadoDTO.setIdEmpregado(Integer.parseInt((request.getParameter("idEmpregado"))));
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
			if(empregadoDTO != null && empregadoDTO.getIdEmpregado() != null){
				document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
			}
			if(empregadoDTO != null){
				document.getElementById("solicitante").setValue(empregadoDTO.getNome());
			}
			if (empregadoDTO != null) {
				solicitacaoServicoDto.setNomecontato(empregadoDTO.getNome());
				solicitacaoServicoDto.setTelefonecontato(empregadoDTO.getTelefone());
				solicitacaoServicoDto.setRamal(empregadoDTO.getRamal());
				solicitacaoServicoDto.setEmailcontato(empregadoDTO.getEmail().trim());
				solicitacaoServicoDto.setIdUnidade(empregadoDTO.getIdUnidade());
				solicitacaoServicoDto.setRamal(empregadoDTO.getRamal());

				this.preencherComboLocalidade(document, request, response);
			}
		}

		HTMLForm form = document.getForm("form");
		form.setValues(solicitacaoServicoDto);

		String sla = "";
		
		/*	M�rio J�nior - 04/12/2013 #Solicita��o-125972
			Alterado pois na vizualiza��o e na execu��o o SLA n�o � mostrado no 3 passo da solicita��o.*/
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null && solicitacaoServicoDto.getIdServico() != null) {
			if(solicitacaoServicoDto.getPrazoHH()!=null && solicitacaoServicoDto.getPrazoMM()!= null){
				String hh = solicitacaoServicoDto.getPrazoHH().toString();
				String mm = solicitacaoServicoDto.getPrazoMM().toString();
				if (hh.equals("0") && mm.equals("0")) {
					sla = UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar");
				} else if (!hh.equals(0) || !mm.equals(0)){
					if (hh.length() == 1)
						hh = "0" + hh;
					if (mm.length() == 1)
						mm = "0" + mm;

					sla = hh + ":" + mm;
				}
			}
			//SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			//sla = solicitacaoServicoService.calculaSLA(solicitacaoServicoDto, request);
			if (sla.equals("")) {
				sla = "N/A";
			}
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Carrega Combo de Contratos de acordo com PAR�METRO de Vinculo de Colaboradores.
	 * 
	 * @param document
	 * @param usuario
	 * @param solicitacaoServicoDto
	 * @param contratoService
	 * @throws Exception
	 * @throws LogicException
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboContrato(DocumentHTML document, UsuarioDTO usuario, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception, LogicException, ServiceException {

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}

		// COMBO CONTRATO
		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();

		// N�O H� NECESSIDADE DE CARREGAR TODOS OS CONTRATOS
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {

			ContratoDTO contratoDto = new ContratoDTO();

			contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());

			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

			contratoDto.setNome(this.tratarNomeContrato(contratoDto));

			((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());

		} else {

			// H� NECESSIDADE DE CARREGAR TODOS OS CONTRATOS (de acordo com o Usu�rio Logado)
			Collection<ContratoDTO> listContratoAtivo = null;

			if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {

				// PAR�METRO DE VINCULO ATIVO
				listContratoAtivo = contratoService.findAtivosByIdEmpregado(usuario.getIdEmpregado());

			} else {

				// PAR�METRO DE VINCULO INATIVO
				listContratoAtivo = contratoService.listAtivos();

			}

			if (listContratoAtivo != null && !listContratoAtivo.isEmpty()) {

				for (ContratoDTO contratoDto : listContratoAtivo) {
					contratoDto.setNome(this.tratarNomeContrato(contratoDto));
				}

				if (listContratoAtivo.size() > 1) {
					((HTMLSelect) document.getSelectById("idContrato")).addOption("", "Selecione");

					((HTMLSelect) document.getSelectById("idContrato")).addOptions(listContratoAtivo, "idContrato", "nome", null);

				} else {
					ContratoDTO contratoDto = ((List<ContratoDTO>) listContratoAtivo).get(0);

					document.executeScript("adicionarIdContratoNaLookup(" + contratoDto.getIdContrato() + ")");

					((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());

					acao = true;

					// � utilizado para carregar as Unidades.
					contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
				}
			}

		}
	}

	/**
	 * Carrega Combo Categoria Solu��o.
	 * 
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboCategoriaSolucao(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		CategoriaSolucaoService categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);

		Collection listCategoriaSolucao = categoriaSolucaoService.listHierarquia();

		HTMLSelect idCategoriaSolucao = (HTMLSelect) document.getSelectById("idCategoriaSolucao");

		idCategoriaSolucao.removeAllOptions();

		idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (listCategoriaSolucao != null && !listCategoriaSolucao.isEmpty()) {
			idCategoriaSolucao.addOptions(listCategoriaSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
		}
	}

	/**
	 * Carrega Combo Causa.
	 * 
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboCausa(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);

		Collection colCausas = causaIncidenteService.listHierarquia();

		HTMLSelect idCausa = (HTMLSelect) document.getSelectById("idCausaIncidente");

		idCausa.removeAllOptions();

		idCausa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		if (colCausas != null && !colCausas.isEmpty()) {
			idCausa.addOptions(colCausas, "idCausaIncidente", "descricaoCausaNivel", null);
		}
	}

	/**
	 * Carrega Combo Grupo Atual.
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboGrupoAtual(DocumentHTML document, HttpServletRequest request) throws Exception, ServiceException {

		HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");

		idGrupoAtual.removeAllOptions();

		idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> listGrupoServiceDesk = grupoSegurancaService.listGruposServiceDesk();

		if (listGrupoServiceDesk != null) {
			idGrupoAtual.addOptions(listGrupoServiceDesk, "idGrupo", "nome", null);
		}
	}

	/**
	 * Carrega Combo de Tipo Demanda Servi�o.
	 * 
	 * @param document
	 * @param request
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarComboTipoDemanda(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);

		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");

		idTipoDemandaServico.removeAllOptions();

		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		Collection<TipoDemandaServicoDTO> listTipoDemanda = tipoDemandaService.listSolicitacoes();

		if (listTipoDemanda != null) {
			idTipoDemandaServico.addOptions(listTipoDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		}
	}

	/**
	 * Carrega Combos Impact e Urg�ncia.
	 * 
	 * @param document
	 * @param request
	 * @throws Exception
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private void carregarCombosImpactoUrgente(DocumentHTML document, HttpServletRequest request) throws Exception, ServiceException {

		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("urgencia");

		urgencia.removeAllOptions();

		HTMLSelect impacto = (HTMLSelect) document.getSelectById("impacto");

		impacto.removeAllOptions();

		if (!getCalcularDinamicamente().trim().equalsIgnoreCase("S")) {

			urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

			impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

		} else {

			Collection<UrgenciaDTO> listUrgenciaDTO = getPrioridadeSolicitacoesService().consultaUrgencia();

			if (listUrgenciaDTO != null && !listUrgenciaDTO.isEmpty()) {
				for (UrgenciaDTO urgenciaTemp : listUrgenciaDTO) {
					urgencia.addOption(urgenciaTemp.getSiglaUrgencia().toString(), urgenciaTemp.getNivelUrgencia());
				}
			}

			Collection<ImpactoDTO> listImpactoDTO = getPrioridadeSolicitacoesService().consultaImpacto();

			if (listImpactoDTO != null && !listImpactoDTO.isEmpty()) {
				for (ImpactoDTO impactoTemp : listImpactoDTO) {
					impacto.addOption(impactoTemp.getSiglaImpacto().toString(), impactoTemp.getNivelImpacto());
				}
			}
		}
	}

	/**
	 * Concatena ao Nome do Contrato o N�mero do Contrato + Data do Contrato + Nome do Cliente + Nome do Fornecedor.
	 * 
	 * @param contratoDto
	 * @return String - Nome do Contrato tratado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 03.11.2013
	 */
	private String tratarNomeContrato(ContratoDTO contratoDto) throws Exception {

		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);

		String nomeCliente = "";
		String nomeFornecedor = "";

		ClienteDTO clienteDto = new ClienteDTO();

		clienteDto.setIdCliente(contratoDto.getIdCliente());

		clienteDto = (ClienteDTO) clienteService.restore(clienteDto);

		if (clienteDto != null) {
			nomeCliente = clienteDto.getNomeRazaoSocial();
		}

		FornecedorDTO fornecedorDto = new FornecedorDTO();

		fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());

		fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);

		if (fornecedorDto != null) {
			nomeFornecedor = fornecedorDto.getRazaoSocial();
		}

		String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.dateToSTR(contratoDto.getDataContrato()) + " (" + nomeCliente + " - " + nomeFornecedor + ")";

		return nomeContrato;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean bExisteSolicitacao = false;
		SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = null;
		try {
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			String CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO, "N");

			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}

			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
			if(solicitacaoServicoDto != null){
				bExisteSolicitacao = solicitacaoServicoDto.getIdSolicitacaoServico() != null;
			}
			
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateServico(solicitacaoServicoDto);
			if (templateDto != null && templateDto.isQuestionario()) {
				Timestamp ts1 = UtilDatas.getDataHoraAtual();
				double tempo = 0;
				solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession().getAttribute("dados_solicit_quest");
				while (solicitacaoServicoQuestionarioDto == null && tempo <= 10000) {
					solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession().getAttribute("dados_solicit_quest");
					Timestamp ts2 = UtilDatas.getDataHoraAtual();
					tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
				}
				if (solicitacaoServicoQuestionarioDto == null) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.informacoesComplementares"));
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
			}
			solicitacaoServicoService.deserializaInformacoesComplementares(solicitacaoServicoDto, solicitacaoServicoQuestionarioDto);

			// Criado por Bruno.Aquino
			// Cria objeto BaseConhecimento e insere dentro setBaseConhecimento

			BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
			UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

			if(solicitacaoServicoDto != null){
				baseConhecimento.setTitulo(solicitacaoServicoDto.getTituloBaseConhecimento());
				baseConhecimento.setConteudo("Descri��o: " + solicitacaoServicoDto.getDescricaoSemFormatacao() + "<br><br>" + "Solu��o/Resposta: " + solicitacaoServicoDto.getResposta());
			}
			baseConhecimento.setOrigem("5");// Servi�o
			baseConhecimento.setDataExpiracao(UtilDatas.getSqlDate(UtilDatas.geraUmAnoSeguinte(UtilDatas.getDataAtual())));
			baseConhecimento.setStatus("N");
			baseConhecimento.setErroConhecido("S");
			baseConhecimento.setSituacao("EAV");
			baseConhecimento.setPrivacidade("C");
			baseConhecimento.setDataInicio(UtilDatas.getDataAtual());
			baseConhecimento.setArquivado("N");
			baseConhecimento.setVersao("1.0");
			baseConhecimento.setIdUsuarioAutor(usuarioDto.getIdUsuario());
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setBeanBaseConhecimento(baseConhecimento);
			}

			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdUnidade() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.unidadecontato"));
				this.verificaImpactoUrgencia(document, request, response);
				document.executeScript("habilitaBotaoGravar()");
				if (solicitacaoServicoQuestionarioDto != null)
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
				return;

			}

			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEscalar() != null && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S")) {
				if (solicitacaoServicoDto.getIdGrupoAtual() == null) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.grupoatendimento"));
					this.verificaImpactoUrgencia(document, request, response);
					document.executeScript("habilitaBotaoGravar()");
					if (solicitacaoServicoQuestionarioDto != null)
						carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
			}
			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			ServicoContratoDTO servicoContratoDto = null;
			if(solicitacaoServicoDto != null){
				servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
			}
			if (servicoContratoDto != null) {
				if (servicoContratoDto != null && solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
					Integer idGrupoNivel1 = null;
					if (servicoContratoDto.getIdGrupoNivel1() != null && servicoContratoDto.getIdGrupoNivel1().intValue() > 0) {
						idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
					} else {
						String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, null, null);
						if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
							try {
								idGrupoNivel1 = new Integer(idGrupoN1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0) {
						document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.grupoatendnivel"));
						if (solicitacaoServicoQuestionarioDto != null)
							carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
						return;
					}

				}
			}
			
//			//Tratamento para quando o grupo n�o tem permiss�o para cria��o da solicita��o
//			
//			SolicitacaoServicoDTO solicitacaoAuxiliar = null;
//			ServicoContratoDTO servicoContratoAuxiliar = null;
//			if(solicitacaoServicoDto.getIdSolicitacaoServico() != null){
//				solicitacaoAuxiliar = new SolicitacaoServicoDao().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
//				servicoContratoAuxiliar = servicoContratoService.findByIdContratoAndIdServico(solicitacaoAuxiliar.getIdContrato(), solicitacaoAuxiliar.getIdServico());
//			}
//			
//			ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
//			//ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
//			FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
//			TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
//			TipoFluxoDTO tipoFluxoDto = new TipoFluxoDTO();
//			FluxoServicoDTO fluxoServicoDto = null;
//			if(servicoContratoAuxiliar != null){
//				fluxoServicoDto = fluxoServicoDao.findPrincipalByIdServicoContrato(servicoContratoAuxiliar.getIdServicoContrato());
//			} else {
//				fluxoServicoDto = fluxoServicoDao.findPrincipalByIdServicoContrato(servicoContratoDto.getIdServicoContrato());
//			}
//
//			int idTipoFluxoSolicitacaoServico = 0;
//
//			// Verifica se h� fluxo associado ao servi�o contrato
//			if (fluxoServicoDto != null && fluxoServicoDto.getIdTipoFluxo() != null) {
//				idTipoFluxoSolicitacaoServico = fluxoServicoDto.getIdTipoFluxo();
//			} else {
//				// Verifica o fluxo padr�o para Solicita��o Servi�o definido em Par�metro
//				String nomeFluxoPadraoSolicitacaoServico = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NomeFluxoPadraoServicos, "SolicitacaoServico");
//
//				if (nomeFluxoPadraoSolicitacaoServico != null) {
//					tipoFluxoDto = tipoFluxoDao.findByNome(nomeFluxoPadraoSolicitacaoServico);
//
//					if (tipoFluxoDto != null && tipoFluxoDto.getIdTipoFluxo() != null) {
//						idTipoFluxoSolicitacaoServico = tipoFluxoDto.getIdTipoFluxo();
//					}
//				}
//			}
//
//			int idGrupo = 0;
//			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null) {
//				idGrupo = solicitacaoServicoDto.getIdGrupoAtual();
//			} else if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null){
//				idGrupo = servicoContratoDto.getIdGrupoExecutor();
//			}else if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoNivel1() != null) {
//				idGrupo = solicitacaoServicoDto.getIdGrupoNivel1();
//			}
//
//			if (idGrupo > 0 && idTipoFluxoSolicitacaoServico > 0) {
//
//				boolean resultado = solicitacaoServicoService.permissaoGrupoExecutorServico(idGrupo, idTipoFluxoSolicitacaoServico);
//
//				if (resultado == false) {
//					document.alert(UtilI18N.internacionaliza(request, "solicitacaoServico.grupoSemPermissao"));
//					this.verificaImpactoUrgencia(document, request, response);
//					document.executeScript("habilitaBotaoGravar()");
//					if (solicitacaoServicoQuestionarioDto != null)
//						carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
//					return;
//				}
//			} else {
//				document.alert(UtilI18N.internacionaliza(request, "fluxo.fluxoserviconaodefinido"));
//				this.verificaImpactoUrgencia(document, request, response);
//				document.executeScript("habilitaBotaoGravar()");
//				if (solicitacaoServicoQuestionarioDto != null)
//					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
//				return;
//			}
//			//Fim Tratamento de grupo sem permiss�o.
			

			List<ConhecimentoSolicitacaoDTO> colConhecimentoSolicitacao = (List<ConhecimentoSolicitacaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					ConhecimentoSolicitacaoDTO.class, "colConhecimentoSolicitacao_Serialize", request);
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColConhecimentoSolicitacaoSerialize(colConhecimentoSolicitacao);
			}
			
			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getSituacao() != null && solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.name())) {

				if (solicitacaoServicoDto.getResposta().trim().equalsIgnoreCase("")) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.solucaoresposta"));
					this.verificaImpactoUrgencia(document, request, response);
					document.executeScript("habilitaBotaoGravar()");
					if (solicitacaoServicoQuestionarioDto != null)
						carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
				TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class,
						WebUtil.getUsuarioSistema(request));
				TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
				tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoDto.getIdTipoDemandaServico());
				if (tipoDemandaServicoDTO.getIdTipoDemandaServico() == null) {
					SolicitacaoServicoDTO solicitacaoServicoAux = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
					tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoAux.getIdTipoDemandaServico());
				}
				tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
				if (tipoDemandaServicoDTO != null) {
					if (tipoDemandaServicoDTO.getClassificacao().equalsIgnoreCase("I")) {
						if (CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("S")) {
							if (solicitacaoServicoDto.getIdCausaIncidente() == null) {
								document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.classifiqueincidente"));
								this.verificaImpactoUrgencia(document, request, response);
								document.executeScript("habilitaBotaoGravar()");
								if (solicitacaoServicoQuestionarioDto != null)
									carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
								return;
							}
							if (solicitacaoServicoDto.getIdCategoriaSolucao() == null) {
								document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.classifiquesolucao"));
								this.verificaImpactoUrgencia(document, request, response);
								document.executeScript("habilitaBotaoGravar()");
								if (solicitacaoServicoQuestionarioDto != null)
									carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
								return;
							}
						}
					}
				}
				boolean bvalidaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento() != null && solicitacaoServicoDto.getValidaBaseConhecimento().equalsIgnoreCase("S");
				if (bvalidaBaseConhecimento) {
					boolean informouBaseConhecimento = solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize() != null
							&& solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize().size() > 0;
					if (!informouBaseConhecimento) {
						document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.baseconhecimento"));
						this.verificaImpactoUrgencia(document, request, response);
						document.executeScript("habilitaBotaoGravar()");
						if (solicitacaoServicoQuestionarioDto != null)
							carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
						return;
					}
				}
			}

			Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED2");
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
			}
			
			Collection colItensProblema = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaDTO.class, "colItensProblema_Serialize", request);
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColItensProblema(colItensProblema);
			}
			
			List<RequisicaoMudancaDTO> colItensMudanca = (List<RequisicaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaDTO.class,
					"colItensMudanca_Serialize", request);
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColItensMudanca(colItensMudanca);
			}
			
			List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize",
					request);
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColItensICSerialize(colItensIC);
			}
			
			List<BaseConhecimentoDTO> colItensBaseConhecimento = (List<BaseConhecimentoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BaseConhecimentoDTO.class, "colItensBaseConhecimento_Serialize",
					request);
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setColItensBaseConhecimento(colItensBaseConhecimento);
			}
			
			EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator.getInstance().getService(EmailSolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getMessageId() != null && solicitacaoServicoDto.getMessageId().trim().length() > 0) {
				EmailSolicitacaoServicoDTO emailDto = new EmailSolicitacaoServicoDTO();
				emailDto.setIdEmailSolicitacaoServico(Integer.parseInt(solicitacaoServicoDto.getMessageId()));
				emailDto = (EmailSolicitacaoServicoDTO) emailSolicitacaoServicoService.restore(emailDto);
				emailDto.setSituacao("Resolvido");
				emailSolicitacaoServicoService.update(emailDto);
			}
			if(solicitacaoServicoDto != null){
				solicitacaoServicoDto.setUsuarioDto(usuario);
				solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
			}
			
			try {
				/* Escapa os caracteres especiais */
				if(solicitacaoServicoDto != null){
					
					//foi comentado pois n�o h� mais necessidade de retirar esses caracteres, n�o estamos utilizando o fckEditor mais para apresentar esses valores.
//					solicitacaoServicoDto.setObservacao(UtilStrings.getParameter(solicitacaoServicoDto.getObservacao()));
//					//solicitacaoServicoDto.setDescricao(UtilStrings.retiraCaracteresEspeciais(solicitacaoServicoDto.getDescricao()));
//					solicitacaoServicoDto.setDetalhamentoCausa(UtilStrings.getParameter(solicitacaoServicoDto.getDetalhamentoCausa()));
//					solicitacaoServicoDto.setRegistroexecucao(UtilStrings.getParameter(solicitacaoServicoDto.getRegistroexecucao()));
				}
				if (solicitacaoServicoDto != null && solicitacaoServicoDto.getResposta() != null) {
					//solicitacaoServicoDto.setResposta(UtilStrings.getParameter(solicitacaoServicoDto.getResposta()));
				}

				// solicitacaoServicoDto.setObservacao((solicitacaoServicoDto.getObservacao()));
				// solicitacaoServicoDto.setRegistroexecucao((solicitacaoServicoDto.getRegistroexecucao()));
				// solicitacaoServicoDto.setDetalhamentoCausa((solicitacaoServicoDto.getDetalhamentoCausa()));
				// solicitacaoServicoDto.setResposta(solicitacaoServicoDto.getResposta());

				if ((solicitacaoServicoDto != null) && (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0)) {
					solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto);
					// document.executeScript("document.getElementById('divInformacoesComplementares').style.display = 'none';");
					// document.alert("Registro efetuado com sucesso. Solicita��o N.o: "
					// + solicitacaoServicoDto.getIdSolicitacaoServico() +
					// " criada.");

					document.executeScript("$('#divInformacoesComplementares').switchClass( 'ativo', 'inativo', null );");

					String comando = "mostraMensagemInsercao('<h3>" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao")
							+ " <b><u>" + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>"
							+ UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": " + solicitacaoServicoDto.getIdPrioridade();
					if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
						comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
					}
					comando = comando + "</h3>')";
					document.executeScript(comando);
					/* carregaInformacoesComplementares(document, request, solicitacaoServicoDto); */
					return;
				} else {
					solicitacaoServicoService.updateInfo(solicitacaoServicoDto);

					// document.executeScript("document.getElementById('divInformacoesComplementares').style.display = 'none';");
					document.alert(UtilI18N.internacionaliza(request, "MSG06"));
				}
			} catch (Exception e) {
				if (!bExisteSolicitacao)
					solicitacaoServicoDto.setIdSolicitacaoServico(null);
				if (solicitacaoServicoQuestionarioDto != null)
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
				String msgErro = e.getMessage();
				msgErro = msgErro.replaceAll("java.lang.Exception:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.ServiceException:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.centralit.citcorpore.exception.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.centralit.citajax.exception.LogicException:", "");
				msgErro = msgErro.replaceAll("Wrapped", "");
				msgErro = msgErro.replaceAll("params.get\\(\"execucaoFluxo\"\\).recuperaGrupoAprovador\\(\\);", "");
				msgErro = msgErro.replaceAll("\\(script#1\\)", "");
				document.alert("" + msgErro);
				this.verificaImpactoUrgencia(document, request, response);
				document.executeScript("habilitaBotaoGravar()");
				return;
			}

			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getReclassificar() != null && solicitacaoServicoDto.getReclassificar().equals("S")) {
				document.executeScript("document.getElementById('divBotoes').style.display = 'block';");
			}

			carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

			String reclassificar = request.getParameter("reclassificar");
			if(reclassificar!= null && reclassificar != "" && reclassificar.equals("S")){
				document.executeScript("parent.refreshTelaGerenciamento()");
			} else{
				document.executeScript("parent.carregaListaServico()");
			}
			solicitacaoServicoDto = null;
		} finally {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	public void sincronizaAD(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getFiltroADPesq() == null) {
			solicitacaoServicoDto.setFiltroADPesq(document.getElementById("filtroADPesq").getValue());
		}

		ContratoDTO contratoDto = new ContratoDTO();

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

		contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());

		contratoDto = (ContratoDTO) contratoService.restore(contratoDto);

		Collection<ADUserDTO> listUsuariosADDto = LDAPUtils.consultaEmpregado(solicitacaoServicoDto.getFiltroADPesq(), contratoDto.getIdGrupoSolicitante());

		if (listUsuariosADDto != null && !listUsuariosADDto.isEmpty()) {

			for (ADUserDTO usuarioADDto : listUsuariosADDto) {

				document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML("Sincroniza��o conclu�da com sucesso. Favor fazer a busca na lookup de colaborador.");
			}

		} else {

			document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML("Nenhum resultado encontrado.");

		}
		document.executeScript("fechar_aguarde();");

		solicitacaoServicoDto = null;

		contratoDto = null;
	}

	/**
	 * Carrega Combo de Servi�os com base no Tipo Demanda e Contrato.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	// public void carregaServicosMulti(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	// SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
	//
	// if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
	// solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
	// }
	//
	// document.getElementById("divScript").setInnerHTML(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
	//
	// HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
	// idServico.removeAllOptions();
	//
	// if (solicitacaoServicoDto.getIdTipoDemandaServico() == null) {
	// document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = 'N/A';");
	//
	// return;
	// }
	//
	// if (solicitacaoServicoDto.getIdContrato() == null) {
	// document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.contrato"));
	// return;
	// }
	//
	// String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");
	//
	// if (!UtilStrings.isNotVazio(controleAccUnidade)) {
	// controleAccUnidade = "N";
	// }
	//
	// EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	// int idUnidade = -999;
	//
	// if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
	// EmpregadoDTO empregadoDto = new EmpregadoDTO();
	// empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
	// if (solicitacaoServicoDto.getIdSolicitante() != null) {
	// empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
	// if (empregadoDto != null && empregadoDto.getIdUnidade() != null) {
	// idUnidade = empregadoDto.getIdUnidade().intValue();
	// }
	// }
	// }
	//
	// ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
	// UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
	//
	// idServico.removeAllOptions();
	//
	// Collection listServicoDoTipoDemandaEContrato = servicoService.findByIdTipoDemandaAndIdContrato(solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdContrato(),
	// solicitacaoServicoDto.getIdCategoriaServico());
	//
	// int cont = 0;
	// Integer idServicoCasoApenas1 = null;
	// if (listServicoDoTipoDemandaEContrato != null) {
	// // this.verificaImpactoUrgencia(document, request, response);
	// /* if (col.size() > 1) */
	//
	// // idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	//
	// for (Iterator it = listServicoDoTipoDemandaEContrato.iterator(); it.hasNext();) {
	// ServicoDTO servicoDTO = (ServicoDTO) it.next();
	// if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
	// if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
	// if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
	// UnidadesAccServicosDTO unidadesAccServicosDTO = new UnidadesAccServicosDTO();
	// unidadesAccServicosDTO.setIdServico(servicoDTO.getIdServico());
	// unidadesAccServicosDTO.setIdUnidade(idUnidade);
	// unidadesAccServicosDTO = (UnidadesAccServicosDTO) unidadeAccService.restore(unidadesAccServicosDTO);
	// if (unidadesAccServicosDTO != null) {// Se existe acesso
	// // idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
	// idServicoCasoApenas1 = servicoDTO.getIdServico();
	// cont++;
	// }
	// } else {
	// // idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
	// idServicoCasoApenas1 = servicoDTO.getIdServico();
	// cont++;
	// }
	// }
	// }
	// }
	// // --- RETITRADO POR EMAURI EM 16/07 - TRATAMENTO DE DELETED --> idServico.addOptions(col, "idServico", "nomeServico", null);
	// }
	// if (cont == 1) { // Se for apenas um servico encontrado, ja executa o carrega contratos.
	// solicitacaoServicoDto.setIdServico(idServicoCasoApenas1);
	// }
	//
	// carregaBaseConhecimentoAssoc(document, request, response);
	// // document.executeScript("geraAutoCompleteServico()");
	//
	// solicitacaoServicoDto = null;
	// }

	/**
	 * Preenche a combo Unidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	// public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	//
	// String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
	//
	// if (PAGE_CADADTRO_SOLICITACAOSERVICO.equalsIgnoreCase("pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load")) {
	//
	// SolicitacaoServicoMultiContratos solicitacaoServicoMultiContratos = new SolicitacaoServicoMultiContratos();
	//
	// SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
	//
	// if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {
	//
	// solicitacaoServicoMultiContratos.carregaUnidade(document, request, response);
	// }
	//
	// solicitacaoServicoDto = null;
	//
	// } else {
	// UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
	// HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
	// ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
	//
	// inicializarCombo(comboUnidade, request);
	// if (unidades != null) {
	// for (UnidadeDTO unidade : unidades)
	// if (unidade.getDataFim() == null)
	// comboUnidade.addOption(Util.tratarAspasSimples(unidade.getIdUnidade().toString()), Util.tratarAspasSimples(unidade.getNomeNivel()));
	// }
	// }
	// }

	/**
	 * Preenche a Combo de Unidades de acordo com o Par�metro UNIDADE_VINC_CONTRATOS.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parametroUnidadeVinculadoAContratos = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

			solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

			ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
			servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());
			if (solicitacaoServicoDto.getIdServicoContrato() != null) {
				servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
			} else {
				servicoContratoDTO = null;
			}
			if (servicoContratoDTO != null) {
				solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
				solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
			}

		}

		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		HTMLSelect comboUnidadeMultContratos = (HTMLSelect) document.getSelectById("idUnidade");

		inicializarCombo(comboUnidadeMultContratos, request);

		if (parametroUnidadeVinculadoAContratos.trim().equalsIgnoreCase("S")) {

			Integer idContrato = solicitacaoServicoDto.getIdContrato();

			if (idContrato != null) {

				ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);

				if (unidades != null) {
					for (UnidadeDTO unidade : unidades) {
						if (unidade.getDataFim() == null) {
							comboUnidadeMultContratos.addOption(Util.tratarAspasSimples(unidade.getIdUnidade().toString()), Util.tratarAspasSimples(unidade.getNomeNivel()));
						}

					}
				}
			}
		} else {

			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();

			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidadeMultContratos.addOption(Util.tratarAspasSimples(unidade.getIdUnidade().toString()), Util.tratarAspasSimples(unidade.getNomeNivel()));
					}
				}
			}
		}

		solicitacaoServicoDto = null;

	}

	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	public void carregaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// carregaServicosMulti(document, request, response);
	}

	public void verificaImpactoUrgencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		document.getSelectById("impacto").setDisabled(false);
		document.getSelectById("urgencia").setDisabled(false);
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

		if (servicoContratoDto != null) {
			AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
			if (acordoNivelServicoDto == null) {
				// Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja vinculado ao servicocontrato.
				AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO == null) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
				// Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);
				if (acordoNivelServicoDto == null) {
					// Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
			}
			if (acordoNivelServicoDto.getImpacto() != null) {
				document.getSelectById("impacto").setValue("" + acordoNivelServicoDto.getImpacto());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("impacto").setDisabled(true);
				}
			} else {
				document.getSelectById("impacto").setValue("B");
			}
			if (acordoNivelServicoDto.getUrgencia() != null) {
				document.getSelectById("urgencia").setValue("" + acordoNivelServicoDto.getUrgencia());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("urgencia").setDisabled(true);
				}
			} else {
				document.getSelectById("urgencia").setValue("B");
			}
		} else {
			document.getSelectById("impacto").setValue("B");
			document.getSelectById("urgencia").setValue("B");
		}

		servicoContratoDto = null;

		solicitacaoServicoDto = null;
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> RESTORE DE SOLICITACAOSERVICO.JAVA
		// super.restore(document, request, response);

		String editar = request.getParameter("editar");

		// request.getSession(true).setAttribute("colUploadsGED", null);

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		ContatoSolicitacaoServicoService contatoSolicitacaoServicoService = (ContatoSolicitacaoServicoService) ServiceLocator.getInstance().getService(ContatoSolicitacaoServicoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoEvtMonService solicitacaoServicoEvtMonService = (SolicitacaoServicoEvtMonService) ServiceLocator.getInstance().getService(SolicitacaoServicoEvtMonService.class,
				WebUtil.getUsuarioSistema(request));
		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class,
				WebUtil.getUsuarioSistema(request));
		EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(EventoMonitConhecimentoService.class,
				WebUtil.getUsuarioSistema(request));
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));

		Integer idTarefa = solicitacaoServicoDto.getIdTarefa();
		String acaoFluxo = solicitacaoServicoDto.getAcaoFluxo();
		String reclassificar = solicitacaoServicoDto.getReclassificar();
		String escalar = solicitacaoServicoDto.getEscalar();
		String alterarSituacao = solicitacaoServicoDto.getAlterarSituacao();

		alterarSituacao = "S";
		String validaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento();

		solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

		if (solicitacaoServicoDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.registronaoencotrado"));
			return;
		}

		solicitacaoServicoDto.setIdTarefa(idTarefa);
		solicitacaoServicoDto.setAcaoFluxo(acaoFluxo);
		solicitacaoServicoDto.setReclassificar(reclassificar);
		solicitacaoServicoDto.setEscalar(escalar);
		solicitacaoServicoDto.setAlterarSituacao(alterarSituacao);
		solicitacaoServicoDto.setValidaBaseConhecimento(validaBaseConhecimento);

		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		if (solicitacaoServicoDto.getIdSolicitante() != null) {
			empregadoDTO.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
		}
		ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
		servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

		if (solicitacaoServicoDto.getIdServicoContrato() != null) {
			servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
		} else {
			servicoContratoDTO = null;
		}

		if (servicoContratoDTO != null) {
			solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
			solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
			ServicoDTO servicoDto = new ServicoDTO();
			servicoDto.setIdServico(servicoContratoDTO.getIdServico());
			servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

			if (servicoDto != null) {
				solicitacaoServicoDto.setIdCategoriaServico(servicoDto.getIdCategoriaServico());

				// document.setBean(solicitacaoServicoDto);
				// carregaServicos(document, request, response);
				// carregaContratos(document, request, response);
			}

		}

		ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = null;
		if (solicitacaoServicoDto.getIdContatoSolicitacaoServico() != null) {
			contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
			contatoSolicitacaoServicoDTO.setIdcontatosolicitacaoservico(solicitacaoServicoDto.getIdContatoSolicitacaoServico());
			contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoService.restore(contatoSolicitacaoServicoDTO);
		}

		if (contatoSolicitacaoServicoDTO != null) {
			solicitacaoServicoDto.setNomecontato(contatoSolicitacaoServicoDTO.getNomecontato());
			solicitacaoServicoDto.setEmailcontato(contatoSolicitacaoServicoDTO.getEmailcontato());
			solicitacaoServicoDto.setTelefonecontato(contatoSolicitacaoServicoDTO.getTelefonecontato());
			solicitacaoServicoDto.setRamal(contatoSolicitacaoServicoDTO.getRamal());
			solicitacaoServicoDto.setObservacao(contatoSolicitacaoServicoDTO.getObservacao());
			solicitacaoServicoDto.setIdLocalidade(contatoSolicitacaoServicoDTO.getIdLocalidade());
			this.preencherComboLocalidade(document, request, response);
		}

		ItemConfiguracaoDTO itemConfiguracaoDTO = null;
		ItemConfiguracaoDTO itemConfiguracaoFilhoDTO = null;
		String tagItemCfg = "";
		if (solicitacaoServicoDto.getIdItemConfiguracao() != null) {
			ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			itemConfiguracaoDTO = new ItemConfiguracaoDTO();
			itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);

			if (solicitacaoServicoDto.getIdItemConfiguracaoFilho() != null) {
				itemConfiguracaoFilhoDTO = new ItemConfiguracaoDTO();
				itemConfiguracaoFilhoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracaoFilho());
				itemConfiguracaoFilhoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoFilhoDTO);
				if (itemConfiguracaoFilhoDTO != null && itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao() != null) {
					TipoItemConfiguracaoService tipoItemConfiguracaoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
					TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO = new TipoItemConfiguracaoDTO();
					tipoItemConfiguracaoDTO.setId(itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao());
					tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) tipoItemConfiguracaoService.restore(tipoItemConfiguracaoDTO);
					if (tipoItemConfiguracaoDTO != null) {
						tagItemCfg = tipoItemConfiguracaoDTO.getTag();
					}
				}
			}
		}

		if (solicitacaoServicoDto.getSolicitanteUnidade() == null) {
			solicitacaoServicoDto.setSolicitanteUnidade("");
		}
		if (solicitacaoServicoDto.getSolicitante() == null) {
			solicitacaoServicoDto.setSolicitante("");
		}

		// HTMLForm form = document.getForm("form");
		// form.clear();

		if (itemConfiguracaoDTO != null) {
			document.getTextBoxById("itemConfiguracao").setValue(itemConfiguracaoDTO.getIdentificacao());
			/* document.executeScript("exibeCampos()"); */
			document.setBean(solicitacaoServicoDto);
			if (tagItemCfg != null && tagItemCfg.equalsIgnoreCase("SOFTWARES")) {
				document.executeScript("document.form.caracteristica[0].checked = true");
				preecherComboSoftware(document, request, response);
			} else {
				// document.executeScript("document.form.caracteristica[1].checked = true");
				preecherComboHardware(document, request, response);
			}
		}

		// form.setValues(solicitacaoServicoDto);

		if (solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("S");
		}

		if (empregadoDTO != null) {
			document.getTextBoxById("solicitante").setValue(empregadoDTO.getNome());
		}

		// document.executeScript("setValueToDataEditor()");
		// document.getElementById("divMails").setVisible(false);

		if (solicitacaoServicoDto.getReclassificar() != null && solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S")) {
			// document.getElementById("divMessage").setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.reclassificacao") + ".</font>");
		} else {
			document.getSelectById("idCategoriaServico").setDisabled(true);
			document.getSelectById("idContrato").setDisabled(true);
			document.getSelectById("idServico").setDisabled(true);
			document.getSelectById("idTipoDemandaServico").setDisabled(true);
			document.getSelectById("urgencia").setDisabled(true);
			document.getSelectById("impacto").setDisabled(true);
			if (solicitacaoServicoDto.getEditar() == null) {
				solicitacaoServicoDto.setEditar("S");
			}
			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar() != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
				// document.getElementById("divMessage").setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.aleracaosolicitacao") +
				// ".</font>");
			} else {
				// document.getElementById("divMessage").setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.aleracaoclassificacao") +
				// ".</font>");
			}
		}

		boolean bEscalar = solicitacaoServicoDto.getEscalar() != null && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S");
		boolean bAlterarSituacao = solicitacaoServicoDto.getAlterarSituacao() != null && solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S");
		if (!bAlterarSituacao) {
			// document.executeScript("document.form.situacao[0].disabled = true;");
			// document.executeScript("document.form.situacao[1].disabled = true;");
			// document.executeScript("document.form.situacao[2].disabled = true;");
		}
		if (!bEscalar) {
			document.getSelectById("idGrupoAtual").setDisabled(true);
		}

		// document.getElementById("divMessage").setVisible(true);

		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, solicitacaoServicoDto.getIdSolicitacaoServico());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		String quantidadeAnexosStr = "0";
		if (colAnexosUploadDTO != null && colAnexosUploadDTO.size() > 0) {
			Integer quantidadeAnexos = colAnexosUploadDTO.size();
			quantidadeAnexosStr = String.valueOf(quantidadeAnexos);
			document.getElementById("quantidadeAnexos").setValue(quantidadeAnexosStr);
		} else {
			document.getElementById("quantidadeAnexos").setValue(quantidadeAnexosStr);
		}

		request.getSession(true).setAttribute("colUploadsGED2", colAnexosUploadDTO);
		request.getSession().setAttribute("colUploadsGED2", colAnexosUploadDTO);

		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
		Collection colOcorrencias = ocorrenciaSolicitacaoService.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colOcorrencias != null && colOcorrencias.size() > 0) {
			String str = listInfoRegExecucaoSolicitacao(colOcorrencias, request);
			request.setAttribute("strRegistrosExecucao", str);
		}

		carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

		/**
		 * Verifica o uso da vers�o free
		 */
		if (!br.com.citframework.util.Util.isVersionFree(request)) {

			HTMLTable tblProblema = document.getTableById("tblProblema");
			tblProblema.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				ProblemaDTO problemadto = new ProblemaDTO();
				problemadto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				Collection col = this.getProblemaService().findByIdSolictacaoServico(problemadto.getIdSolicitacaoServico());
				if (col != null) {
					tblProblema.addRowsByCollection(col, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idProblema" }, "Problema j� cadastrado!!",
							new String[] { "exibeIconesProblema" }, null, null);
					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
				}
			}

			HTMLTable tblMudanca = document.getTableById("tblMudanca");
			tblMudanca.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();
				requisicaoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				Collection col = this.getRequisicaoMudancaService().findBySolictacaoServico(requisicaoMudancaDTO);
				if (col != null) {
					tblMudanca.addRowsByCollection(col, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idRequisicaoMudanca" }, "Mudan�a j� cadastrado!!",
							new String[] { "exibeIconesMudanca" }, null, null);
					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
				}
			}

			HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");
			tblBaseConhecimento.deleteAllRows();
			ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDTO = new ConhecimentoSolicitacaoDTO();
			conhecimentoSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			Collection colConhecimentoSolicitacao = this.getConhecimentoSolicitacaoService().findBySolictacaoServico(conhecimentoSolicitacaoDTO);

			if (colConhecimentoSolicitacao != null) {
				tblBaseConhecimento.addRowsByCollection(colConhecimentoSolicitacao, new String[] { "idBaseConhecimento", "titulo", "" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null);

				document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
			}

			HTMLTable tblIC = document.getTableById("tblIC");
			tblIC.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServDTO = new ItemCfgSolicitacaoServDTO();
				ItemCfgSolicitacaoServService serviceItem = (ItemCfgSolicitacaoServService) ServiceLocator.getInstance().getService(ItemCfgSolicitacaoServService.class, null);
				ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
				Collection col = serviceItem.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

				if (col != null) {
					for (Iterator it = col.iterator(); it.hasNext();) {
						ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServAux = (ItemCfgSolicitacaoServDTO) it.next();
						ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
						itemConfiguracaoAux.setIdItemConfiguracao(itemCfgSolicitacaoServAux.getIdItemConfiguracao());
						itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoAux);
						if (itemConfiguracaoAux != null) {
							itemCfgSolicitacaoServAux.setIdentificacaoStatus(itemConfiguracaoAux.getIdentificacaoStatus());
						}
					}
				}

				if (col != null) {
					tblIC.addRowsByCollection(col, new String[] { "idItemConfiguracao", "identificacao", "", "" }, new String[] { "idItemConfiguracao" }, "Item Configura��o j� cadastrado!!",
							new String[] { "exibeIconesIC" }, null, null);

					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
				}
			}
		}

		this.abrirListaDeSubSolicitacoes(document, request, response);

		StringBuilder strEventos = new StringBuilder();
		Collection colEventsSolic = solicitacaoServicoEvtMonService.findByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colEventsSolic != null && colEventsSolic.size() > 0) {
			strEventos.append("<table border='1' width='100%'>");
			for (Iterator it = colEventsSolic.iterator(); it.hasNext();) {
				SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = (SolicitacaoServicoEvtMonDTO) it.next();
				EventoMonitoramentoDTO eventoMonitoramentoDto = new EventoMonitoramentoDTO();
				eventoMonitoramentoDto.setIdEventoMonitoramento(solicitacaoServicoEvtMonDTO.getIdEventoMonitoramento());
				eventoMonitoramentoDto = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDto);
				if (eventoMonitoramentoDto != null) {
					Collection<EventoMonitConhecimentoDTO> colEventos = eventoMonitConhecimentoService.listByIdEventoMonitoramento(eventoMonitoramentoDto.getIdEventoMonitoramento());
					Integer[] ids = null;
					if (colEventos != null && colEventos.size() > 0) {
						ids = new Integer[colEventos.size()];
						int x = 0;
						for (Iterator itEvtBC = colEventos.iterator(); itEvtBC.hasNext();) {
							EventoMonitConhecimentoDTO eventoMonitConhecimentoDTO = (EventoMonitConhecimentoDTO) itEvtBC.next();
							ids[x] = eventoMonitConhecimentoDTO.getIdBaseConhecimento();
							x++;
						}
					}

					Collection colBasesConhec = baseConhecimentoService.listarBaseConhecimentoByIds(ids);

					StringBuilder strBC = new StringBuilder();

					if (colBasesConhec != null && colBasesConhec.size() > 0) {
						strBC.append("<table width='100%'>");
						for (Iterator itBC = colBasesConhec.iterator(); itBC.hasNext();) {
							BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) itBC.next();
							String onclickStr = "onclick='abreConhecimento(\"" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
									+ br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages\", \"idBaseConhecimento=" + baseConhecimentoDto.getIdBaseConhecimento() + "\")'";
							strBC.append("<tr>");
							strBC.append("<td>");
							strBC.append("<img style='cursor:pointer' src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
									+ br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/script.png' border='0' " + onclickStr + "/>");
							strBC.append("</td>");
							strBC.append("<td style='cursor:pointer' " + onclickStr + ">");
							strBC.append("" + UtilStrings.retiraAspasApostrofe(baseConhecimentoDto.getTitulo()));
							strBC.append("</td>");
							strBC.append("</tr>");
						}
						strBC.append("</table>");
					}

					strEventos.append("<tr>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append("<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
							+ "/imagens/relampago.png' border='0'/>");
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(eventoMonitoramentoDto.getNomeEvento()));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings.nullToVazio(solicitacaoServicoEvtMonDTO.getNomeHost())));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings.nullToVazio(solicitacaoServicoEvtMonDTO.getNomeService())));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(strBC.toString());
					strEventos.append("</td>");
					strEventos.append("</tr>");
				}
			}
			strEventos.append("</table>");

			// document.getElementById("divEvtMonitoramento").setInnerHTML(strEventos.toString());
			// document.getElementById("divEvtMonitoramento").setVisible(true);
		}

		/*
		 * Desenvolvedor: M�rio J�nior - Data: 23/10/2013 - Hor�rio: 16h00min - ID Citsmart: 122010 - Motivo/Coment�rio: Inser��o do t�tulo da solicita��o, informa��o do frame de solicita��o
		 */
		String tarefa = request.getParameter("idTarefa");
		String responsavelAtual = "";
		String tarefaAtual = "";

		if (solicitacaoServicoDto.getIdTarefa() != null || tarefa != null) {
			ItemTrabalho itemTrabalho = null;
			if (solicitacaoServicoDto.getIdTarefa() != null) {
				itemTrabalho = solicitacaoServicoService.getItemTrabalho(solicitacaoServicoDto.getIdTarefa());
			} else {
				Integer idTarefaStr = Integer.parseInt(tarefa);
				itemTrabalho = solicitacaoServicoService.getItemTrabalho(idTarefaStr);
			}
			if (itemTrabalho != null) {
				tarefaAtual = itemTrabalho.getElementoFluxoDto().getNome();
				if (itemTrabalho.getItemTrabalhoDto() != null) {
					if (itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual() != null) {
						UsuarioDTO usuarioDto = new UsuarioDTO();
						usuarioDto.setIdUsuario(itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual());
						usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
						if (usuarioDto != null) {
							EmpregadoDTO empDto = new EmpregadoDTO();
							empDto.setIdEmpregado(usuarioDto.getIdEmpregado());
							empDto = (EmpregadoDTO) empregadoService.restore(empDto);
							if (empDto != null) {
								responsavelAtual = empDto.getNome();
							}
						}
					}
				}
			}
		}

		document.executeScript("informaNumeroSolicitacao(\"" + solicitacaoServicoDto.getIdSolicitacaoServico() + "\", \"" + UtilStrings.nullToVazio(responsavelAtual) + "\", \""
				+ UtilStrings.nullToVazio(tarefaAtual) + "\")");
		document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");

		// Isto permite que nas classes herdadas, seja colocado o Bean no document.
		document.setBean(solicitacaoServicoDto);

		if (solicitacaoServicoDto.getObservacao() != null && !solicitacaoServicoDto.getObservacao().equals("")) {
			document.executeScript("setValorTextArea(\"#observacao\",'" + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getObservacao()) + "')");
		}
		if (editar != null && editar.equals("N")) {
			document.executeScript("desabilitaTextAreaWysi(\"#observacao\")");
		}
		if (solicitacaoServicoDto.getDescricao() != null && !solicitacaoServicoDto.getDescricao().equals("")) {
			document.executeScript("setValorTextArea(\"#descricao\",'" + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDescricao()) + "')");
		}
		if (editar != null && editar.equals("N")) {
			document.executeScript("desabilitaTextAreaWysi(\"#descricao\")");
		}
		if (solicitacaoServicoDto.getDetalhamentoCausa() != null && !solicitacaoServicoDto.getDetalhamentoCausa().equals("")) {
			document.executeScript("setValorTextArea(\"#detalhamentoCausa\",'" + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDetalhamentoCausa()) + "')");
		}
		if (editar != null && editar.equals("N")) {
			document.executeScript("desabilitaTextAreaWysi(\"#detalhamentoCausa\")");
		}
		if (solicitacaoServicoDto.getResposta() != null && !solicitacaoServicoDto.getResposta().equals("")) {
			document.executeScript("setValorTextArea(\"#resposta\",'" + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getResposta()) + "')");
		}
		if (editar != null && editar.equals("N")) {
			document.executeScript("desabilitaTextAreaWysi(\"#resposta\")");
		}
		if (solicitacaoServicoDto.getSolicitante().isEmpty()) {
			document.executeScript("validaCampoExecutanteNullparaVazio()");
		}
		/* document.executeScript("validarSolicitante()"); */
		if (editar != null && editar.equals("N")) {
			document.executeScript("document.getElementById('divBotoes').style.display = 'none';");
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> AT� AQUI

		if (solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("S");
		}
		if (solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
			// document.getElementById("tdListServicos").setVisible(false);
			// document.getElementById("tdLimparServicos").setVisible(false);
			document.getTextBoxById("servicoBusca").setDisabled(true);
		} else {
			if (solicitacaoServicoDto.getReclassificar() == null || solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("N")) {
				// document.getElementById("tdListServicos").setVisible(false);
				// document.getElementById("tdLimparServicos").setVisible(false);
				document.getTextBoxById("servicoBusca").setDisabled(true);
			}
		}

		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			this.renderizaHistoricoSolicitacoesEmAndamentoUsuario(document, request, response);
			this.carregaBaseConhecimentoAssoc(document, request, response);
		}
		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
			solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
			idGrupoAtual.removeAllOptions();
			idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			Collection colGrupos = grupoSegurancaService.listGruposServiceDeskByIdContrato(solicitacaoServicoDto.getIdContrato());
			if (colGrupos != null)
				idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
		}

		verificaGrupoExecutorInterno(document, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutorInterno(DocumentHTML document, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
		if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null)
			document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
		else
			document.getElementById("idGrupoAtual").setValue("");
	}

	public void carregarModalDuplicarSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		
		String situacao;
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getSituacao() != null) {
			situacao = solicitacaoServicoDto.getSituacao();
		} else {
			situacao = "";
		}
		
		Integer idGrupoAtual;
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null) {
			idGrupoAtual = solicitacaoServicoDto.getIdGrupoAtual();
		} else {
			idGrupoAtual = -1;
		}		

		solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);

		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

		servicoContratoDto.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

		servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);

		solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());

		this.preencherComboOrigem(document, request, response);

		this.carregaUnidade(document, request, response);

		document.getElementById("idContrato").setValue(solicitacaoServicoDto.getIdContrato().toString());

		preencherComboLocalidade(document, request, response);

		HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");		
		formSolicitacaoServico.setValues(solicitacaoServicoDto);
		
		/*
		 * Rodrigo Pecci Acorse - 03/12/2013 14h40 - #126139
		 * Ao setar o DTO da solicita��o de servi�os, a situa��o e grupo executor do filtro estava sendo preenchida indevidamente.
		 * As linhas abaixo preenchem os selects com os valores corretos.
		 */
		document.executeScript("$('#situacao').find('option[value=\"" + situacao + "\"]').attr(\"selected\",true);");
		document.executeScript("$('#idGrupoAtual').find('option[value=\"" + idGrupoAtual + "\"]').attr(\"selected\",true);");

		solicitacaoServicoDto = null;
		servicoContratoDto = null;

	}

	public void duplicarSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		SolicitacaoServicoDTO novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		UsuarioDTO usuarioDto = new UsuarioDTO();

		usuarioDto = WebUtil.getUsuario(request);

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoOrigem = new SolicitacaoServicoDTO();
		ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

		solicitacaoServicoOrigem.setIdSolicitacaoServico(novaSolicitacaoServicoDto.getIdSolicitacaoServico());

		solicitacaoServicoOrigem = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoOrigem);

		servicoContratoDto.setIdServicoContrato(solicitacaoServicoOrigem.getIdServicoContrato());

		servicoContratoDto = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDto);

		novaSolicitacaoServicoDto.setIdSolicitacaoServico(null);
		novaSolicitacaoServicoDto.setIdSolicitacaoPai(solicitacaoServicoOrigem.getIdSolicitacaoServico());
		novaSolicitacaoServicoDto.setIdContatoSolicitacaoServico(null);

		novaSolicitacaoServicoDto.setIdServico(servicoContratoDto.getIdServico());

		novaSolicitacaoServicoDto.setUsuarioDto(usuarioDto);
		novaSolicitacaoServicoDto.setDescricao(solicitacaoServicoOrigem.getDescricao());
		novaSolicitacaoServicoDto.setSituacao(solicitacaoServicoOrigem.getSituacao());
		novaSolicitacaoServicoDto.setRegistroexecucao("");
		novaSolicitacaoServicoDto.setEnviaEmailCriacao("S");

		novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(novaSolicitacaoServicoDto);

		document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.duplicadacomsucesso"));

		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		document.executeScript("parent.refreshTelaGerenciamento()");

		novaSolicitacaoServicoDto = null;
		solicitacaoServicoOrigem = null;
		servicoContratoDto = null;

	}

	public void restauraSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		if (empregadoDto != null) {
			solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
			solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
			solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
			solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
			this.preencherComboLocalidade(document, request, response);
		}

		UsuarioDTO usuarioDto = new UsuarioDTO();
		if(empregadoDto != null){
			usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
		}
		
		if (usuarioDto != null) {
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

			if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getItemConfiguracao());
				document.executeScript("exibeCampos()");
			}
		}

		HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
		formSolicitacaoServico.setValues(solicitacaoServicoDto);
		document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

		solicitacaoServicoDto = null;

	}

	public void restoreSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		if (empregadoDto != null) {
			solicitacaoServicoDto.setNomeSolicitante(empregadoDto.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
			solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
			solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
			solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
		}

		UsuarioDTO usuarioDto = new UsuarioDTO();
		if(empregadoDto != null){
			usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
		}
		
		if (usuarioDto != null) {
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

			if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getItemConfiguracao());
			}
		}

		HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
		formSolicitacaoServico.setValues(solicitacaoServicoDto);
		document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

		document.executeScript("setDataEditor()");

		// verifica se tem historico para mostrar botao de historico
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
		}

		resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null, null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
		}

		solicitacaoServicoDto = null;
	}

	private void carregaComboOrigem(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception, LogicException, RemoteException {
		OrigemAtendimentoService origemService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);

		HTMLSelect origem = (HTMLSelect) document.getSelectById("idOrigem");

		origem.removeAllOptions();
		inicializarCombo(origem, request);

		Collection listOrigem = origemService.list();

		if (listOrigem != null) {

			origem.addOptions(listOrigem, "idOrigem", "descricao", null);
		}
	}

	public void preenchePorEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		empregadoDTO = empregadoService.listEmpregadoContrato(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getEmailcontato());
		if (empregadoDTO != null) {
			document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
			document.getElementById("nomecontato").setValue(empregadoDTO.getNome());
			document.getElementById("telefonecontato").setValue(empregadoDTO.getTelefone());
			document.getElementById("idUnidade").setValue(Util.tratarAspasSimples(empregadoDTO.getIdUnidade().toString()));
			document.getElementById("solicitante").setValue(empregadoDTO.getNome());
			document.getElementById("idOrigem").setValue("3");
		}

		solicitacaoServicoDto = null;
		empregadoDTO = null;
	}

	public void calculaSLA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sla = "";
		try {
			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
			if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0)
				throw new LogicException("Contrato n�o encontrado");
			if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdServico().intValue() == 0)
				throw new LogicException("Servi�o n�o encontrado");

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			sla = solicitacaoServicoService.calculaSLA(solicitacaoServicoDto, request);

			if (sla.equals("")) {
				sla = "N/A";
			}

			document.executeScript("stopLoading()");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').style.display='block'");

		} catch (Exception e) {
			e.printStackTrace();
			document.executeScript("stopLoading()");
			if (sla.equals("")) {
				sla = "N/A";
			}
			document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
		}
	}

	public void atualizaGridProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDTO = new ProblemaDTO();

		problemaDTO.setIdProblema(bean.getIdProblema());

		problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		if (problemaDTO == null) {
			return;
		}
		HTMLTable tblProblema = document.getTableById("tblProblema");

		if (problemaDTO.getSequenciaProblema() == null) {
			tblProblema.addRow(problemaDTO, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idProblema" }, "Problema j� cadastrado!!", new String[] { "exibeIconesProblema" }, null,
					null);
			// tblProblema.addRow(problemaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema j� cadastrado!!", new String[] { "exibeIconesProblema"
			// },"buscaProblema", null);
		} else {
			tblProblema.updateRow(problemaDTO, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idProblema" }, "Problema j� cadastrado!!", new String[] { "exibeIconesProblema" },
					"buscaProblema", null, problemaDTO.getSequenciaProblema());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
		document.executeScript("fecharModalProblema();");

		bean = null;
	}

	public void atualizaGridMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();

		requisicaoMudancaDTO.setIdRequisicaoMudanca(bean.getIdRequisicaoMudanca());

		requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);

		HTMLTable tblMudanca = document.getTableById("tblMudanca");

		if (requisicaoMudancaDTO.getSequenciaMudanca() == null) {
			tblMudanca.addRow(requisicaoMudancaDTO, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idRequisicaoMudanca" }, "Mudan�a j� cadastrado!!",
					new String[] { "exibeIconesMudanca" }, null, null);
		} else {
			tblMudanca.updateRow(requisicaoMudancaDTO, new String[] { "numberAndTitulo", "status", "" }, new String[] { "idRequisicaoMudanca" }, "Mudan�a j� cadastrado!!",
					new String[] { "exibeIconesMudanca" }, "buscaMudanca", null, requisicaoMudancaDTO.getSequenciaMudanca());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
		document.executeScript("fecharModalMudanca();");

		bean = null;
	}

	public void atualizaGridBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();

		if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
			baseConhecimentoDTO.setIdBaseConhecimento(solicitacaoServicoDTO.getIdItemBaseConhecimento());
			baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);

			HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");

			if (baseConhecimentoDTO.getSequenciaBaseConhecimento() == null) {
				tblBaseConhecimento.addRow(baseConhecimentoDTO, new String[] { "idBaseConhecimento", "titulo", "" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null);
			} else {
				tblBaseConhecimento.updateRow(baseConhecimentoDTO, new String[] { "idBaseConhecimento", "titulo", "" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null,
						baseConhecimentoDTO.getSequenciaBaseConhecimento());
			}
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
			document.executeScript("fecharBaseConhecimento();");
		}
	}

	public void atualizaGridItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		ItemConfiguracaoService baseConhecimentoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
			itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDTO.getIdItemBaseConhecimento());
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) baseConhecimentoService.restore(itemConfiguracaoDTO);

			HTMLTable tblBaseConhecimento = document.getTableById("tblIC");

			if (itemConfiguracaoDTO.getSequenciaIC() == null) {
				tblBaseConhecimento.addRow(itemConfiguracaoDTO, new String[] { "idItemConfiguracao", "descricao", "", "" }, new String[] { "idItemConfiguracao" }, "Item Configura��o j� cadastrado!!",
						new String[] { "exibeIconesMudanca" }, "abreItemConfiguracao", null);

			} else {
				tblBaseConhecimento.updateRow(itemConfiguracaoDTO, new String[] { "idItemConfiguracao", "descricao", "", "" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null,
						itemConfiguracaoDTO.getSequenciaIC());
			}
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
			document.executeScript("fecharModalItemConfiguracao();");
		}
	}

	public void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	private void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		document.executeScript("document.getElementById('flagGrupo').value = 1;");
		document.executeScript("document.getElementById('divGrupoAtual').style.display = 'block';");
		document.executeScript("document.getElementById('divSituacao').style.display = 'block';");
		document.executeScript("document.getElementById('solucao').style.display = 'block';");
		document.executeScript("document.getElementById('divUrgencia').style.display = 'block';");
		document.executeScript("document.getElementById('divImpacto').style.display = 'block';");
		document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'block';");
		document.executeScript("document.getElementById('divProblema').style.display = 'block';");
		document.executeScript("document.getElementById('divMudanca').style.display = 'block';");
		document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'block';");
		document.executeScript("document.getElementById('col4').style.display = 'block';");
		// if (solicitacaoServicoDto.getIdSolicitacaoServico() != null)
		// document.executeScript("document.getElementById('divSolicitacaoRelacionada').style.display = 'block';");

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
				WebUtil.getUsuarioSistema(request));
		document.executeScript("exibirInformacoesComplementares(\"" + solicitacaoServicoService.getUrlInformacoesComplementares(solicitacaoServicoDto) + "\");");
		//Inclu�do, quando question�rio for recarregado.
		document.executeScript("incluiInfoComplSeQuestionario(\"" + solicitacaoServicoService.getUrlInformacoesComplementares(solicitacaoServicoDto) + "\");");
		TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateServico(solicitacaoServicoDto);
		if (templateDto != null) {
			if (templateDto.getScriptAposRecuperacao() != null && !StringUtils.isBlank(templateDto.getScriptAposRecuperacao()))
				document.executeScript(templateDto.getScriptAposRecuperacao());
			if (!templateDto.getHabilitaDirecionamento().equalsIgnoreCase("S")) {
				document.executeScript("document.getElementById('flagGrupo').value = 0;");
				document.executeScript("document.getElementById('divGrupoAtual').style.display = 'none';");
			}
			if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divSituacao').style.display = 'none';");
			if (!templateDto.getHabilitaSolucao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('solucao').style.display = 'none';");
			if (!templateDto.getHabilitaUrgenciaImpacto().equalsIgnoreCase("S")) {
				document.executeScript("document.getElementById('divUrgencia').style.display = 'none';");
				document.executeScript("document.getElementById('divImpacto').style.display = 'none';");
			}
			if (!templateDto.getHabilitaNotificacaoEmail().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'none';");
			if (!templateDto.getHabilitaProblema().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divProblema').style.display = 'none';");
			if (!templateDto.getHabilitaMudanca().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divMudanca').style.display = 'none';");
			if (!templateDto.getHabilitaItemConfiguracao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'none';");
			if (!templateDto.getHabilitaSolicitacaoRelacionada().equalsIgnoreCase("S"))
				if (request.getAttribute("tarefaAssociada") != null && !((String) request.getAttribute("tarefaAssociada")).equalsIgnoreCase("N")) {
					document.executeScript("document.getElementById('liIncidentesRelacionados').style.display = 'none';");
				}
			if (!templateDto.getHabilitaGravarEContinuar().equalsIgnoreCase("S") && solicitacaoServicoDto.getIdTarefa() != null)
				document.executeScript("document.getElementById('btnGravarEContinuar').style.display = 'none';");

			/*
			 * Desenvolvedor: Riubbe Oliveira - Data: 08/11/2013 - Hor�rio: 17:39 - ID Citsmart: 123538
			 * 
			 * Motivo/Coment�rio: Esta parte do codigo estava sendo comentada, isso nao deve ser feito. � nesse momento que � carregado a informa��o da altura do template, se estiver tendo problema
			 * com a altura da sua template entre no sistema e altera o tamanho por l�.
			 */
			if (templateDto.getAlturaDiv() != null)
				document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '" + templateDto.getAlturaDiv().intValue() + "px';");
			if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S") && !templateDto.getHabilitaSolucao().equalsIgnoreCase("S")) {
				document.executeScript("document.getElementById('col4').style.display = 'none';");
			}
		}
	}

	public void pesquisaBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
		baseConhecimentoDto.setTermoPesquisa(solicitacaoServicoDto.getDescricaoSemFormatacao());

		UsuarioDTO usuario = WebUtil.getUsuario(request);

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);

		List<BaseConhecimentoDTO> listaPesquisaBaseConhecimento = null;

		if ((baseConhecimentoDto.getTermoPesquisa() == null || baseConhecimentoDto.getTermoPesquisa().trim().equalsIgnoreCase("")) && baseConhecimentoDto.getIdUsuarioAutorPesquisa() == null
				&& baseConhecimentoDto.getIdUsuarioAprovadorPesquisa() == null && baseConhecimentoDto.getDataInicioPesquisa() == null && baseConhecimentoDto.getDataPublicacaoPesquisa() == null
				&& (baseConhecimentoDto.getTermoPesquisaNota() == null || baseConhecimentoDto.getTermoPesquisaNota().equalsIgnoreCase(""))) {

			document.alert("Informe um termo para pesquisa");

			return;

		} else {
			document.executeScript("$('#modal_pesquisaSolucaoBaseConhecimento').modal('show')");
			Lucene lucene = new Lucene();
			listaPesquisaBaseConhecimento = lucene.pesquisaBaseConhecimento(baseConhecimentoDto);
		}

		StringBuilder TabelaDados = new StringBuilder("<table>");

		CompararBaseConhecimentoGrauImportancia comparaGrauDeImportancia = new CompararBaseConhecimentoGrauImportancia();

		if (listaPesquisaBaseConhecimento != null && !listaPesquisaBaseConhecimento.isEmpty()) {

			for (BaseConhecimentoDTO baseConhecimentoDTO : listaPesquisaBaseConhecimento) {
				if (baseConhecimentoDTO != null && baseConhecimentoDTO.getIdPasta() != null) {
					if (baseConhecimentoDTO != null) {
						if (baseConhecimentoService.obterGrauDeImportanciaParaUsuario(baseConhecimentoDTO, usuario) != null) {
							Integer grauImportancia = baseConhecimentoService.obterGrauDeImportanciaParaUsuario(baseConhecimentoDTO, usuario);
							baseConhecimentoDTO.setGrauImportancia(grauImportancia);
						}
					}
				}
			}

			Collections.sort(listaPesquisaBaseConhecimento, comparaGrauDeImportancia);

			for (BaseConhecimentoDTO dto : listaPesquisaBaseConhecimento) {

				Integer importancia = dto.getGrauImportancia();

				String titulo = UtilStrings.nullToVazio(dto.getTitulo());

				titulo = titulo.replaceAll("\"", "");
				titulo = titulo.replaceAll("/", "");

				TabelaDados.append("<ul>");
				TabelaDados.append("<tr style='height: 25px !important;'>");
				TabelaDados.append("<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: Arial; width: 422px;'>");
				TabelaDados.append("<li>");
				// TabelaDados.append("<div>");

				TabelaDados.append("<a href='#' onclick='contadorClicks(" + dto.getIdBaseConhecimento() + ");abreVISBASECONHECIMENTO(" + dto.getIdBaseConhecimento() + ");'>");
				// TabelaDados.append("<a href='#' class='ui-icon-bullet' onclick='abreVISBASECONHECIMENTO(" + dto.getIdBaseConhecimento() + ");'>");

				TabelaDados.append(titulo + getGrauImportancia(request, importancia) + "</a>");
				// TabelaDados.append("</div>");
				TabelaDados.append("</li>");
				TabelaDados.append("</td>");
				TabelaDados.append("</tr>");
				TabelaDados.append("</ul>");
			}

		} else {
			TabelaDados.append("<tr style='height: 25px !important;'>");
			TabelaDados.append("<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: 'Arial'; width : 422px;'>");
			TabelaDados.append("<label> " + UtilI18N.internacionaliza(request, "citcorpore.comum.resultado") + "</label>");
			TabelaDados.append("</td>");
			TabelaDados.append("</tr>");
		}

		TabelaDados.append("</table>");

		document.getElementById("resultPesquisa").setInnerHTML(TabelaDados.toString());

		document.getElementById("modal_pesquisaSolucaoBaseConhecimento").setVisible(true);
	}

	public void contadorDeClicks(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		UsuarioDTO usuario = WebUtil.getUsuario(request);

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		baseConhecimentoDto.setIdBaseConhecimento(solicitacaoServicoDto.getIdBaseConhecimento());
		baseConhecimentoDto = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDto);

		ContadorAcessoDTO contadorAcessoDto = new ContadorAcessoDTO();
		ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);
		if (contadorAcessoDto.getIdContadorAcesso() == null) {
			contadorAcessoDto.setIdUsuario(usuario.getIdUsuario());
			contadorAcessoDto.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
			contadorAcessoDto.setDataHoraAcesso(UtilDatas.getDataHoraAtual());
			contadorAcessoDto.setContadorAcesso(1);
			if (contadorAcessoService.verificarDataHoraDoContadorDeAcesso(contadorAcessoDto)) {
				contadorAcessoService.create(contadorAcessoDto);

				// Avalia��o - M�dia da nota dada pelos usu�rios
				Double media = baseConhecimentoService.calcularNota(baseConhecimentoDto.getIdBaseConhecimento());
				if (media != null)
					baseConhecimentoDto.setMedia(media.toString());
				else
					baseConhecimentoDto.setMedia(null);
				contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);

				// Qtde de cliques
				Integer quantidadeDeCliques = contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDto);
				if (quantidadeDeCliques != null)
					baseConhecimentoDto.setContadorCliques(quantidadeDeCliques);
				else
					baseConhecimentoDto.setContadorCliques(0);

				Lucene lucene = new Lucene();
				lucene.indexarBaseConhecimento(baseConhecimentoDto);
			}
		}

	}

	public void listarServicosPorContratoDemandaCategoria(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		HTMLTable tblListaServicos = document.getTableById("tblListaServicos");
		tblListaServicos.deleteAllRows();

		Collection<ServicoDTO> colServicos = servicoService.findByIdTipoDemandaAndIdContrato(solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdContrato(),
				solicitacaoServicoDto.getIdCategoriaServico());
		if (colServicos != null && !colServicos.isEmpty()) {
			tblListaServicos.addRowsByCollection(colServicos, new String[] { "idServico", "nomeServico" }, null, null, null, "selecionarServico", null);
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	}

	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect selectOrigem = (HTMLSelect) document.getSelectById("idOrigem");
		selectOrigem.removeAllOptions();
		ArrayList<OrigemAtendimentoDTO> todasOrigens = (ArrayList) origemAtendimentoService.list();
		ArrayList<OrigemAtendimentoDTO> origensNaoExcluidas = new ArrayList<OrigemAtendimentoDTO>();
		String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO, "");

		selectOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (todasOrigens != null) {
			for (OrigemAtendimentoDTO origemAtendimento : todasOrigens) {
				if (origemAtendimento.getDataFim() == null) {
					origensNaoExcluidas.add(origemAtendimento);
				}
			}
			selectOrigem.addOptions(origensNaoExcluidas, "idOrigem", "descricao", origemPadrao);
		}
	}

	/**
	 * Preenche a combo Localidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboLocalidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		try {
			if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
				solicitacaoServicoDto = new SolicitacaoServicoServiceEjb().restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

		LocalidadeDTO localidadeDto = new LocalidadeDTO();

		Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

		Collection<LocalidadeDTO> listaIdlocalidade = null;

		String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

		HTMLSelect comboLocalidade = (HTMLSelect) document.getSelectById("idLocalidade");
		comboLocalidade.removeAllOptions();
		if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
			if (solicitacaoServicoDto.getIdUnidade() != null) {
				listaIdlocalidadePorUnidade = (ArrayList) localidadeUnidadeService.listaIdLocalidades(solicitacaoServicoDto.getIdUnidade());
			}
			if (listaIdlocalidadePorUnidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
					localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
				}

			}
		} else {
			listaIdlocalidade = (ArrayList) localidadeService.listLocalidade();
			if (listaIdlocalidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeDTO localidadeDTO : listaIdlocalidade) {
					localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), localidadeDto.getNomeLocalidade());
				}
			}

		}
		solicitacaoServicoDto = null;
	}

	/**
	 * @return the calcularDinamicamente
	 * @throws Exception
	 */
	public String getCalcularDinamicamente() throws Exception {
		calcularDinamicamente = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE, "N");
		return calcularDinamicamente.trim();
	}

	/**
	 * @return the prioridadeSolicitacoesService
	 * @throws Exception
	 * @throws ServiceException
	 */
	public PrioridadeSolicitacoesService getPrioridadeSolicitacoesService() throws ServiceException, Exception {
		if (prioridadeSolicitacoesService == null) {
			prioridadeSolicitacoesService = (PrioridadeSolicitacoesService) ServiceLocator.getInstance().getService(PrioridadeSolicitacoesService.class, null);
		}
		return prioridadeSolicitacoesService;
	}

	private void inicializarComboLocalidade(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	private void preparaTelaInclusao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// document.getElementById("btOcorrencias").setVisible(false);
		// document.getElementById("btIncidentesRelacionados").setVisible(false);
	}

	public void carregaBaseConhecimentoAssoc(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		document.getElementById("divScript").setInnerHTML(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
		if (solicitacaoServicoDto.getIdServico() == null)
			return;
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		ServicoDTO servicoDto = new ServicoDTO();
		servicoDto.setIdServico(solicitacaoServicoDto.getIdServico());
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
		if (servicoDto != null) {
			if (servicoDto.getIdBaseconhecimento() != null) {
				BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();
				baseConhecimentoDTO.setIdBaseConhecimento(servicoDto.getIdBaseconhecimento());
				baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);
				if (baseConhecimentoDTO != null) {
					document.getElementById("divScript").setInnerHTML(baseConhecimentoDTO.getConteudo());
					document.getElementById("countScript").setInnerHTML("1");
					document.executeScript("destaqueScript()");
				} else {
					document.getElementById("countScript").setInnerHTML("0");
					document.executeScript("$('#divMenuScript').removeClass('ui-state-highlight')");
				}
			} else {
				document.getElementById("countScript").setInnerHTML("0");
			}
		}

		solicitacaoServicoDto = null;
	}

	public void carregaContratos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idContrato");
		idContrato.removeAllOptions();

		if (solicitacaoServicoDto.getIdServico() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		Collection<ServicoContratoDTO> colContratos = servicoContratoService.findByIdServico(solicitacaoServicoDto.getIdServico());
		if (colContratos != null) {
			FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
			if (colContratos.size() > 1)
				idContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			else {
				ServicoContratoDTO servicoContratoDto = (ServicoContratoDTO) ((List) colContratos).get(0);
				solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());
				verificaGrupoExecutorInterno(document, solicitacaoServicoDto);
				verificaImpactoUrgencia(document, request, response);
			}
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			for (ServicoContratoDTO servicoContratoDto : colContratos) {
				if (servicoContratoDto.getDeleted() == null || servicoContratoDto.getDeleted().equalsIgnoreCase("N")) {
					ContratoDTO contratoDto = new ContratoDTO();
					contratoDto.setIdContrato(servicoContratoDto.getIdContrato());
					contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
					if (contratoDto != null) {
						if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("N")) {
							String id = contratoDto.getNumero();
							FornecedorDTO fornecedorDto = new FornecedorDTO();
							fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
							fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
							if (fornecedorDto != null)
								id += " - " + fornecedorDto.getRazaoSocial();
							idContrato.addOptionIfNotExists("" + contratoDto.getIdContrato(), id);
						}
					}
				}
			}
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Preenche a combo Software.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preecherComboSoftware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
		HTMLSelect comboSoftware = (HTMLSelect) document.getSelectById("idItemConfiguracaoPai");
		inicializarCombo(comboSoftware, request);
		valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
		for (ValorDTO valores : this.getListaCaracteristicaSoftware(valor, "SOFTWARES")) {
			comboSoftware.addOption(valores.getIdItemConfiguracao().toString(), valores.getValorStr());
		}

		solicitacaoServicoDto = null;
	}

	public Collection<ValorDTO> getListaCaracteristicaSoftware(ItemConfiguracaoDTO itemConfiguracao, String tagTipoItemConfiguracao) throws ServiceException, Exception {
		TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
		tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
		return this.getValorService().findByItemAndTipoItemConfiguracaoSofware(itemConfiguracao, tipoItemConfiguracao);

	}

	/**
	 * Retorna Service de Valor.
	 * 
	 * @return ValorService
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public ValorService getValorService() throws Exception {
		return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
	}

	/**
	 * Preenche a combo Hardware.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preecherComboHardware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
		HTMLSelect comboHardware = (HTMLSelect) document.getSelectById("idItemConfiguracaoFilho");
		Integer idItemAnterior = -9999;
		valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
		inicializarCombo(comboHardware, request);
		for (ValorDTO valores : this.getListaCaracteristica(valor, "HARDWARE")) {
			if (idItemAnterior.intValue() != valores.getIdItemConfiguracao().intValue()) {
				comboHardware.addOption(valores.getIdItemConfiguracao().toString(), valores.getTagtipoitemconfiguracao() + " - Id: " + valores.getIdItemConfiguracao());
			}
			idItemAnterior = valores.getIdItemConfiguracao();
			comboHardware.addOption(valores.getIdItemConfiguracao().toString(), valores.getNomeCaracteristica() + " - " + valores.getValorStr());
		}
		// document.executeScript("addCorCombo();");

		solicitacaoServicoDto = null;

	}

	public String listInfoRegExecucaoSolicitacao(Collection col, HttpServletRequest request) throws ServiceException, Exception {
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();

		StringBuilder strBuffer = new StringBuilder();
		strBuffer.append("<table width='100%' border='1'>");
		strBuffer.append("<tr>");
		strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
		strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
		strBuffer.append("</td>");
		strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
		strBuffer.append(UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao"));
		strBuffer.append("</td>");
		strBuffer.append("</tr>");

		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = (OcorrenciaSolicitacaoDTO) it.next();

				if (ocorrenciaSolicitacaoAux.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaSolicitacaoAux.getOcorrencia());
					ocorrenciaSolicitacaoAux.setOcorrencia(source.getTextExtractor().toString());
				}

				if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null && categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != 0) {
					categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia());
					categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
				}

				if (origemOcorrenciaDTO.getIdOrigemOcorrencia() != null && origemOcorrenciaDTO.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia());
					origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
				}

				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getOcorrencia());
				String descricao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDescricao());
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getInformacoesContato());
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
				strBuffer.append("<tr>");
				strBuffer.append("<td style='border:1px solid black'>");
				strBuffer.append("<b>" + UtilDatas.dateToSTR(ocorrenciaSolicitacaoAux.getDataregistro()) + " - " + ocorrenciaSolicitacaoAux.getHoraregistro());

				String strRegPor = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getRegistradopor());
				try {
					if (ocorrenciaSolicitacaoAux.getRegistradopor() != null && !ocorrenciaSolicitacaoAux.getRegistradopor().trim().equalsIgnoreCase("Autom�tico")) {
						UsuarioDTO usuarioDto = usuarioService.restoreByLogin(ocorrenciaSolicitacaoAux.getRegistradopor());
						if (usuarioDto != null) {
							EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
							strRegPor = strRegPor + " - " + empregadoDto.getNome();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				strBuffer.append(" - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + strRegPor + "</b>");
				strBuffer.append("</td>");
				strBuffer.append("<td style='border:1px solid black'>");
				strBuffer.append("<b>" + ocorrenciaSolicitacaoAux.getDescricao() + "<br><br></b>");
				strBuffer.append("<b>" + ocorrencia + "<br><br></b>");

				/*
				 * if (ocorrenciaSolicitacaoAux.getCategoria().equalsIgnoreCase( Enumerados.CategoriaOcorrencia.Suspensao.toString() ) || ocorrenciaSolicitacaoAux
				 * .getCategoria().equalsIgnoreCase(Enumerados .CategoriaOcorrencia.MudancaSLA.toString() ) ) { JustificativaSolicitacaoDTO justificativaSolicitacaoDTO = new
				 * JustificativaSolicitacaoDTO(); if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) { justificativaSolicitacaoDTO .setIdJustificativa(ocorrenciaSolicitacaoAux
				 * .getIdJustificativa() ); justificativaSolicitacaoDTO = (JustificativaSolicitacaoDTO) justificativaSolicitacaoService. restore(justificativaSolicitacaoDTO); if
				 * (justificativaSolicitacaoDTO != null) { strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" +
				 * justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>"; } } if (!UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux
				 * .getComplementoJustificativa()).trim().equalsIgnoreCase("") ) { strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux .getComplementoJustificativa()) +
				 * "<br><br></b>"; } }
				 */
				if (ocorrenciaSolicitacaoAux.getCategoria() != null && !ocorrenciaSolicitacaoAux.getCategoria().equals("")) {
					if (ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
							|| ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())
							|| ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.SuspensaoSLA.toString())) {
						JustificativaSolicitacaoDTO justificativaSolicitacaoDTO = new JustificativaSolicitacaoDTO();
						if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) {
							justificativaSolicitacaoDTO.setIdJustificativa(ocorrenciaSolicitacaoAux.getIdJustificativa());
							justificativaSolicitacaoDTO = (JustificativaSolicitacaoDTO) justificativaSolicitacaoService.restore(justificativaSolicitacaoDTO);
							if (justificativaSolicitacaoDTO != null) {
								strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoJustificativa()
										+ "<br><br></b>");
							}
						}
						if (!UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
							strBuffer.append("<b>" + UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa()) + "<br><br></b>");
						}
					}
				}
				strBuffer.append("</td>");
				strBuffer.append("</tr>");
			}
		}
		strBuffer.append("</table>");

		categoriaOcorrenciaDTO = null;
		origemOcorrenciaDTO = null;

		return strBuffer.toString();
	}

	private ProblemaService getProblemaService() throws ServiceException, Exception {
		if (problemaService == null) {
			problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		}
		return problemaService;
	}

	private RequisicaoMudancaService getRequisicaoMudancaService() throws ServiceException, Exception {
		if (requisicaoMudancaService == null) {
			requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		}
		return requisicaoMudancaService;
	}

	private ConhecimentoSolicitacaoService getConhecimentoSolicitacaoService() throws ServiceException, Exception {
		if (conhecimentoSolicitacaoService == null) {
			conhecimentoSolicitacaoService = (ConhecimentoSolicitacaoService) ServiceLocator.getInstance().getService(ConhecimentoSolicitacaoService.class, null);
		}
		return conhecimentoSolicitacaoService;
	}

	public void abrirListaDeSubSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		String html = "";
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);
			Collection colFinal = new ArrayList();
			if (solicitacaoServicoAux != null) {
				if (solicitacaoServicoAux.getIdSolicitacaoRelacionada() != null) {
					solicitacaoServicoAux.setIdSolicitacaoServico(solicitacaoServicoAux.getIdSolicitacaoRelacionada());
					SolicitacaoServicoDTO solicitacaoServicoAux2 = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoAux);
					if (solicitacaoServicoAux2 != null) {
						colFinal.add(solicitacaoServicoAux2);
					}
				}
			}

			Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService.listSolicitacaoServicoRelacionadaPai(solicitacaoServicoDto.getIdSolicitacaoServico());
			if (solicitacoesRelacionadas != null) {
				colFinal.addAll(solicitacoesRelacionadas);
			}

			StringBuffer script = new StringBuffer();

			html = this.gerarHtmlComListaSubSolicitacoes(colFinal, script, request);

			document.getElementById("solicitacaoRelacionada").setInnerHTML(html);
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Retorna Grau de Import�ncia.
	 * 
	 * @param request
	 * @param importancia
	 * @return String
	 * @author Vadoilo Damasceno
	 */
	public String getGrauImportancia(HttpServletRequest request, Integer importancia) {
		if (importancia != null) {
			if (importancia == 1) {
				return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.baixo");
			} else {
				if (importancia == 2) {
					return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.medio");
				} else {
					if (importancia == 3) {
						return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.alto");
					}
				}
			}
		}
		return "";
	}

	private String gerarHtmlComListaSubSolicitacoes(Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, StringBuffer script, HttpServletRequest request) {
		StringBuffer html = new StringBuffer();

		html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</th>");
		html.append("</tr>");

		if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
				html.append("<tr>");
				html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante() + "'/>");
				html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel() + "'/>");
				html.append("<td style='text-align: center;'>" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(solicitacaoServicoRelacionada.getDataHoraSolicitacao()) + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":" + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getDescricao() + "</td>");
				html.append("<td>" + (solicitacaoServicoRelacionada.getResposta() != null ? solicitacaoServicoRelacionada.getResposta() : "-") + "</td>");
				if(solicitacaoServicoRelacionada.getSituacao().equals("EmAndamento")){
					html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
				} else if (solicitacaoServicoRelacionada.getSituacao().equals("Suspensa")){
					html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa") + "</td>");
				} else if (solicitacaoServicoRelacionada.getSituacao().equals("Cancelada")){
					html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada") + "</td>");
				}  else if (solicitacaoServicoRelacionada.getSituacao().equals("Resolvida")){
					html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida") + "</td>");
				} else {
					html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
				}
					
				if (solicitacaoServicoRelacionada.getDataHoraLimite() != null)
					html.append("<td>" + UtilDatas.formatTimestamp(solicitacaoServicoRelacionada.getDataHoraLimite()) + "</td>");
				else
					html.append("<td>� combinar</td>");

				html.append("</tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	/**
	 * Retorna lista de caracter�sticas.
	 * 
	 * @param idItemConfiguracao
	 * @param tagTipoItemConfiguracao
	 * @return listaCaracteristica
	 * @throws ServiceException
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public Collection<ValorDTO> getListaCaracteristica(ItemConfiguracaoDTO itemConfiguracao, String tagTipoItemConfiguracao) throws ServiceException, Exception {
		TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
		tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
		return this.getValorService().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);

	}

	/**
	 * Retorna o service de empregado para buscar a unidade do solicitante
	 * 
	 * @author rodrigo.oliveira
	 */
	public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		return (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	}

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	public void marcarChecksEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getCheckboxById("enviaEmailCriacao").setChecked(false);
		document.getCheckboxById("enviaEmailAcoes").setChecked(false);
		document.getCheckboxById("enviaEmailFinalizacao").setChecked(false);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		GrupoDTO grupoDTO = new GrupoDTO();
		if(solicitacaoServicoDto != null){
			grupoDTO.setIdGrupo(solicitacaoServicoDto.getIdGrupoAtual());
		}
		
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null && !solicitacaoServicoDto.getIdGrupoAtual().equals("")) {
			grupoDTO = (GrupoDTO) grupoService.restore(grupoDTO);
		}

		if (grupoDTO.getAbertura() != null) {
			if (grupoDTO.getAbertura().equalsIgnoreCase("S")) {
				document.getCheckboxById("enviaEmailCriacao").setValue("S");
			}
		}
		if (grupoDTO.getAndamento() != null) {
			if (grupoDTO.getAndamento().equalsIgnoreCase("S")) {
				document.getCheckboxById("enviaEmailAcoes").setValue("S");
			}
		}
		if (grupoDTO.getEncerramento() != null) {
			if (grupoDTO.getEncerramento().equalsIgnoreCase("S")) {
				document.getCheckboxById("enviaEmailFinalizacao").setValue("S");
			}
		}
	}

	/**
	 * Cria Combo de Categoria Servi�o Ativas.
	 * 
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 * @throws LogicException
	 * @throws RemoteException
	 */
	public void criarComboCategoriaServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.executeScript("document.getElementById(\"divCategoriaServico\").style.display = 'block'");
		// document.executeScript("$(\"#divCategoriaServico\").load();");

		CategoriaServicoService categoriaService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
		HTMLSelect idCategoriaServico = (HTMLSelect) document.getSelectById("idCategoriaServico");
		idCategoriaServico.removeAllOptions();
		idCategoriaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection listaDeCategoriasAtivas = categoriaService.listHierarquia();
		if (listaDeCategoriasAtivas != null) {
			idCategoriaServico.addOptions(listaDeCategoriasAtivas, "idCategoriaServico", "nomeCategoriaServicoNivel", null);
		}

		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}

	/**
	 * Restaura Colaborador selecionado como Solicitante, obtendo e atribuindo informa��es de Contato, Item de Configura��o e Hist�rico de Solicita��es.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restoreColaboradorSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		// CITCorporeUtil.limparFormulario(document);
		if (empregadoDto != null) {
			solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
			solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
			solicitacaoServicoDto.setRamal(empregadoDto.getRamal());
			solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail().trim());
			solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
			solicitacaoServicoDto.setRamal(empregadoDto.getRamal());

			this.preencherComboLocalidade(document, request, response);
		}

		UsuarioDTO usuarioDto = new UsuarioDTO();

		if (empregadoDto != null && empregadoDto.getIdEmpregado() != null) {
			usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
		}

		if (usuarioDto != null) {
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

			if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getItemConfiguracao());
				/* document.executeScript("exibeCampos()"); */
			}
		}

		HTMLForm form = document.getForm("form");

		document.executeScript("setDataEditor()");

		form.setValues(solicitacaoServicoDto);

		document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
		}

		resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null, null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
		}

		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

		if (!UtilStrings.isNotVazio(controleAccUnidade)) {
			controleAccUnidade = "N";
		}

		if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
			carregaServicos(document, request, response);
		}

		document.executeScript("camposObrigatoriosSolicitante()");

		solicitacaoServicoDto = null;
	}

	public void restoreItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		itemConfiguracaoDTO.setIdItemConfiguracao(bean.getIdItemConfiguracao());
		itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);

		HTMLTable tblIC = document.getTableById("tblIC");

		if (itemConfiguracaoDTO.getSequenciaIC() == null) {
			tblIC.addRow(itemConfiguracaoDTO, new String[] { "idItemConfiguracao", "identificacao", "", "" }, new String[] { "idItemConfiguracao" }, "Item Configura��o j� cadastrado!!",
					new String[] { "exibeIconesIC" }, null, null);
		} else {
			tblIC.updateRow(itemConfiguracaoDTO, new String[] { "idItemConfiguracao", "identificacaoStatus", "", "" }, new String[] { "idItemConfiguracao" }, "Item Configura��o j� cadastrado!!",
					new String[] { "exibeIconesIC" }, null, null, itemConfiguracaoDTO.getSequenciaIC());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");

		document.executeScript("$('#modal_pesquisaItemConfiguracao').modal('hide');");

		/* document.executeScript("fecharM();"); */

		// metodo para setar urgencia e impacto de acordo com o item de configura��o
		int prioridade = 0;
		int prioridadeObj = 0;
		List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize", request);
		if (colItensIC == null) {
			colItensIC = new ArrayList<ItemConfiguracaoDTO>();
		}
		colItensIC.add(itemConfiguracaoDTO);
		if (colItensIC != null) {
			for (ItemConfiguracaoDTO itemConfiguracaoDTO2 : colItensIC) {
				itemConfiguracaoDTO2 = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO2);
				if (itemConfiguracaoDTO2.getUrgencia() != null && itemConfiguracaoDTO2.getImpacto() != null) {
					if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 1;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 2;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 2;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 4;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 4;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 5;
					}
					if (prioridadeObj > prioridade) {
						prioridade = prioridadeObj;
						document.getSelectById("urgencia").setValue(itemConfiguracaoDTO2.getUrgencia());
						document.getSelectById("impacto").setValue(itemConfiguracaoDTO2.getImpacto());
					}
				}
			}
		}
		// fim metodo urgencia e impacto

		bean = null;
	}

	/**
	 * @author breno.guimaraes
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void renderizaHistoricoSolicitacoesIncidente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idIc = null;
		if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
			idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @author breno.guimaraes
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void renderizaHistoricoSolicitacoesIC(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idIc = null;
		if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
			idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void renderizaHistoricoSolicitacoesUsuario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idSolicitante = null;
		if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante, null);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void renderizaHistoricoSolicitacoesEmAndamentoUsuario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idSolicitante = null;

		String ehRestauracaoSolicitacao = "";
		String ehCriacaoSolicitacao = "";

		if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
			ehCriacaoSolicitacao = "true";
		} else {
			ehRestauracaoSolicitacao = "true";
		}

		if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}

		if (ehRestauracaoSolicitacao.equalsIgnoreCase("true")) {
			idSolicitante = ((SolicitacaoServicoDTO) document.getBean()).getIdSolicitante();
		}

		String situacao = request.getParameter("situacaoFiltroSolicitante");
		String campoBusca = request.getParameter("buscaFiltroSolicitante");

		/*
		 * if (situacao == null || situacao.isEmpty()) { situacao = "EmAndamento"; } else { situacao = situacao.trim(); }
		 */

		String situacaoFiltro = "";

		if (situacao != null) {
			if (situacao.equalsIgnoreCase("EmAndamento") || situacao == null || situacao.isEmpty()) {
				situacaoFiltro = "EmAndamento";
				situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento");
			} else if (situacao.equalsIgnoreCase("Cancelada")) {
				situacaoFiltro = "Cancelada";
				situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada");
			} else if (situacao.equalsIgnoreCase("Suspensa")) {
				situacaoFiltro = "Suspensa";
				situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa");
			} else {
				situacao = situacao.trim();
				situacaoFiltro = situacao;
			}
		}

		if (campoBusca != null) {
			campoBusca = UtilStrings.decodeCaracteresEspeciais(campoBusca);
			campoBusca = campoBusca.trim();
		}

		StringBuffer script = new StringBuffer();
		StringBuffer filtro = new StringBuffer();

		/*
		 * filtro.append("<div class='clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all' style='padding-top: 10px; margin-bottom: 5px;'>");
		 * filtro.append("   <div style='float: left;  width: 75%;' class='space'>"); filtro.append("      <table cellspacing='3' cellpadding='0' style='float: left; height:100%;'>");
		 * filtro.append("         <tbody>"); filtro.append("            <tr>"); filtro.append("               <td style='vertical-align: middle; padding-left: 10px;'>");
		 * filtro.append("                  " + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + ":"); filtro.append("               </td>");
		 * filtro.append("               <td style='vertical-align: middle; padding-left: 10px;' colspan='2'>"); filtro.append("                  " + UtilI18N.internacionaliza(request,
		 * "citcorpore.comum.busca") + ":"); filtro.append("               </td>"); filtro.append("            </tr>"); filtro.append("            <tr>");
		 * filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>"); filtro.append("			 		<select name='situacaoTblResumo2' id='situacaoTblResumo2'>");
		 * filtro.append("			 			<option value='EmAndamento' " + ("EmAndamento".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento") +
		 * "</option>"); filtro.append("			 			<option value='Fechada' " + ("Fechada".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.fechada") +
		 * "</option>"); filtro.append("			   		</select>"); filtro.append("                </td>"); filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		 * filtro.append
		 * ("                  <input type='text' style='border:1px solid #B3B3B3;height:32px;' maxlength='256' size='25' id='campoBuscaTblResumo2' name='campoBuscaTblResumo2' class='text' ");
		 * filtro.append((campoBusca != null ? "value='" + campoBusca + "'" : "") + ">"); filtro.append("               </td> ");
		 * filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>"); filtro.append("		 			 <button id='' type='button' class='light img_icon has_text' onclick='");
		 * filtro.append("inicializarTemporizador();"); filtro.append("document.form.situacaoFiltroSolicitante.value = document.getElementById(\"situacaoTblResumo2\").value;");
		 * filtro.append("document.form.buscaFiltroSolicitante.value = document.getElementById(\"campoBuscaTblResumo2\").value;");
		 * filtro.append("document.form.fireEvent(\"renderizaHistoricoSolicitacoesEmAndamentoUsuario\");'>");
		 * filtro.append("		 				<img src='/citsmart/template_new/images/icons/small/grey/magnifying_glass.png'>"); filtro.append("		 				<span>" + UtilI18N.internacionaliza(request,
		 * "citcorpore.comum.pesquisar") + "</span>"); filtro.append("					 </button>"); filtro.append("               </td>"); filtro.append("            </tr>"); filtro.append("         </tbody>");
		 * filtro.append("      </table>"); filtro.append("   </div>"); filtro.append("</div>");
		 */

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante, situacaoFiltro, campoBusca);
		String corpoHTML = "";

		if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
			corpoHTML = montaHTMLResumoSolicitacoes(resumo, script, request, "true");
		} else {
			corpoHTML = montaHTMLResumoSolicitacoes(resumo, script, request, "false");
		}
		if (resumo.size() > 0) {

			document.getElementById("countSolicitacoesAbertasSolicitante").setInnerHTML("" + resumo.size());
			document.executeScript("destaqueSolicitacaoMesmoUsuario()");
		} else {
			document.getElementById("countSolicitacoesAbertasSolicitante").setInnerHTML("0");
			document.executeScript("$('#divMenuSolicitacao').removeClass('ui-state-highlight');");
		}
		filtro.append(corpoHTML);

		document.getElementById("tblResumo2").setInnerHTML(filtro.toString());

		document.executeScript(script.toString());
		if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
			document.executeScript("temporizador.init();");
		}

		// document.executeScript("$(\"#tblResumo2\").dialog(\"open\");");
	}

	/**
	 * @param resumo
	 *            Lista de solicitações que será montada.
	 * @param script
	 *            A string que será alimentada por referência para ser executada posteriormente.
	 * @return
	 * @author breno.guimaraes
	 */
	private String montaHTMLResumoSolicitacoes(ArrayList<SolicitacaoServicoDTO> resumo, StringBuffer script, HttpServletRequest request, String utilizaTemporizador) {
		StringBuffer html = new StringBuffer();

		html.append("<div style='overflow:auto'>");
		html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisa.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
		/*
		 * html.append("<img width='20' height='20'"); html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") +
		 * "' id='imgAtivaTimer' style='opacity:0.5; cursor:pointer;' "); html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
		 * html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
		 */
		html.append("</th>");
		html.append("</tr>");
		for (SolicitacaoServicoDTO r : resumo) {
			html.append("<tr class='trSolicitacaoUsuario' onclick=\"detalheSolicitacao('" + r.getContrato() + "#" + r.getNomecontato() + "#" + r.getEmailcontato() + "#" + r.getTelefonecontato() + "#"
					+ r.getDemanda() + "#" + r.getServico() + "#" + r.getSituacao()
					+ "')\" style=\"cursor:default\" onMouseOver=\"javascript:this.style.backgroundColor='#CFCFCF'\" onMouseOut=\"javascript:this.style.backgroundColor=''\" >");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
			html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
			html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "</td>");
			html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
			html.append("<td>" + r.getDescricao() + "</td>");
			html.append("<td>" + (r.getResposta() != null && !r.getResposta().equals("") ? r.getResposta() : "-") + "</td>");
			if (r.getSituacao().equalsIgnoreCase("EmAndamento")) {
				html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento") + "</td>");
			} else if (r.getSituacao().equalsIgnoreCase("Fechada")) {
				html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada") + "</td>");
			} else {
				html.append("<td>" + r.getSituacao() + "</td>");
			}
			if (r.getDataHoraLimite() != null) {
				html.append("<td>" + UtilDatas.formatTimestamp(r.getDataHoraLimite()) + "</td>");
				if (r.getSituacao().equals("EmAndamento") && utilizaTemporizador.equalsIgnoreCase("true")) {
					script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico() + "', " + "'"
							+ r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
				}
				html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
				html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
			} else {
				html.append("<td>&nbsp;</td>");
				html.append("<td>&nbsp;</td>");
			}
			html.append("</tr>");
		}
		html.append("</table>");
		html.append("</div>");

		return html.toString();
	}

	public void listHistorico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		Collection col = solicitacaoService.getHistoricoByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("<table width='100%'>");
		strBuilder.append("<tr>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.responsavel"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.acao"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "tarefa.tarefa"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidogrupo"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidousuario"));
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) it.next();
				strBuilder.append("<tr>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilDatas.dateToSTR(solicitacaoServicoAux.getDataHora()) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(solicitacaoServicoAux.getDataHora()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				if (solicitacaoServicoAux.getSeqReabertura() == null) {
					strBuilder.append("--");
				} else {
					strBuilder.append(solicitacaoServicoAux.getSeqReabertura());
				}
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getResponsavel()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getAcaoFluxo()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getTarefa()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getSiglaGrupo()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getNomeUsuario()));
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
		}
		strBuilder.append("</table>");
		document.getElementById("divResultHistorico").setInnerHTML(strBuilder.toString());

		solicitacaoServicoDto = null;
	}

	public void gravarAnexo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
		document.getElementById("contatdorAnexo").setValue("" + arquivosUpados.size());
		// Rotina para gravar no banco
		if (solicitacaoServicoDto.getColArquivosUpload() != null && solicitacaoServicoDto.getColArquivosUpload().size() > 0) {
			Integer idEmpresa = WebUtil.getIdEmpresa(request);
			if (idEmpresa == null)
				idEmpresa = 1;
			solicitacaoServicoService.gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), idEmpresa, solicitacaoServicoDto, null);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			document.executeScript("('#POPUP_menuAnexos').dialog('close');");
		}

		solicitacaoServicoDto = null;
	}

	public Integer carregarProblema(Integer indice, Integer id) throws ServiceException, br.com.citframework.excecao.LogicException, Exception {
		ProblemaDTO problemadto = new ProblemaDTO();
		problemadto.setIdSolicitacaoServico(id);
		if (id != null) {
			Collection col = this.getProblemaService().findBySolictacaoServico(problemadto);
			if (col == null) {
				return null;
			}
			problemadto = (ProblemaDTO) ((List) col).get(indice);
			if (problemadto == null) {
				return null;
			}
		}
		return problemadto.getIdProblema();
	}

	public Integer obterGrauDeImportanciaParaUsuario(BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto) throws Exception {

		ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
				ImportanciaConhecimentoGrupoService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		Collection<GrupoEmpregadoDTO> listGrupoEmpregadoDto = grupoEmpregadoService.findByIdEmpregado(usuarioDto.getIdEmpregado());

		ImportanciaConhecimentoGrupoDTO importanciaConhecimento = importanciaConhecimentoGrupoService.obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregadoDto, usuarioDto);

		if (importanciaConhecimento != null) {
			return Integer.parseInt(importanciaConhecimento.getGrauImportancia());
		} else {
			return 0;
		}
	}

	/**
	 * Adicionado para fazer limpeza da se��o quando for gerenciamento de Servi�o
	 * 
	 * @author mario.junior
	 * @since 31/10/2013 09:36
	 */
	public void carregaFlagGerenciamento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("flagGerenciamento", "S");
	}

	public void flagGerenciamentoClose(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession(true).setAttribute("flagGerenciamento", null);
	}

}
