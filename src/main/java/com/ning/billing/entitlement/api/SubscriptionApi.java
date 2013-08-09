package com.ning.billing.entitlement.api;

import com.ning.billing.util.callcontext.TenantContext;

import java.util.List;
import java.util.UUID;

/**
 * API to manage the retrieval of <code>Subscription</code> information.
 */
public interface SubscriptionApi {

    /**
     * Retrieves a <code>Subscription</code> For the entitlementId
     *
     * @param entitlementId the id of the entitlement associated with the subscription
     * @param context       the context
     *
     * @return the subscription
     *
     * @throws SubscriptionApiException if it odes not exist
     */
    Subscription getSubscriptionForEntitlementId(UUID entitlementId, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>Subscription</code> attached to the base entitlement.
     *
     * @param bundleId              the id of the bundle
     * @param context               the context
     *
     * @return                      a list of subscriptions
     *
     * @throws SubscriptionApiException if the baseEntitlementId does not exist.
     */
    public SubscriptionBundle getAllSubscriptionsForBundle(UUID bundleId, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>SubscriptionBundle</code> for a given account and matching an external key.
     *
     * @param accountId     the account id
     * @param externalKey   the external key
     * @param context       the context
     *
     * @return              a list of Subscriptions
     *
     * @throws SubscriptionApiException if the account does not exist.
     */
    public SubscriptionBundle getSubscriptionBundleForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>SubscriptionBundle</code> for a given account.
     *
     * @param accountId     the account id
     * @param context       the context
     *
     * @return              a list of Subscriptions
     *
     * @throws SubscriptionApiException if the account does not exist
     */
    public List<SubscriptionBundle> getSubscriptionBundlesForAccountId(UUID accountId, TenantContext context) throws SubscriptionApiException;

}
