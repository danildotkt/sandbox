package io.sandbox.entity;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUser {

    private long chatId;

    private String sandboxToken;

    private String accountId;

}
