package ch.ffhs.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaryEntry {
    // Getters and Setters
    private String title;
    private String content;
    private String user_id;
    private int entryId;

    // Constructors
    public DiaryEntry() {
    }

    public DiaryEntry(String title, String content, String user_id) {
        this.title = title;
        this.content = content;
        this.user_id = user_id;
    }
}
