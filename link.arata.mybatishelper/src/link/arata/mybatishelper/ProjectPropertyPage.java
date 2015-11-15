package link.arata.mybatishelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;

public class ProjectPropertyPage extends PropertyPage {
	private String KEY_NEW_LINE_CODE;
	private Text newLineCode;

	@Override
	protected Control createContents(Composite parent) {
		IProject project = (IProject) getElement();

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(composite, SWT.NONE);
		label.setText("改行コード");

		newLineCode = new Text(composite, SWT.SINGLE | SWT.BORDER);
		newLineCode.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		String value = getValue(project, KEY_NEW_LINE_CODE);
		if (value != null) {
			newLineCode.setText(value);
		}

		return composite;
	}

	@Override
	public boolean performOk() {
		IProject project = (IProject) getElement();
		setValue(project, KEY_NEW_LINE_CODE, newLineCode.getText());

		return true;
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
