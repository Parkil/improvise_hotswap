package config;

import java.nio.file.Path;

public class Config {

    private Config(){}

    //todo 외부 설정파일이나 기타 방법으로 ROOT_PATH를 설정할수 있도록 변경
    public static final Path WATCH_ROOT_PATH = Path.of("src", "main", "java");
}