-- PostgreSQL

-- In�cio Murilo Gabriel 27/06/13

insert into modelosemails (idmodeloemail, titulo, texto, situacao, identificador) values (54, 'Pesquisa de Satisfa��o - Solicita��o ${IDSOLICITACAOSERVICO}', 'Uma nota &quot;${NOTA}&quot; foi dada pelo usu&aacute;rio ${USUARIO} na pesquisa de satisfa&ccedil;&atilde;o da solicita&ccedil;&atilde;o&nbsp;${IDSOLICITACAOSERVICO}.<br />Segue o coment&aacute;rio/sujest&atilde;o de melhoria informados pelo usu&aacute;rio:<br /><br />${COMENTARIO}<br /><br />Atenciosamente,<br /><br />Central IT Tecnologia da Informa&ccedil;&atilde;o Ltda.<br type="_moz" />', 'A', 'pesqSatisfNegativo');

-- Fim Murilo Gabriel

-- In�cio Fl�vio 28/06/13

ALTER TABLE acordoservicocontrato ADD COLUMN habilitado character varying;

UPDATE acordoservicocontrato SET habilitado = 'S';

-- Fim Fl�vio

-- In�cio Cledson 04/07/13

CREATE TABLE historicosolicitacaoservico (
	idhistoricosolicitacao integer NOT NULL,
	idsolicitacaoservico integer NOT NULL,
	idresponsavelatual integer,
	idgrupo integer,
	idocorrencia integer NOT NULL,
	idservicocontrato integer NOT NULL,
	idcalendario integer NOT NULL,
	datacriacao timestamp,
	datafinal timestamp,
	horastrabalhadas float,
	status varchar
);

-- Fim Cledson

-- In�cio Fl�vio J�nior 19/07/13

ALTER TABLE moedas ADD COLUMN datainicio date;

ALTER TABLE moedas ADD COLUMN datafim date;

ALTER TABLE moedas ADD COLUMN usarcotacao character varying(1);

-- Fim Fl�vio J�nior

-- In�cio Rodrigo Engelberg 22/07/13

update menu set datafim = now() where descricao like 'Download Agente';

-- Fim Rodrigo Engelberg