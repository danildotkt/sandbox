package io.sandbox.user_state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserStateTest {

    @Test
    void testEnumValues() {
        UserState[] expectedValues = {
                UserState.STATE_START_REQUEST,
                UserState.STATE_START_RESPONSE,
                UserState.STATE_PORTFOLIO,
                UserState.STATE_POST_ORDER_REQUEST,
                UserState.STATE_POST_ORDER_OPERATION_TYPE,
                UserState.STATE_POST_ORDER_RESPONSE,
                UserState.STATE_OPERATIONS,
                UserState.STATE_COMPANY_DATA_REQUEST,
                UserState.STATE_COMPANY_DATA_RESPONSE,
                UserState.STATE_DEFAULT
        };

        UserState[] actualValues = UserState.values();

        assertEquals(expectedValues.length, actualValues.length);
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], actualValues[i]);
        }
    }
}