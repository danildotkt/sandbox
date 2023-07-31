package io.sandbox.entity;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser {
    private long chatId;

    private String sandboxToken;

    private String sandboxId;
}
