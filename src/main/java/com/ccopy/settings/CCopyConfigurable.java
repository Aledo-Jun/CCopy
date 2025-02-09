package com.ccopy.settings;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;


public class CCopyConfigurable implements SearchableConfigurable {
    private JPanel mainPanel;
    private JCheckBox enableCheckBox;

    // UI 초기화
    @Nullable
    @Override
    public JComponent createComponent() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(4);
        gbc.anchor = GridBagConstraints.WEST;

        // 기능 활성화 체크박스
        enableCheckBox = new JCheckBox("Enable Copy Expansion");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(enableCheckBox, gbc);

        return mainPanel;
    }

    @Override
    public boolean isModified() {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        return enableCheckBox.isSelected() != state.enabled;
    }

    @Override
    public void apply() throws ConfigurationException {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        state.enabled = enableCheckBox.isSelected();
    }

    @Override
    public void reset() {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        enableCheckBox.setSelected(state.enabled);
    }

    @Override
    public void disposeUIResources() {
        mainPanel = null;
        enableCheckBox = null;
    }

    @NotNull
    @Override
    public String getId() {
        return "com.ccopy.settings.CCopyConfigurable";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "CCopy Settings";
    }
}
