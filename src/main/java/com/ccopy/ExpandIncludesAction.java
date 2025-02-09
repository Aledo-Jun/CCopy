package com.ccopy;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;

public class ExpandIncludesAction extends AnAction {

    public ExpandIncludesAction() {
        super("Expand Includes", "Expand C++ include directives", null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 현재 프로젝트와 에디터, PsiFile 가져오기
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getData(CommonDataKeys.PSI_FILE);

        if (editor == null || psiFile == null) {
            return; // 에디터나 파일이 없으면 동작하지 않음
        }

        // 선택한 텍스트가 있으면 사용, 없으면 전체 파일 텍스트 사용
        String text = editor.getSelectionModel().getSelectedText();
        if (text == null || text.isEmpty()) {
            text = editor.getDocument().getText();
        }

        // 확장 처리 수행
        String expandedText = IncludeExpander.expand(psiFile, text);

        // 예시: 결과를 클립보드에 복사
        CopyPasteManager.getInstance().setContents(new StringSelection(expandedText));
    }
}
