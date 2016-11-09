/**
 * MyBatis Helper
 *
 * Copyright (c) 2016 Shinichi ARATA
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package link.webarata3.mybatishelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import link.webarata3.mybatishelper.util.PropertiesUtil;
import link.webarata3.mybatishelper.util.StringUtil;

public class MyBatisHelperCommand extends AbstractHandler {
	private IProject project;
	private String sourcePath;
	private String resourcesPath;
	private String newLineCode;
	private String templateXml;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		init();
		try {
			// アクティブエディタ（編集中のファイル）の情報を取得する。
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IEditorPart editor = window.getActivePage().getActiveEditor();
			ITextEditor textEditor = (ITextEditor) editor;

			IFileEditorInput editorInput = (IFileEditorInput) textEditor.getEditorInput();
			IFile file = editorInput.getFile();
			IProject project = file.getProject();

			// 指定したディレクトリーの存在チェック
			IFolder srcFolder = project.getFolder(new Path(sourcePath));
			if (!srcFolder.exists()) {
				MessageDialog.openError(window.getShell(), MessageResources.getMessage("noSourceFolder"),
						MessageResources.getMessage("noSourceFolder.detail"));
				return null;
			}
			IFolder resoucesFolder = project.getFolder(new Path(resourcesPath));
			if (!resoucesFolder.exists()) {
				MessageDialog.openError(window.getShell(), MessageResources.getMessage("noResourcesFolder"),
						MessageResources.getMessage("noResourcesFolder"));
				return null;
			}

			// ファイル名の取得
			String fileName = getFileName(textEditor);
			file = project.getFile(new Path(resourcesPath + fileName + ".xml"));
			if (!file.exists()) {
				createFolder(file.getProject(), file.getProjectRelativePath().removeLastSegments(1));
				// デフォルトのXMLテンプレートの出力
				InputStream is = new ByteArrayInputStream(templateXml.getBytes("utf-8"));
				file.create(is, false, null);
				is.close();
			}

			IWorkbenchPage page = window.getActivePage();
			IDE.openEditor(page, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 初期化処理
	private void init() {
		project = Activator.getProject();

		ProjectPropertyPage.setDefault(project);

		sourcePath = normalizePath(PropertiesUtil.getValue(project, ProjectPropertyPage.KEY_SOURCE_PACKAGE));
		resourcesPath = normalizePath(PropertiesUtil.getValue(project, ProjectPropertyPage.KEY_RESOURCES_PACKAGE));
		newLineCode = ProjectPropertyPage.NEW_LINE_CODE_MAP
				.get(PropertiesUtil.getValue(project, ProjectPropertyPage.KEY_NEW_LINE_CODE));
		templateXml = PropertiesUtil.getValue(project, ProjectPropertyPage.KEY_TEMPLATE_XML);
		templateXml = StringUtil.normalizeNewLine(templateXml, newLineCode);
	}

	// パスの最初と最後に / をつける
	private String normalizePath(String path) {
		if (path.length() == 0 || !(path.charAt(0) == '/')) {
			path = "/" + path;
		}
		if (path.length() == 1 || !(path.lastIndexOf("/") == path.length() + 1)) {
			path = path + "/";
		}
		return path;
	}

	// パッケージのルートと拡張子を除くファイルのパス
	private String getFileName(ITextEditor textEditor) {
		IFileEditorInput input = (IFileEditorInput) textEditor.getEditorInput();
		IFile openFile = input.getFile();
		String filePath = openFile.getFullPath().toString();
		String noExtentionPath = filePath.substring(0, filePath.lastIndexOf("."));
		return noExtentionPath.substring(("/" + project.getName()).length() + sourcePath.length());
	}

	// フォルダーを再帰的に作成
	private void createFolder(IProject project, IPath path) throws CoreException {
		IFolder folder = project.getFolder(path);
		if (!folder.exists()) {
			createFolder(project, path.removeLastSegments(1));
			folder.create(false, true, null);
		}
	}
}
