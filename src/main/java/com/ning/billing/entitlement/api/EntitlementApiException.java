package com.ning.billing.entitlement.api;

import com.ning.billing.BillingExceptionBase;
import com.ning.billing.ErrorCode;
import com.ning.billing.account.api.AccountApiException;
import com.ning.billing.subscription.api.user.SubscriptionUserApiException;

public class EntitlementApiException extends BillingExceptionBase {


    public EntitlementApiException(final AccountApiException e) {
        super(e, e.getCode(), e.getMessage());
    }

    public EntitlementApiException(final SubscriptionUserApiException e) {
        super(e, e.getCode(), e.getMessage());
    }

    public EntitlementApiException(final Throwable e, final ErrorCode code, final Object... args) {
        super(e, code, args);
    }

    public EntitlementApiException(final Throwable e, final int code, final String message) {
        super(e, code, message);
    }

    public EntitlementApiException(final ErrorCode code, final Object... args) {
        super(code, args);
    }
}
