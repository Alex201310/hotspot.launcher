package org.fenixsoft.hotspot.model;

import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.ResourceManager;
import org.fenixsoft.hotspot.Activator;

/**
 * HotSpot���������ṩ�ߣ���TreeViewer�б���ʾ���Լ�ViewFilter����ʹ��
 * 
 * @author icyfenix@gmail.com
 */
public class HotSpotArgumentsContentProvider implements ITreeContentProvider, ITableLabelProvider, ICheckStateProvider {

	/** �ļ��кͲ���ͼƬ */
	private Image categoryImage = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/prj_obj.gif");
	private Image argumentImage = ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/java.gif");

	/** �����ֿ� */
	private ArgumentRepostory repostory;

	public ArgumentRepostory getRepostory() {
		return repostory;
	}

	public void setRepostory(ArgumentRepostory repostory) {
		this.repostory = repostory;
	}

	/**
	 * ���ش������������Ӷ�������ö�������Ҷ�ӽڵ�û���Ӷ��󣬷���null
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof String) {
			return repostory.getArgumentsByCategory((String) parentElement).toArray();
		} else if (parentElement instanceof Argument) {
			return null;
		} else {
			throw new IllegalAccessError("never get here");
		}

	}

	/**
	 * ���ش������ĸ�����
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof String) {
			return repostory;
		} else if (element instanceof Argument) {
			return ((Argument) element).getFirstCategory();
		} else {
			throw new IllegalAccessError("never get here");
		}
	}

	/**
	 * �жϴ�������Ƿ�����Ӷ���
	 */
	@Override
	public boolean hasChildren(Object element) {
		return element instanceof String && repostory.getArgumentsByCategory((String) element) != null;
	}

	/**
	 * ������������ݣ���TreeViewer��input�����ṩ����ȡ��Ԫ�ص�������Ԫ�ء�<br>
	 * �����������ݱ�����ArgumentRepostory��ʵ��
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		repostory = (ArgumentRepostory) inputElement;
		return repostory.getAllArgumentsWithCateorgy().keySet().toArray();
	}

	/**
	 * ��ȡ�ƶ�����ʾͼ��
	 */
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			if (element instanceof String) {
				return categoryImage;
			} else {
				return argumentImage;
			}
		} else {
			return null;
		}
	}

	/**
	 * ��ȡָ���е���ʾ����
	 */
	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof String) {
			// Category�ڵ�ֻ�ڵ�һ����ʾ����
			if (columnIndex == 0) {
				return (String) element;
			} else {
				return "";
			}
		} else {
			// ֱ�ӵ������ʾ����
			Argument arg = (Argument) element;
			switch (columnIndex) {
			case 0:
				return arg.getName();
			case 1:
				return arg.getArgumentType().toString();
			case 2:
				return arg.getDataType();
			case 3:
				return arg.getDefaultValue();
			case 4:
				return arg.getUserValue();
			case 5:
				return arg.getComment();
			default:
				return "";
			}
		}
	}

	/**
	 * �Ƿ�ѡ��
	 */
	@Override
	public boolean isChecked(Object element) {
		if (element instanceof Argument) {
			return ((Argument) element).isCheck();
		} else {
			return false;
		}
	}

	/**
	 * �Ƿ��û�
	 */
	@Override
	public boolean isGrayed(Object element) {
		if (element instanceof Argument) {
			return false;
		} else {
			return true;
		}
	}

	// ////////////////////////////
	// ����Ϊ����Ҫʹ�õķ���
	// ////////////////////////////

	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
}
