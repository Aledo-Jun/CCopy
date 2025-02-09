package com.ccopy;

import com.ccopy.settings.CCopySettings;
import com.intellij.codeInsight.editorActions.CopyPastePreProcessor;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.RawText;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class IncludeExpanderCopyPasteProcessor implements CopyPastePreProcessor {

    // To prevent multiple includes
    private final Set<String> processedFiles = new HashSet<>();

    /**
     * If not-null value is returned by this method, it will replace copied text. No other preprocessor will be invoked at copy time after this.
     */
    @Override
    public @Nullable String preprocessOnCopy(PsiFile file, int[] startOffsets, int[] endOffsets, String text) {
        if (!CCopySettings.getInstance().getState().enabled) return text;

        if (text == null) return null;

        if (!isCppFile(file)) {
            return text;
        }

        // processedFiles initialize
        processedFiles.clear();

        // main process
        String startingAnnouncement = "// ** The #include statements were replaced by [ CCopy ] **\n";
        return startingAnnouncement + expandIncludes(text, file);
    }

    /**
     * Replaces pasted text. {@code text} value should be returned if no processing is required.
     */
    @Override
    public @NotNull String preprocessOnPaste(Project project, PsiFile file, Editor editor, String text, RawText rawText) {
        return text;
    }

    private boolean isCppFile(PsiFile file) {
        String langId = file.getLanguage().getID();
        return "C/C++".equals(langId) || "ObjectiveC++".equals(langId) || "ObjectiveC".equals(langId);
    }

    private String expandIncludes(String text, PsiFile currentFile) {
        StringBuilder result = new StringBuilder();

        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().startsWith("#include")) {
                String headerName = extractFilePath(line);
                if (headerName == null)
                    result.append(line).append("\n");
                else if (StandardHeaders.is_std_header(headerName)) {
                    if (!processedFiles.contains(headerName)) {
                        processedFiles.add(headerName);
                        result.append(line).append("\n");
                    }
                }
                else {
                    if (!processedFiles.contains(headerName)) {
                        processedFiles.add(headerName);
                        String fileContent = readAndProcessFile(headerName, currentFile);
                        result.append(fileContent).append("\n");
                    }
                }
            } else {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private String extractFilePath(String includeLine) {
        // Ex) "#include "MyHeader.h" -> "MyHeader.h"
        int start = includeLine.indexOf("\"");
        int end = includeLine.lastIndexOf("\"");
        if (start != -1 && end != -1 && start < end) {
            return includeLine.substring(start + 1, end).trim();
        }
        start = includeLine.indexOf("<");
        end = includeLine.lastIndexOf(">");
        if (start != -1 && end != -1 && start < end) {
            return includeLine.substring(start + 1, end).trim();
        }
        return null;
    }

    private String readAndProcessFile(String relativePath, PsiFile currentFile) {
        VirtualFile currentVF = currentFile.getVirtualFile();
        if (currentVF == null) {
            return "// [ " + currentFile.getName() + " ] Current file not accessible";
        }
        VirtualFile includeVF = currentVF.getParent().findFileByRelativePath(relativePath);
        if (includeVF == null) { // if StandardHeaders are well constructed, it'll not happen
            return "// [ " + relativePath + " ] Cannot find the file";
        }

        String fileText;
        try {
            fileText = new String(includeVF.contentsToByteArray(), includeVF.getCharset());
        } catch (IOException e) {
            return "// [ " + relativePath + " ] Cannot read the file";
        }

        // include guard removing
        fileText = IncludeGuardRemover.removeIncludeGuards(fileText);

        // remove starting comments
        fileText = removeStartingComments(fileText);

        fileText = "// [ #include \"" + relativePath + "\" ]\n" + fileText + "\n// end of [ " + relativePath + " ]";

        // recursion
        return expandIncludes(fileText, currentFile);
    }

    private String removeStartingComments(String fileText) {
        StringBuilder result = new StringBuilder();
        String[] lines = fileText.split("\n");
        boolean firstComment = true;
        for (String line : lines) {
            if (!firstComment || !line.trim().startsWith("//")) {
                if (!line.trim().isEmpty()) firstComment = false;
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }
}
