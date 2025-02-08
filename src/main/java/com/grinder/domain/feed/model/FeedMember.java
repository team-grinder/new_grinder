package com.grinder.domain.feed.model;

import com.grinder.common.annotation.Name;
import com.grinder.common.utils.DateUtils;
import com.grinder.domain.image.model.ImageTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class FeedMember {
    private Long feedId;

    private String nickname;

    private String memberImageUrl;

    private String content;

    private int grade;

    @Name(description = "yyyy-MM-dd 형식")
    private String createDate;

    private long likes;

    private long commentCount;

    @Setter
    @Name(description = "첨부파일 URL 리스트")
    private List<ImageTag> imageTagList;

    private boolean isLike;

    private boolean isMine;


    public FeedMember(Long feedId, String nickname, String memberImageUrl, String content, int grade, LocalDateTime createDate, boolean isMine, long likes, boolean isLike) {
        this.feedId = feedId;
        this.nickname = nickname;
        this.memberImageUrl = memberImageUrl;
        this.content = content;
        this.grade = grade;
        this.createDate = DateUtils.parseYYYYMMDD(createDate);
        this.likes = likes;
        this.isLike = isLike;
        this.isMine = isMine;
    }

    public FeedMember(Long feedId, String nickname, String memberImageUrl, String content, int grade, LocalDateTime createDate, boolean isMine, long likes, long commentCount, boolean isLike) {
        this.feedId = feedId;
        this.nickname = nickname;
        this.memberImageUrl = memberImageUrl;
        this.content = content;
        this.grade = grade;
        this.createDate = DateUtils.parseYYYYMMDD(createDate);
        this.likes = likes;
        this.commentCount = commentCount;
        this.isLike = isLike;
        this.isMine = isMine;
    }
}
