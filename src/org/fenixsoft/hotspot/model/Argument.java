package org.fenixsoft.hotspot.model;

/**
 * HotSpot���������
 * 
 * @author icyfenix@gmail.com
 */
public class Argument {

	/** �������� */
	private String category;

	/** �������� */
	private String name;

	/**
	 * �������ͣ�develop, develop_pd, product, product_pd, diagnostic, experimental,
	 * notproduct, manageable, product_rw, lp64_product��
	 */
	private ArgumentType argumentType;

	/** �������� */
	private String dataType;

	/** ע�� */
	private String comment;

	/** ����Ĭ��ֵ */
	private String defaultValue;

	/** �Ƿ�ѡ�У����ⲿ���� */
	private boolean check;

	/** �û��趨ֵ */
	private String userValue;

	public Argument() {
	}

	public Argument(String argumentLine) {
		String[] argumentArray = argumentLine.split("\t");
		setArgumentType(ArgumentType.valueOf(argumentArray[0]));
		setCategory(argumentArray[1]);
		setName(argumentArray[2]);
		setDataType(argumentArray[3]);
		setDefaultValue(argumentArray[4]);
		setComment(argumentArray[5]);
	}

	public String getCategory() {
		return category;
	}

	public String getFirstCategory() {
		return getCategory().split("/t")[0];
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArgumentType getArgumentType() {
		return argumentType;
	}

	public void setArgumentType(ArgumentType argumentType) {
		this.argumentType = argumentType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComment() {
		return comment == null ? "" : comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getUserValue() {
		return userValue;
	}

	public void setUserValue(String userValue) {
		this.userValue = userValue;
	}

}
