package com.grinder.domain.feed.model;

import com.grinder.common.annotation.Name;
import com.grinder.common.utils.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

    @Setter
    @Name(description = "첨부파일 URL 리스트")
    private List<String> attachments;

    private boolean isLike;

    private boolean isMine;

    public FeedMember(String feedId, Long feedMemberId, String nickname, String memberImage, String content, int rating, LocalDateTime period, int likes, Long memberId, boolean isLike) {
        this.feedId = feedId;
        this.nickname = nickname;
        this.memberImage = memberImage;
        this.content = content;
        this.rating = rating;
        this.period = DateUtils.parseYYYYMMDD(period);
        this.likes = likes;
        this.isLike = isLike;
        this.isMine = feedMemberId.equals(memberId);
    }
}
