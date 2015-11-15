package link.arata.mybatishelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class ProjectPropertyPage extends PropertyPage {
	private static final String KEY_SOUSRC_PACKAGE = "sourcePackage";
	private static final String KEY_RESOURCES_PACKAGE = "resourcesPackage";
	private static final String KEY_NEW_LINE_CODE = "newLineCoee";
	private static final String KEY_TEMPLATE_XML = "templateXml";

	private Text sourcePackageText;
	private Text resourcesPackageText;
	private Combo newLineCodeCombo;
	private Text templateXmlText;

	private static final String DEFAULT_SOUSRC_PACKAGE = "src/main/java";
	private static final String DEFAULT_RESOURCES_PACKAGE = "src/main/resources";
	private static final String DEFAULT_NEW_LINE_CODE = "CRLF";
	private static final String DEFAULT_TEMPLATE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n"
			+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n"
			+ "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + "<mapper>\r\n" + "</mapper>";

	private static final String[] NEW_LINE_CODE_VALUES = { "CRLF", "LF" };

	@Override
	protected Control createContents(Composite parent) {
		IProject project = getProject();
		Composite composite = new Composite(parent, SWT.NONE);
		initDisplay(composite);

		initValue(project);
		// Activator.getDefault().getProject();
		return composite;
	}

	private void initDisplay(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label sourcePackageLavel = new Label(composite, SWT.NONE);
		sourcePackageLavel.setText("ソースパッケージ");

		sourcePackageText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		sourcePackageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label resourcesPackageLabel = new Label(composite, SWT.NONE);
		resourcesPackageLabel.setText("リソースパッケージ");

		resourcesPackageText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		resourcesPackageText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label newLineCodeLabel = new Label(composite, SWT.NONE);
		newLineCodeLabel.setText("改行コード");

		newLineCodeCombo = new Combo(composite, SWT.READ_ONLY);
		for (String newLine : NEW_LINE_CODE_VALUES) {
			newLineCodeCombo.add(newLine);
		}

		Label templateXmlLabel = new Label(composite, SWT.NONE);
		templateXmlLabel.setText("テンプレートXML");

		templateXmlText = new Text(composite, SWT.BORDER | SWT.V_SCROLL);
		templateXmlText.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private void initValue(IProject project) {
		String sourcePackage = getValue(project, KEY_SOUSRC_PACKAGE);
		if (sourcePackage != null) {
			sourcePackageText.setText(sourcePackage);
		} else {
			sourcePackageText.setText(DEFAULT_SOUSRC_PACKAGE);
		}

		String resourcesPackage = getValue(project, KEY_RESOURCES_PACKAGE);
		if (resourcesPackage != null) {
			resourcesPackageText.setText(resourcesPackage);
		} else {
			resourcesPackageText.setText(DEFAULT_RESOURCES_PACKAGE);
		}

		String newLineCode = getValue(project, KEY_NEW_LINE_CODE);
		if (newLineCode != null) {
			newLineCodeCombo.setText(newLineCode);
		} else {
			newLineCodeCombo.setText(DEFAULT_NEW_LINE_CODE);
		}

		String templateXml = getValue(project, KEY_TEMPLATE_XML);
		if (templateXml != null) {
			templateXmlText.setText(templateXml);
		} else {
			templateXmlText.setText(DEFAULT_TEMPLATE_XML);
		}
	}

	@Override
	public boolean performOk() {
		IProject project = getProject();
		setValue(project, KEY_SOUSRC_PACKAGE, sourcePackageText.getText());
		setValue(project, KEY_RESOURCES_PACKAGE, resourcesPackageText.getText());
		setValue(project, KEY_NEW_LINE_CODE, newLineCodeCombo.getText());
		setValue(project, KEY_TEMPLATE_XML, templateXmlText.getText());

		return true;
	}

	@Override
	protected void performDefaults() {
		sourcePackageText.setText(DEFAULT_SOUSRC_PACKAGE);
		resourcesPackageText.setText(DEFAULT_RESOURCES_PACKAGE);
		newLineCodeCombo.setText(DEFAULT_NEW_LINE_CODE);
		templateXmlText.setText(DEFAULT_TEMPLATE_XML);
	}

	protected IProject getProject() {
		IAdaptable element = getElement();
		if (element instanceof IProject) {
			return (IProject) element;
		}
		Object project = element.getAdapter(IProject.class);
		if (project instanceof IProject) {
			return (IProject) project;
		}
		return null;
	}

	private String getValue(IProject project, String key) {
		try {
			return project.getPersistentProperty(new QualifiedName(Activator.PLUGIN_ID, key));
		} catch (CoreException e) {
			ILog log = Activator.getDefault().getLog();
			log.log(e.getStatus());
			return null;
		}
	}

	private void setValue(IProject project, String key, String value) {
		try {
			project.setPersistentProperty(new QualifiedName(Activator.PLUGIN_ID, key), value);
		} catch (CoreException e) {
			ILog log = Activator.getDefault().getLog();
			log.log(e.getStatus());
		}
	}
}
