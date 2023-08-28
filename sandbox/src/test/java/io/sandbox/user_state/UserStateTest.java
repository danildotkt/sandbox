package io.sandbox.user_state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserStateTest {

    @Test
    void testIsRequest() {
        assertTrue(UserState.STATE_START_REQUEST.isRequest());
        assertTrue(UserState.STATE_POST_ORDER_REQUEST.isRequest());
        assertTrue(UserState.STATE_COMPANY_DATA_REQUEST.isRequest());
        assertFalse(UserState.STATE_START_RESPONSE.isRequest());
        assertFalse(UserState.STATE_PORTFOLIO_RESPONSE.isRequest());
        assertFalse(UserState.STATE_POST_ORDER_RESPONSE.isRequest());
        assertFalse(UserState.STATE_OPERATIONS_RESPONSE.isRequest());
        assertFalse(UserState.STATE_COMPANY_DATA_RESPONSE.isRequest());
    }

    @Test
    void testIsResponse() {
        assertFalse(UserState.STATE_START_REQUEST.isResponse());
        assertFalse(UserState.STATE_POST_ORDER_REQUEST.isResponse());
        assertFalse(UserState.STATE_COMPANY_DATA_REQUEST.isResponse());
        assertTrue(UserState.STATE_START_RESPONSE.isResponse());
        assertTrue(UserState.STATE_PORTFOLIO_RESPONSE.isResponse());
        assertTrue(UserState.STATE_POST_ORDER_RESPONSE.isResponse());
        assertTrue(UserState.STATE_OPERATIONS_RESPONSE.isResponse());
        assertTrue(UserState.STATE_COMPANY_DATA_RESPONSE.isResponse());
    }
}