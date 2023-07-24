package io.sandbox.user_state;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public enum UserState {
    STATE_START_REQUEST,
    STATE_START_RESPONSE,
    STATE_PORTFOLIO,
    STATE_POST_ORDER_REQUEST,
    STATE_POST_ORDER_OPERATION_TYPE,
    STATE_POST_ORDER_RESPONSE,
    STATE_OPERATIONS,
    STATE_COMPANY_DATA_REQUEST,
    STATE_COMPANY_DATA_TYPE,
    STATE_COMPANY_DATA_RESPONSE,
    STATE_DEFAULT;

    private final Map<Long, UserState> map = new ConcurrentHashMap<>();

    public void setUserState(long chatId, UserState state) {
        map.put(chatId, state);
    }

    public UserState getUserState(long chatId) {
        return map.get(chatId);
    }

    public void removeUserState(long chatId) {
        map.remove(chatId);
    }
}
