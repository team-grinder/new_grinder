package com.grinder.domain.feed.model;

import com.grinder.common.annotation.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FeedMember {
    private String feedId;

    private String nickname;

    private String memberImage;

    private String content;

    private int rating;

    @Name(description = "yyyy-MM-dd 형식")
    private String period;

    private int likes;

    @Name(description = "첨부파일 URL 리스트")
    private List<String> attachments;

    private boolean isLike;

    private boolean isMine;

    public FeedMember(String feedId, String nickname, String memberImage, String content, int rating, String period, int likes, List<String> attachments, Long memberId) {
        this.feedId = feedId;
        this.nickname = nickname;
        this.memberImage = memberImage;
        this.content = content;
        this.rating = rating;
        this.period = period;
        this.likes = likes;
        this.attachments = attachments;
        this.isLike = isLike;
        this.isMine = isMine;
    }
}
