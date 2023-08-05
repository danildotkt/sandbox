package io.sandbox.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "telegram_user")
public class TelegramUser {

    @Id
    private long chatId;

    private String sandboxToken;

    private String accountId;

}