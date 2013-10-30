package org.fenixsoft.hotspot.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * �����û��Ƿ�ѡ�в������ڲ����ֿ��н��й���
 * 
 * @author icyfenix@gmail.com
 */
public class HotSpotArgumentsContentCheckedFilter extends ViewerFilter {

	public HotSpotArgumentsContentCheckedFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof Argument) {
			return ((Argument) element).isCheck();
		} else {
			return true;
		}
	}
}
