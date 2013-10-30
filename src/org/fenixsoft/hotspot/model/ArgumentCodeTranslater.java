package org.fenixsoft.hotspot.model;

import java.util.Map;

/**
 * ���ݲ����ֿ����û�ѡ������ݣ�����HotSpot���������߸���HotSpot�������ݣ����������ֿ��е�����ѡ��
 * 
 * @author icyfenix@gmail.com
 */
public class ArgumentCodeTranslater {

	/**
	 * ���ݲ����ֿ����û�������������HotSpot�����ַ���
	 */
	public String getArgumentCodeStringFromRepostory(ArgumentRepostory repo) {
		StringBuffer ret = new StringBuffer();
		for (Map.Entry<String, Argument> argumentEntity : repo.getArgumentIndex().entrySet()) {
			Argument argument = argumentEntity.getValue();
			if (argument.isCheck()) {
				String name = argument.getName();
				String value = argument.getUserValue();
				value = value == null ? "" : value;
				if (argument.getArgumentType() == ArgumentType.standard) {
					// ��׼����
					if ("Xmx".equals(name) || "Xms".equals(name) || "Xss".equals(name) || "Xmn".equals(name)) {
						ret.append(" -").append(name).append(value);
					} else {
						if ("".equals(value)) {
							ret.append(" -").append(name);
						} else {
							ret.append(" -").append(name).append(":").append(value);
						}
					}
				} else if (argument.getArgumentType() == ArgumentType.custom) {
					// �û�����
					ret.append(" -D").append(name).append("=").append(value);
				} else {
					// ���ȶ�����
					ret.append(" -XX:");
					if ("bool".equals(argument.getDataType())) {
						ret.append(Boolean.valueOf(value) ? "+" : "-").append(name);
					} else {
						ret.append(name).append("=").append(value);
					}
				}
			}
		}
		return ret.toString().trim();
	}

	/**
	 * ����HotSpot�����ַ����趨�����ֿ����û���������
	 */
	public void setArgumentCodeStringToRepostory(ArgumentRepostory repo, String codeString) {
		if (codeString == null || codeString.trim().length() == 0) {
			return;
		}
		String[] codes = codeString.split(" ");
		String name = "", value = "";
		boolean isCustom;
		for (String code : codes) {
			if (code == null || code.trim().length() == 0) {
				continue;
			}
			try {
				isCustom = false;
				if (code.startsWith("-XX:")) {
					// ���ȶ�����
					code = code.substring(4);
					if (code.startsWith("+")) {
						name = code.substring(1);
						value = "true";
					} else if (code.startsWith("-")) {
						name = code.substring(1);
						value = "false";
					} else {
						int equalMarkIndex = code.indexOf("=");
						name = code.substring(0, equalMarkIndex);
						value = code.substring(equalMarkIndex + 1);
					}
				} else if (code.startsWith("-D")) {
					// �û�����
					isCustom = true;
					code = code.substring(2);
					int equalMarkIndex = code.indexOf("=");
					if (equalMarkIndex > -1) {
						name = code.substring(0, equalMarkIndex);
						value = code.substring(equalMarkIndex + 1);
					} else {
						name = code;
						value = "";
					}
				} else {
					// ��׼����
					code = code.substring(1);
					if (code.startsWith("Xmx") || code.startsWith("Xms") || code.startsWith("Xss") || code.startsWith("Xmn")) {
						name = code.substring(0, 3);
						value = code.substring(3);
					} else {
						int equalMarkIndex = code.indexOf(":");
						if (equalMarkIndex > -1) {
							name = code.substring(0, equalMarkIndex);
							value = code.substring(equalMarkIndex + 1);
						} else {
							name = code;
							value = "";
						}
					}
				}
				// ��д�ֿ�
				Argument arg = repo.getArgumentIndex().get(name);
				if (arg == null) {
					// ����ò����ڲֿ��в����ڣ�����Custom/Unknown����������һ��
					arg = new Argument();
					arg.setName(name);
					if (isCustom) {
						arg.setCategory("Custom");
						arg.setArgumentType(ArgumentType.custom);
					} else {
						arg.setCategory("Unknown");
						arg.setArgumentType(ArgumentType.unknown);
					}
					repo.addArgument(arg);
				}
				arg.setUserValue(value);
				arg.setCheck(true);
			} catch (Exception e) {
				// ĳ����������ʧ�ܣ�����Ӱ������������������
				e.printStackTrace();
			}
		}
	}
}
