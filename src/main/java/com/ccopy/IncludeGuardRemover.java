package com.ccopy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IncludeGuardRemover {

    /**
     * 파일 전체 텍스트에서 전형적인 include guard 패턴(및 #pragma once)을 제거합니다.
     *
     * @param fileText 원본 파일 텍스트
     * @return include guard가 제거된 텍스트
     */
    public static String removeIncludeGuards(String fileText) {
        // 줄 단위로 분할
        List<String> lines = new ArrayList<>(Arrays.asList(fileText.split("\n")));

        // 1. #pragma once 제거
        removePragmaOnce(lines);

        // 2. 파일 상단의 include guard 패턴 탐지:
        //    - 첫 번째 의미 있는 줄이 "#ifndef SOME_MACRO"
        //    - 두 번째 의미 있는 줄이 "#define SAME_MACRO"
        int firstMeaningful = findFirstMeaningfulLine(lines, 0);
        if (firstMeaningful == -1) {
            return String.join("\n", lines);
        }

        String ifndefLine = lines.get(firstMeaningful).trim();
        Pattern ifndefPattern = Pattern.compile("#ifndef\\s+(\\w+)");
        Matcher ifndefMatcher = ifndefPattern.matcher(ifndefLine);
        if (!ifndefMatcher.find()) {
            // include guard 패턴이 아님
            return String.join("\n", lines);
        }
        String macro = ifndefMatcher.group(1);

        // 두 번째 의미 있는 줄 확인: #define <macro>
        int secondMeaningful = findFirstMeaningfulLine(lines, firstMeaningful + 1);
        if (secondMeaningful == -1) {
            return String.join("\n", lines);
        }
        String defineLine = lines.get(secondMeaningful).trim();
        Pattern definePattern = Pattern.compile("#define\\s+(\\w+)");
        Matcher defineMatcher = definePattern.matcher(defineLine);
        if (!defineMatcher.find() || !defineMatcher.group(1).equals(macro)) {
            // 패턴이 일치하지 않으면 include guard가 아닌 것으로 간주
            return String.join("\n", lines);
        }

        // 3. guard의 끝(#endif) 찾기: include guard는 일반적으로 파일의 마지막에 위치합니다.
        //    단, 파일 내부에 다른 조건부 컴파일 블록이 있을 수 있으므로, 첫 번째 두 줄(#ifndef, #define)부터 시작해
        //    nesting count를 이용해 매칭되는 #endif를 찾습니다.
        int endifIndex = findMatchingEndif(lines, firstMeaningful);
        if (endifIndex == -1) {
            // 매칭되는 #endif가 없으면 guard 제거를 수행하지 않음.
            return String.join("\n", lines);
        }

        // 4. include guard 제거: ifndef, define, 그리고 매칭된 endif 라인을 제거하고,
        //    나머지 코드(guard 내부에 있는 실제 코드)는 그대로 유지합니다.
        //    단, guard 외의 다른 부분에는 영향을 주지 않습니다.
        List<String> resultLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            // ifndef와 define 줄, 그리고 guard를 닫는 endif 줄은 제거
            if (i == firstMeaningful || i == secondMeaningful || i == endifIndex) {
                continue;
            }
            resultLines.add(lines.get(i));
        }

        return String.join("\n", resultLines);
    }

    // #pragma once 제거 (줄 단위로 검색)
    private static void removePragmaOnce(List<String> lines) {
        for (Iterator<String> it = lines.iterator(); it.hasNext();) {
            String line = it.next().trim();
            if (line.equals("#pragma once")) {
                it.remove();
            }
        }
    }

    // 주어진 시작 인덱스부터, 의미 있는(빈 줄이나 주석이 아닌) 첫 번째 줄의 인덱스를 반환.
    private static int findFirstMeaningfulLine(List<String> lines, int start) {
        for (int i = start; i < lines.size(); i++) {
            String trimmed = lines.get(i).trim();
            if (trimmed.isEmpty() || trimmed.startsWith("//") || trimmed.startsWith("/*")) {
                continue;
            }
            return i;
        }
        return -1;
    }

    // 첫 번째 #ifndef부터 시작하여, 조건부 컴파일의 nesting을 고려하면서 매칭되는 #endif 라인의 인덱스를 찾음.
    private static int findMatchingEndif(List<String> lines, int startIndex) {
        int nesting = 0;
        for (int i = startIndex; i < lines.size(); i++) {
            String trimmed = lines.get(i).trim();
            // #if, #ifdef, #ifndef 등으로 nesting 증가
            if (trimmed.startsWith("#if") || trimmed.startsWith("#ifdef") || trimmed.startsWith("#ifndef")) {
                nesting++;
            } else if (trimmed.startsWith("#endif")) {
                nesting--;
                if (nesting == 0) {
                    return i;
                }
            }
        }
        return -1; // 매칭되는 #endif가 없는 경우
    }

    // 테스트용 main 메서드
    public static void main(String[] args) {
        String headerFile = ""
                + "#pragma once\n"
                + "\n"
                + "// 헤더 파일 시작\n"
                + "#ifndef MY_HEADER_H\n"
                + "#define MY_HEADER_H\n"
                + "\n"
                + "/**\n"
                + " * MyHeader.h\n"
                + " */\n"
                + "int myFunction();\n"
                + "\n"
                + "#endif // MY_HEADER_H\n"
                + "\n"
                + "// 파일 끝";

        String result = removeIncludeGuards(headerFile);
        System.out.println("----- 결과 -----");
        System.out.println(result);
    }
}
