package link.arata.mybatishelper;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.QualifiedName;

public class PropertiesUtil {
	/**
	 * プロパティの値の取得
	 * 
	 * @param project
	 *            プロジェクト
	 * @param key
	 *            プロパティのキー
	 * @return プロパティの値
	 */
	public static String getValue(IProject project, String key) {
		try {
			return project.getPersistentProperty(new QualifiedName(Activator.PLUGIN_ID, key));
		} catch (CoreException e) {
			ILog log = Activator.getDefault().getLog();
			log.log(e.getStatus());
			return null;
		}
	}

	/**
	 * プロパティの値のセット
	 * 
	 * @param project
	 *            プロジェクト
	 * @param key
	 *            プロパティのキー
	 * @param value
	 *            プロパティの値
	 */
	public static void setValue(IProject project, String key, String value) {
		try {
			project.setPersistentProperty(new QualifiedName(Activator.PLUGIN_ID, key), value);
		} catch (CoreException e) {
			ILog log = Activator.getDefault().getLog();
			log.log(e.getStatus());
		}
	}
}
