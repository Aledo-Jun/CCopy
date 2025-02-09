package com.ccopy.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;


@State(name = "CCopySettings", storages = @Storage("ccopy.xml"))
public class CCopySettings implements PersistentStateComponent<CCopySettings.State> {
    public static class State {
        public boolean enabled = true;          // ON if true
        public String copyShortcut = "ctrl C";  // default = ctrl C
    }

    private State myState = new State();

    @Override
    public @NotNull State getState() {
        if (myState == null) myState = new State();
        return myState;
    }

    @Override
    public void loadState(@NotNull State state) {
        myState = state;
    }

    // 플러그인 내 어디서든 설정을 참조할 수 있도록 싱글톤 패턴으로 제공
    public static CCopySettings getInstance() {
        return ApplicationManager.getApplication().getService(CCopySettings.class);
    }
}