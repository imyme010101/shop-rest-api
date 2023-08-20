package com.imyme010101.restapi.common.response;

/**
 * [공통] API Response 결과의 반환 값을 관리
 */
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor(staticName = "of")
public class ApiResponse<D> {
    private final int status;
    private final String resultMsg;
    private final D result;
}
