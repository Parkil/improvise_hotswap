package javafile;

import exception.runtime.GlobalException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaFileUtil {
    private JavaFileUtil(){}

    public static String getPackage(Path javaFilePath) {
        String packageStr = "";

        try {
            List<String> packageLineList = Files.readAllLines(javaFilePath, StandardCharsets.UTF_8).stream()
                    .filter(line -> line.startsWith("package ")).collect(Collectors.toList());

            if(!packageLineList.isEmpty()) {
                String tempLineStr = packageLineList.get(0);

                Pattern pattern = Pattern.compile("package\\s+(.*);");
                Matcher matcher = pattern.matcher(tempLineStr);

                if(matcher.find()) {
                    packageStr = matcher.group(1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new GlobalException(e);
        }

        return packageStr;
    }
}