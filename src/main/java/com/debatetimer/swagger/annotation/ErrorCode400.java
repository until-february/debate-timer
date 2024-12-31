package com.debatetimer.swagger.annotation;

import com.debatetimer.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "400",
        description = "클라이언트 입력 오류",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
)
public @interface ErrorCode400 {

    @AliasFor(annotation = ApiResponse.class, attribute = "description")
    String description() default "클라이언트 입력 오류";
}
