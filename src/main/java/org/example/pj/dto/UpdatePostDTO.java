package org.example.pj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostDTO {
    private int postId;
    private String title;
    private String text;
    private String tag;
    private String date;
}
