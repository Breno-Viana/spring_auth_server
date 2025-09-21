package br.com.gdev.spring_auth_server.infra.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String ERROR_MESSAGE,
        LocalDateTime TIMESTAMP,
        Integer STATUS_CODE,
        List<ErrorFields> ERRORS_FIELDS
) {
}
