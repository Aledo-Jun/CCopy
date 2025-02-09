package com.ccopy.settings;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;


public class OpenCCopySettingsAction extends AnAction {

    // 생성자에서 액션의 이름과 설명(툴팁)을 설정할 수 있습니다.
    public OpenCCopySettingsAction() {
        super("CCopy Settings", "Open the CCopy plugin settings", null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // 현재 열려 있는 프로젝트 가져오기 (없으면 null일 수 있으니 주의)
        Project project = event.getProject();

        // 플러그인 설정 페이지의 ID가 설정 UI의 getId()와 일치해야 합니다.
        // 예를 들어, Configurable 클래스의 getId()가 "com.ccopy.settings.CCopyConfigurable"라면,
        // 아래와 같이 해당 ID를 사용합니다.
        ShowSettingsUtil.getInstance().showSettingsDialog(project, "com.ccopy.settings.CCopyConfigurable");
    }
}