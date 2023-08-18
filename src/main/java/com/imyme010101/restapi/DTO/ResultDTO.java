package com.imyme010101.restapi.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor(staticName = "of")
public class ResultDTO<D> {
    private final String code;
    private final String message;
    private final D data;
}