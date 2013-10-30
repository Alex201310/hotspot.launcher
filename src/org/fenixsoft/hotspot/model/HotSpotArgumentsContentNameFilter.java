package org.fenixsoft.hotspot.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Text;

/**
 * �����û��ڹ��˿ؼ�����������ݣ��ڲ��������н��й��ˣ���Сд������
 * 
 * @author icyfenix@gmail.com
 */
public class HotSpotArgumentsContentNameFilter extends ViewerFilter {

	private Text text;

	public HotSpotArgumentsContentNameFilter(Text text) {
		this.text = text;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof Argument) {
			return ((Argument) element).getName().toUpperCase().indexOf(text.getText().toUpperCase()) > -1;
		} else {
			return true;
		}
	}
}
