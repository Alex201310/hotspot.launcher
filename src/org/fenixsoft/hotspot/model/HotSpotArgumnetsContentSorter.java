package org.fenixsoft.hotspot.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * ����������򣺰���Category��Argument������ĸ������
 * 
 * @author icyfenix@gmail.com
 */
public class HotSpotArgumnetsContentSorter extends ViewerSorter {
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof String) {
			// compare category
			return ((String) e1).compareTo((String) e2);
		} else {
			// compare argument
			return ((Argument) e1).getName().compareTo(((Argument) e2).getName());
		}
	}
}
