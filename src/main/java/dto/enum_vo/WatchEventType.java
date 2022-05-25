package dto.enum_vo;

public enum WatchEventType {
    ENTRY_CREATE("파일 등록"),
    ENTRY_DELETE("파일 삭제"),
    ENTRY_MODIFY("파일 수정"),
    NONE("해당사항 없음");

    private final String desc;
    WatchEventType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
