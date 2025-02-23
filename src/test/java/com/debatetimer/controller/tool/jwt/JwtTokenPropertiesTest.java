package com.debatetimer.controller.tool.jwt;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.debatetimer.exception.custom.DTInitializationException;
import com.debatetimer.exception.errorcode.InitializationErrorCode;
import java.time.Duration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class JwtTokenPropertiesTest {

    @Nested
    class ValidateSecretKey {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\n\t"})
        void 시크릿_키가_비어있을_경우_예외를_발생시킨다(String emptyKey) {
            Duration accessTokenExpiration = Duration.ofMinutes(1);
            Duration refreshTokenExpiration = Duration.ofMinutes(5);

            assertThatThrownBy(() -> new JwtTokenProperties(emptyKey, accessTokenExpiration, refreshTokenExpiration))
                    .isInstanceOf(DTInitializationException.class)
                    .hasMessage(InitializationErrorCode.JWT_SECRET_KEY_EMPTY.getMessage());
        }
    }

    @Nested
    class ValidateToken {

        @Test
        void 유효_기간이_비어있을_경우_예외를_발생시킨다() {
            String secretKey = "testtesttest";

            assertAll(
                    () -> assertThatThrownBy(() -> new JwtTokenProperties(secretKey, null, Duration.ofMinutes(5)))
                            .isInstanceOf(DTInitializationException.class)
                            .hasMessage(InitializationErrorCode.JWT_TOKEN_DURATION_EMPTY.getMessage()),
                    () -> assertThatThrownBy(() -> new JwtTokenProperties(secretKey, Duration.ofMinutes(1), null))
                            .isInstanceOf(DTInitializationException.class)
                            .hasMessage(InitializationErrorCode.JWT_TOKEN_DURATION_EMPTY.getMessage())
            );
        }

        @Test
        void 유효_기간이_음수일_경우_예외를_발생시킨다() {
            String secretKey = "testtesttest";
            Duration negativeExpiration = Duration.ofMinutes(-1);

            assertAll(
                    () -> assertThatThrownBy(
                            () -> new JwtTokenProperties(secretKey, negativeExpiration, Duration.ofMinutes(5)))
                            .isInstanceOf(DTInitializationException.class)
                            .hasMessage(InitializationErrorCode.JWT_TOKEN_DURATION_INVALID.getMessage()),
                    () -> assertThatThrownBy(
                            () -> new JwtTokenProperties(secretKey, Duration.ofMinutes(1), negativeExpiration))
                            .isInstanceOf(DTInitializationException.class)
                            .hasMessage(InitializationErrorCode.JWT_TOKEN_DURATION_INVALID.getMessage())
            );
        }
    }
}
