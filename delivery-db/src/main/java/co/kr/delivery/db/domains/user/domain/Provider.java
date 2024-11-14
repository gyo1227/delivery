package co.kr.delivery.db.domains.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    LOCAL("로컬"),
    KAKAO("카카오"),
    NAVER("네이버"),
    GOOGLE("구글"),
    ;

    private final String type;
}
