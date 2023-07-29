package io.sandbox.dto;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class TelegramUser {
    private long id;
    private String token;
    private String accountId;
}
