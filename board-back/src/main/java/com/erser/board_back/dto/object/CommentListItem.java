package com.erser.board_back.dto.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentListItem {
    private String nickname;
    private String profileImage;
    private String writeDateTime;
    private String content;
}
