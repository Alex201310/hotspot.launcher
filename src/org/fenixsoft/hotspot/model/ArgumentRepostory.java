package org.fenixsoft.hotspot.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HotSpot�����Ĭ��ֵ���ݲֿ�
 * 
 * @author icyfenix@gmail.com
 */
public class ArgumentRepostory {

	/** ������������ */
	private Map<String, List<Argument>> arguments = new HashMap<String, List<Argument>>();
	/** �������������ݲ������ƽ���ȫ�ֲ��� */
	private Map<String, Argument> argumentsIndex = new HashMap<String, Argument>();

	public Map<String, Argument> getArgumentIndex() {
		return argumentsIndex;
	}

	public Map<String, List<Argument>> getAllArgumentsWithCateorgy() {
		return arguments;
	}

	public List<Argument> getArgumentsByCategory(String category) {
		return arguments.get(category);
	}

	/**
	 * �����������ֿ���
	 * 
	 * @param arg
	 */
	public void addArgument(Argument arg) {
		String[] categories = arg.getCategory().split("/");
		for (String category : categories) {
			List<Argument> args = arguments.get(category);
			if (args == null) {
				args = new ArrayList<Argument>();
				arguments.put(category, args);
			}
			args.add(arg);
		}
		argumentsIndex.put(arg.getName(), arg);
	}

	/**
	 * Ĭ�ϴ�HotSpotDefaultArguments.txt�ļ��г�ʼ��������Ϣ
	 */
	public ArgumentRepostory() {
		try {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("HotSpotDefaultArguments.txt")));
				String line = null;
				while ((line = in.readLine()) != null) {
					addArgument(new Argument(line));
				}
			} finally {
				if (in != null) {
					in.close();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
