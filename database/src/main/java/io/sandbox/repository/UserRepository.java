package io.sandbox.repository;

import io.sandbox.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TelegramUser, Long> {
}
