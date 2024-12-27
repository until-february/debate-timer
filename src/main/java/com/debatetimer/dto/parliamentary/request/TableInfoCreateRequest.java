package com.debatetimer.dto.parliamentary.request;

import com.debatetimer.domain.member.Member;
import com.debatetimer.domain.parliamentary.ParliamentaryTable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TableInfoCreateRequest(
        @Schema(description = "테이블 이름", example = "테이블1")
        @NotBlank
        String name,

        @Schema(description = "토론 유형", example = "PARLIAMENTARY")
        @NotBlank
        String type,

        @Schema(description = "토론 주제", example = "촉법소년 연령 인하")
        @NotNull
        String agenda
) {

    public ParliamentaryTable toTable(Member member, int duration) {
        return new ParliamentaryTable(member, name, agenda, duration);
    }
}
