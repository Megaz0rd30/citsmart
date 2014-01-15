package br.com.citframework.integracao;

import java.io.Serializable;

/**
 * Classe de campos da base de dados e com os atributos do DTO.
 * 
 * @author Administrador
 * 
 */
public class Field implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5945592714921506681L;

	private String fieldDB;
	private String fieldClass;
	private boolean pk = false;// caso seja chave prim�ria
	private boolean sequence = false;// preenchido com sequence
	private boolean auto = false;// valor default ou auto incremento
	private boolean unique = false;// se pode ou n�o possuir valor duplicado
	private String msgReturn = ""; // mensagem de retorno para campo �nico

	/**
	 * Construtor Field sem o campo de descri��o para unique
	 * 
	 * @param fieldDB
	 *            Nome do campo no banco de dados.
	 * @param fieldClass
	 *            Nome do atributo do DTO
	 * @param pk
	 *            Ccso seja chave prim�ria
	 * @param sequence
	 *            Preenchido com sequence
	 * @param auto
	 *            Valor default ou auto incremento
	 * @param unique
	 *            Se pode ou n�o possuir valor duplicado
	 */
	public Field(String fieldDB, String fieldClass, boolean pk,
			boolean sequence, boolean auto, boolean unique) {
		super();
		this.fieldDB = fieldDB;
		this.fieldClass = fieldClass;
		this.pk = pk;
		this.sequence = sequence;
		this.auto = auto;
		this.unique = unique;
	}

	/**
	 * Construtor Field com o campo de descri��o para unique
	 * 
	 * @param fieldDB
	 *            Nome do campo no banco de dados.
	 * @param fieldClass
	 *            Nome do atributo do DTO
	 * @param pk
	 *            Ccso seja chave prim�ria
	 * @param sequence
	 *            Preenchido com sequence
	 * @param auto
	 *            Valor default ou auto incremento
	 * @param unique
	 *            Se pode ou n�o possuir valor duplicado
	 * @param msgReturn
	 *            de retorno caso aconte�a duplica��o de campos acionado pelo
	 *            par�metro 'unique'
	 */
	public Field(String fieldDB, String fieldClass, boolean pk,
			boolean sequence, boolean auto, boolean unique, String msgReturn) {
		super();
		this.fieldDB = fieldDB;
		this.fieldClass = fieldClass;
		this.pk = pk;
		this.sequence = sequence;
		this.auto = auto;
		this.unique = unique;
		this.msgReturn = msgReturn;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(String campoClasse) {
		this.fieldClass = campoClasse;
	}

	public String getFieldDB() {
		return fieldDB;
	}

	public void setFieldDB(String campoDB) {
		this.fieldDB = campoDB;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isSequence() {
		return sequence;
	}

	public void setSequence(boolean sequence) {
		this.sequence = sequence;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public void setMsgReturn(String msgReturn) {
		this.msgReturn = msgReturn;
	}

	public String getMsgReturn() {
		return msgReturn;
	}

}
