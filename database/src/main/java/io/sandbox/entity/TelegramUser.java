package io.sandbox.entity;


import lombok.*;
import javax.persistence.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "telegram_user_table")
public class TelegramUser {

    @Id
    private long chatId;

    private String sandboxToken;

    private String sandboxId;

}