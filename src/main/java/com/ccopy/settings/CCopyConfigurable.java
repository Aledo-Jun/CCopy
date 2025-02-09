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
    private JTextField shortcutField;

    // UI 초기화
    @Nullable
    @Override
    public JComponent createComponent() {
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = JBUI.insets(4);
        gbc.anchor = GridBagConstraints.WEST;

        // 기능 활성화 체크박스
        enableCheckBox = new JCheckBox("Enable Copy Include Expansion");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(enableCheckBox, gbc);

        // 단축키 라벨
        JLabel shortcutLabel = new JLabel("Copy Command Shortcut:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(shortcutLabel, gbc);

        // 단축키 입력란
        shortcutField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(shortcutField, gbc);

        return mainPanel;
    }

    @Override
    public boolean isModified() {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        boolean modified = (enableCheckBox.isSelected() != state.enabled);
        modified |= (!shortcutField.getText().equals(state.copyShortcut));
        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        state.enabled = enableCheckBox.isSelected();
        state.copyShortcut = shortcutField.getText();
    }

    @Override
    public void reset() {
        CCopySettings.State state = CCopySettings.getInstance().getState();
        enableCheckBox.setSelected(state.enabled);
        shortcutField.setText(state.copyShortcut);
    }

    @Override
    public void disposeUIResources() {
        mainPanel = null;
        enableCheckBox = null;
        shortcutField = null;
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
