package link.arata.mybatishelper;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "link.arata.mybatishelper"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
	 * BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
	 * BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	// public IProject getProject() {
	// IViewReference[] parts =
	// getWorkbench().getActiveWorkbenchWindow().getActivePage().getViewReferences();
	// for (IViewReference reference : parts) {
	// System.out.println(reference.getClass().getCanonicalName());
	// reference.getView(restore)
	// IViewPart part = reference.getView(false);
	// if (part instanceof ResourceNavigator) {
	// System.out.println(part.getClass().getCanonicalName());
	// ResourceNavigator navigator = (ResourceNavigator) part;
	// StructuredSelection selection = (StructuredSelection)
	// navigator.getTreeViewer().getSelection();
	// IResource resource = (IResource) selection.getFirstElement();
	// return resource.getProject();
	// }
	// }
	// return null;
	// }
}
