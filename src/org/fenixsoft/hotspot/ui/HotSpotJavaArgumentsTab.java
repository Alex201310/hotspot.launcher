package org.fenixsoft.hotspot.ui;

import java.lang.reflect.Field;

import org.eclipse.jdt.debug.ui.launchConfigurations.JavaArgumentsTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.fenixsoft.hotspot.Activator;
import org.fenixsoft.hotspot.model.ArgumentCodeTranslater;
import org.fenixsoft.hotspot.model.ArgumentRepostory;
import org.fenixsoft.hotspot.model.HotSpotArgumentsContentProvider;

/**
 * ��չEclipseԭ����LocalJavaApplicationTab�е�JavaArgumentsTab���������HotSpot�Ĳ�������ҳǩ
 * 
 * @author icyfenix@gmail.com
 */
public class HotSpotJavaArgumentsTab extends JavaArgumentsTab {

	/** �����ı�<->�����ֿ�����˫��ת���� */
	private ArgumentCodeTranslater translater = new ArgumentCodeTranslater();
	/** HotSpot����Composite */
	private HotSpotArgumentsComposite hsaComposite;

	/**
	 * ����ҳǩ�ؼ�
	 */
	@Override
	public void createControl(Composite parent) {
		// ���ؼ�������һ��CTabFolder
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new FillLayout(SWT.HORIZONTAL));
		final CTabFolder tabFolder = new CTabFolder(mainComposite, SWT.FLAT | SWT.BOTTOM);

		// ���ø���createControl����������ԭ�е�JavaArgumentsTabҳǩ�ؼ�������ΪbasicTabPageҳǩ�еĿؼ�
		Composite originalComposite = new Composite(tabFolder, SWT.NONE);
		originalComposite.setLayout(new GridLayout(1, false));
		super.createControl(originalComposite);
		final CTabItem basicTabPage = new CTabItem(tabFolder, SWT.NONE);
		basicTabPage.setText("Basic");
		basicTabPage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/basic.png"));
		basicTabPage.setControl(originalComposite);

		// �ӳٴ���HotSpotArgumentsComposite��ֻ����advanceTabPageҳǩ
		final CTabItem advanceTabPage = new CTabItem(tabFolder, SWT.NONE);
		advanceTabPage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/advance.png"));
		advanceTabPage.setText("Advance");

		tabFolder.addSelectionListener(new SelectionAdapter() {
			/** ��������Tabҳǩ�л��¼� */
			@Override
			public void widgetSelected(SelectionEvent selectionevent) {
				if (selectionevent.item == advanceTabPage) {
					// Basicҳǩת��Advanceҳǩ����VM�����ı�������ݽ���Ϊ��ѡ��
					ArgumentRepostory repo = new ArgumentRepostory();
					String argCode = getVMArgumentText().getText();
					translater.setArgumentCodeStringToRepostory(repo, argCode);
					// �����²�����������Composite
					hsaComposite = new HotSpotArgumentsComposite(tabFolder, SWT.FULL_SELECTION, repo);
					advanceTabPage.setControl(hsaComposite);
				} else {
					// Advanceҳǩת��Basicҳǩ�����������õ�VM�����ı���
					String argCode = translater.getArgumentCodeStringFromRepostory(getArgumentRepostory());
					Text text = getVMArgumentText();
					text.setText(argCode);
				}
			}
		});

		tabFolder.setSelection(0);
		setControl(mainComposite);
	}

	/**
	 * ��ȡ��ǰ������������ֿ�
	 * 
	 * @return
	 */
	private ArgumentRepostory getArgumentRepostory() {
		return ((HotSpotArgumentsContentProvider) hsaComposite.getCheckboxTreeViewer().getContentProvider()).getRepostory();
	}

	/**
	 * ��ȡ����������������ı������ڿɼ��Բ��㣬��Ҫ����
	 * 
	 * @return
	 */
	private Text getVMArgumentText() {
		try {
			Field vmTextField = fVMArgumentsBlock.getClass().getDeclaredField("fVMArgumentsText");
			vmTextField.setAccessible(true);
			return (Text) vmTextField.get(fVMArgumentsBlock);
		} catch (Exception e) {
			throw new IllegalAccessError(e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "HS Arguments";
	}

}
