package io.sandbox.user_state;

public enum UserState {

    STATE_START_REQUEST,
    STATE_START_RESPONSE,
    STATE_PORTFOLIO_RESPONSE,
    STATE_POST_ORDER_REQUEST,
    STATE_POST_ORDER_RESPONSE,
    STATE_OPERATIONS_RESPONSE,
    STATE_COMPANY_DATA_REQUEST,
    STATE_COMPANY_DATA_RESPONSE,
    STATE_DEFAULT;

    public boolean isRequest() {
        return this.name().endsWith("_REQUEST");
    }

    public boolean isResponse() {
        return this.name().endsWith("_RESPONSE");
    }
}
