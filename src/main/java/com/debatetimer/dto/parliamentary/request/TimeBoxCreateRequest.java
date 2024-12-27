package com.debatetimer.dto.parliamentary.request;

import com.debatetimer.domain.BoxType;
import com.debatetimer.domain.Stance;
import com.debatetimer.domain.parliamentary.ParliamentaryTable;
import com.debatetimer.domain.parliamentary.ParliamentaryTimeBox;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record TimeBoxCreateRequest(
        @Schema(description = "입장", example = "PROS")
        @NotBlank
        String stance,

        @Schema(description = "발언 유형", example = "OPENING")
        @NotBlank
        String type,

        @Schema(description = "발언 시간(초)", example = "175")
        @Positive
        int time,

        @Schema(description = "발언자 번호", example = "1", nullable = true)
        Integer speakerNumber //TODO Long으로 변환은 어떤지?
) {

    public ParliamentaryTimeBox toTimeBox(ParliamentaryTable parliamentaryTable, int sequence) {
        return new ParliamentaryTimeBox(
                parliamentaryTable,
                sequence,
                Stance.valueOf(stance),
                BoxType.valueOf(type),
                time,
                speakerNumber
        );
    }
}
