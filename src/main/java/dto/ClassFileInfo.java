package dto;

import java.nio.file.Path;

public class ClassFileInfo {
    private final Path classFilePath;
    private final String packageName;

    public ClassFileInfo(String packageName, Path classFilePath) {
        this.classFilePath = classFilePath;
        this.packageName = packageName;
    }

    public Path getClassFilePath() {
        return classFilePath;
    }

    public String getPackageName() {
        return packageName;
    }
}
