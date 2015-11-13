/*
 * Copyright 2015 Shinichi ARATA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package link.arata.mybatishelper;

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
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

public class MyBatisHelperCommand extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			// アクティブエディタ（編集中のファイル）の情報を取得する。
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IEditorPart editor = window.getActivePage().getActiveEditor();
			ITextEditor textEditor = (ITextEditor) editor;

			IFileEditorInput editorInput = (IFileEditorInput) textEditor.getEditorInput();
			IFile file = editorInput.getFile();
			IProject project = file.getProject();

			// ファイル名の取得
			String fileName = getFileName(textEditor);
			file = project.getFile(new Path("/src/main/resources/" + fileName + ".xml"));
			if (!file.exists()) {
				createFolder(file.getProject(), file.getProjectRelativePath().removeLastSegments(1));
				// デフォルトのXMLテンプレートの出力
				String rtnXml = MessageResources.getMessage("mapperXml");
				InputStream is = new ByteArrayInputStream(rtnXml.getBytes("utf-8"));
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

	// 拡張子を除くファイル名の取得
	private String getFileName(ITextEditor textEditor) {
		IFileEditorInput input = (IFileEditorInput) textEditor.getEditorInput();
		IFile openFile = input.getFile();
		String filePath = openFile.getFullPath().toString();
		String noExtentionPath = filePath.substring(0, filePath.lastIndexOf("."));
		return noExtentionPath.substring(noExtentionPath.lastIndexOf("/src/main/java/") + 15);
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
