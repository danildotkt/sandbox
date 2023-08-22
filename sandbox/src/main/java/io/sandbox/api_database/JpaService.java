package io.sandbox.api_database;

public interface JpaService {

    boolean isExist(long chatId);

    String getSandboxToken(long chatId);

    String getAccountId(long chatId);

}
