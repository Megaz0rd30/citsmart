<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>
<%@page import="br.com.centralit.citcorpore.util.UtilI18N"%>
<%
Collection listaServicos = (Collection)request.getAttribute("listaServicos");
String info = (String)request.getAttribute("info");;
%>
<!doctype html>
<html>
<head>
	<%
		response.setHeader( "Cache-Control", "no-cache");
		response.setHeader( "Pragma", "no-cache");
		response.setDateHeader ( "Expires", -1);
		
		//identifica se a p�gina foi aberta a partir de um iframe (popup de cadastro r�pido)
		String iframe = "";
		iframe = request.getParameter("iframe");
		
	%>
	<%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	
	<style type="text/css">
	
		td{
			border: 1px solid #f5f5f5;
			cursor: pointer;
			padding: 0.5em;
		 	font-weight: bold;
		 	font-family: arial;
		 	font-size: 12px;
		 	background:#f2f2f2; 
		}
		
		.linhaSubtituloGrid{
			padding: 0px;
			font-size:13px;
		    box-shadow: 0 0 2px 0 #DDDDDD inset;
		    margin-top: 3px;
		    background-color: #F3F3F3;
		    border: 1px solid #B3B3B3;
		}
	
	</style>
	
	<script type="text/javascript">
		aguarde = function(){
			JANELA_AGUARDE_MENU.show();
		}
		
		fechar_aguarde = function(){
	    	JANELA_AGUARDE_MENU.hide();
		}
				
		function pesquisa(){
			
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			
			if (document.form.validate()){
				if(!validaData(dataInicio, dataFim)){
					return;
				}
				aguarde();
				document.form.action = document.form.action + '.load'; 
				document.form.submit();
			}
		}
		
		
		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {
			
			var dtInicio = new Date();
			var dtFim = new Date();
			
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			
			if (dtInicio > dtFim){
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
				return false;
			}else
				return true;
		}
		
	</script>
	<%
	//se for chamado por iframe deixa apenas a parte de cadastro da p�gina
	if(iframe != null){%>
		<style type="text/css">
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
		</style>
	<%}%>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if(iframe == null){%>
			<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%if(iframe == null){%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
		<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/avaliacaoContrato/avaliacaoContrato' method="post">
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="avaliacaocontrato.avaliacaocontrato"/>
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<fieldset>
					<legend><i18n:message key="citcorpore.comum.filtros"/></legend>
					<table>
						<tr>
							<td>
								<label class='campoObrigatorio'><i18n:message key="avaliacaocontrato.contrato"/></label>
							</td>
							<td colspan="4">
								<select name="idContrato" id="idContrato" class="Valid[Required] Description[<i18n:message key="avaliacaocontrato.contrato"/>]"></select>
							</td>
						</tr>
						<tr>
							<td>
								<label class='campoObrigatorio'><i18n:message key="avaliacaocontrato.periodo"/></label>
							</td>
							<td>
								<input type='text' name='dataInicio' id="dataInicio" size="10" maxlength="10" class="Valid[Required,Date] Description[citcorpore.comum.validacao.datainicio] Format[Date] datepicker" />									
							</td>
							<td>
								<input type='text' name='dataFim'  id="dataFim" size="10" maxlength="10" class="Valid[Required,Date] Description[citcorpore.comum.datafim] Format[Date] datepicker" />									
							</td>
							<td>
								&nbsp;
							</td>
							<td style='vertical-align: top;'>
								<button type="button" onclick='pesquisa()'>
									<i18n:message key="citcorpore.comum.gerarInformacoes"/>
								</button>
							</td>							
						</tr>
					</table>
				</fieldset>				
				<div id="divInfo">
				<%
				if (info != null){
					out.println(info);
				}
				%>
				</div>
				
<div>	
<i18n:message key="avaliacaoContrato.avaliacaoContratoDesempenhoServico"/>			
<table cellpadding="0" cellspacing="0" width="100%" style='width: 98%'>
	<tr style='text-align: center;' class=''>
		<td class="linhaSubtituloGrid">
			&nbsp;
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 20px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="citcorpore.comum.servico"/></b>
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="citcorpore.comum.situacao"/></b>
		</td>		
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="avaliacao.fornecedor.servico.demanda"/></b>
		</td>	
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="avaliacao.fornecedor.sla"/></b>
		</td>			
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="avaliacao.fornecedor.dataInicio"/></b>
		</td>
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="avaliacao.fornecedor.dataFim"/></b>
		</td>	
		<td class="linhaSubtituloGrid">
			<b style='line-height: 25px;font-size:13px; padding:0.5em;font-family: arial;'><i18n:message key="avaliacaoContrato.quantidadeRequisicoesIncidentes"/></b>
		</td>					
	</tr>
	<%
	String corLinha = "";
	if (listaServicos != null && listaServicos.size() > 0){
		for(Iterator it = listaServicos.iterator(); it.hasNext();){
			if (!corLinha.trim().equalsIgnoreCase("#f5f5f5")){
				corLinha = "#f5f5f5";
			}else{
				corLinha = "white";
			}
			ServicoContratoDTO servicoContratoAux = (ServicoContratoDTO)it.next();
			out.print("<tr style='border: none;background:" + corLinha + ";'>");
			
				out.print("<td style='padding:0.2em; text-align: center;' align='center'>");
			/*	
					out.print("<table style='text-align: center; align='center''>");
					out.print("<tr style='text-align: center;'>");
					out.print("<td style='text-align: center;' align='center'>");
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/write.png' border='0' style='cursor:pointer' title='Editar o servi�o' onclick='editarServicoContrato(" + servicoContratoAux.getIdServicoContrato() + ")'/>");					
					out.print("</td>");					
					out.print("</tr>");
					out.print("</table>");
					
				*/
				out.print("</td>");
				out.print("<td>");
					out.print(servicoContratoAux.getNomeServico());
				out.print("</td>");
				out.print("<td>");
				if (servicoContratoAux.getSituacaoServico().intValue() == 1){
					if (servicoContratoAux.getDataFim() != null && servicoContratoAux.getDataFim().before(UtilDatas.getDataAtual())){
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='Servi�o Inativo'/>");					
						out.print(""+UtilI18N.internacionaliza(request, "avalicaoContrato.Inativo(Datafim)")+"");						
					}else{
						out.print("<img src='" + br.com.citframework.util.Constantes
								.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO") + "/imagens/bolaverde.png' border='0'  title='Servi�o Ativo'/>");					
						out.print(""+UtilI18N.internacionaliza(request, "citcorpore.comum.ativo")+"");
					}
				}else if (servicoContratoAux.getSituacaoServico().intValue() == 2){
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/bolavermelha.png' border='0'  title='Servi�o Inativo'/>");					
					out.print("Inativo");
				}else {
					String strSituacao = "";
					if (servicoContratoAux.getSituacaoServico().intValue() == -999){
						strSituacao = UtilI18N.internacionaliza(request, "avaliacaoContrato.emAnalise");
					}
					if (servicoContratoAux.getSituacaoServico().intValue() == 3){
						strSituacao = UtilI18N.internacionaliza(request, "perfil.criacao");
					}
					if (servicoContratoAux.getSituacaoServico().intValue() == 4){
						strSituacao = UtilI18N.internacionaliza(request, "baseconhecimento.emdesenho");
					}					
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/ball_gray__.gif' border='0'  title='" + strSituacao + "'/>");					
					out.print(strSituacao);					
				}
				out.print("</td>");				
				out.print("<td>");
					out.print(servicoContratoAux.getNomeTipoDemandaServico());
				out.print("</td>");		
				out.print("<td>");
				if (servicoContratoAux.getTemSLA()){
					out.print("<img src='" + br.com.citframework.util.Constantes
							.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO") + "/imagens/tick.png' border='0'  title='SLA OK'/>");					
				}else{
					out.print("&nbsp;");					
				}
				out.print("</td>");
				out.print("<td>");
					out.print(UtilDatas.dateToSTR(servicoContratoAux.getDataInicio()));
				out.print("</td>");	
				out.print("<td>");
				if (servicoContratoAux.getDataFim() != null){
					out.print(UtilDatas.dateToSTR(servicoContratoAux.getDataFim()));
				}else{
					out.print("--");
				}
				out.print("</td>");	
				out.print("<td>");
				if (servicoContratoAux.getQuantidade() != null){
					out.print(servicoContratoAux.getQuantidade());
				}else{
					out.print("--");
				}
				out.print("</td>");					
			out.print("</tr>");
			if ((servicoContratoAux.getQtdeDentroPrazo() != null && servicoContratoAux.getQtdeDentroPrazo().intValue() > 0) 
					|| (servicoContratoAux.getQtdeForaPrazo() != null && servicoContratoAux.getQtdeForaPrazo().intValue() > 0)){
			out.print("<tr style='border: none;background:" + corLinha + ";'>");
				out.print("<td>");
					out.print("&nbsp;");
				out.print("</td>");
				out.print("<td colspan='20'>");
				out.print("<table width='100%'>");
				out.print("<tr>");
					out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
					out.print("<td>");
					out.print("<div style='border: none;background:" + corLinha + ";' id='divContratoServico_" + servicoContratoAux.getIdServicoContrato() + "'>");
					out.print("<table>");
						out.print("<tr>");
							out.print("<td>&nbsp;</td>");
						out.print("</tr>");
						out.print("<tr>");
							out.print("<td>"+UtilI18N.internacionaliza(request, "avaliacao.fornecedor.dentroPrazo")+":</td>");
							out.print("<td> <b><u>" + servicoContratoAux.getQtdeDentroPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getDentroPrazo(),2) + "%)</u></b></td>");
							out.print("<td>&nbsp;</td>");
							out.print("<td>&nbsp;"+UtilI18N.internacionaliza(request, "avaliacao.fornecedor.foraPrazo")+":</td>");
							out.print("<td> <b><u>" + servicoContratoAux.getQtdeForaPrazo() + " (" + UtilFormatacao.formatDouble(servicoContratoAux.getForaPrazo(),2) + "%)</u></b></td>");
						out.print("</tr>");
					out.print("</table>");
					out.print("<table width='100%' border='1'>");
						out.print("<tr>");
							for (int i = 1; i <= 100; i++){
								String cor = "";
								if (i <= servicoContratoAux.getDentroPrazo().intValue()){
									cor = "green";
								}else{
									cor = "red";
								}
								out.print("<td style='border:1px solid black;background-color:" + cor + ";width:1%' title='" + i + "%'>&nbsp;</td>");
							}
						out.print("</tr>");
					out.print("</table>");
					out.print("</div>");
					out.print("</td>");
				out.print("</tr>");
				out.print("</table>");
				out.print("</td>");
			out.print("</tr>");
			}else{
				out.print("<tr style='border: none;background:" + corLinha + ";'>");
					out.print("<td>");
						out.print("&nbsp;");
					out.print("</td>");
					out.print("<td colspan='20'>");
						out.print("<table width='100%'>");
							out.print("<tr>");
								out.print("<td>&nbsp;</td>");
							out.print("</tr>");						
							out.print("<tr>");
								out.print("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");
								out.print("<td>"+UtilI18N.internacionaliza(request, "avaliacao.fornecedor.sla.erro")+"</td>");				
							out.print("</tr>");
						out.print("</table>");
					out.print("</td>");
				out.print("</tr>");
			}
		}
	}else{
		out.print("<tr>");
		out.print("<td>");
		out.print("<b>" + UtilI18N.internacionaliza(request, "avaliacaoContrato.erro") + "!</b>");	
		out.print("</td>");
		out.print("</tr>");
	}
	%>
</table>	
</div>			
			</div>
		</form>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>