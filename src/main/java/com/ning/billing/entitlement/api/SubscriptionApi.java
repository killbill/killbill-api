package com.ning.billing.entitlement.api;

import com.ning.billing.util.callcontext.TenantContext;

import java.util.List;
import java.util.UUID;

/**
 * API to manage the retrieval of <code>Subscription</code> information.
 */
public interface SubscriptionApi {

    /**
     * Retrieves a <code>Subscription</code> from the entitlementId
     *
     * @param entitlementId the id of the entitlement associated with the subscription
     * @param context       the context
     *
     * @return the subscription
     *
     * @throws SubscriptionApiException if it odes not exist
     */
    Subscription getSubscriptionFromEntitlementId(UUID entitlementId, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>Subscription</code> attached to the base entitlement.
     *
     * @param baseEntitlementId     the id of the base entitlement
     * @param context               the context
     *
     * @return                      a list of subscriptions
     *
     * @throws SubscriptionApiException if the baseEntitlementId does not exist.
     */
    public List<Subscription> getAllSubscriptionsFromBaseEntitlementId(UUID baseEntitlementId, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>Subscription</code> for a given account and matching an external key.
     *
     * @param accountId     the account id
     * @param externalKey   the external key
     * @param context       the context
     *
     * @return              a list of Subscriptions
     *
     * @throws SubscriptionApiException if the account does not exist.
     */
    public List<Subscription> getAllSubscriptionsForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context) throws SubscriptionApiException;

    /**
     *
     * Retrieves all the <code>Subscription</code> for a given account.
     *
     * @param accountId     the account id
     * @param context       the context
     *
     * @return              a list of Subscriptions
     *
     * @throws SubscriptionApiException if the account does not exist
     */
    public List<Subscription> getAllSubscriptionsFromAccountId(UUID accountId, TenantContext context) throws SubscriptionApiException;


    /**
     *
     * Returns a list of  <code>SubscriptionBundleTimeline</code> for the set of <code>Subscription</code> associated with the account
     *
     * @param accountId     the account id
     * @param context       the context
     *
     * @return              the timeline
     *
     * @throws SubscriptionApiException if the account id or external key don't exist
     */
    public List<SubscriptionBundleTimeline> getSubscriptionBundleTimeline(UUID accountId, TenantContext context)
            throws SubscriptionApiException;

    /**
     * Returns a <code>SubscriptionBundleTimeline</code> for the set of <code>Subscription</code> associated with the account and external key.
     *
     * @param accountId     the account id
     * @param externalKey   the external key
     * @param context       the context
     *
     * @return              the timeline
     *
     * @throws SubscriptionApiException if the account id or external key don't exist
     */
    public SubscriptionBundleTimeline getSubscriptionBundleTimeline(UUID accountId, String externalKey, TenantContext context)
            throws SubscriptionApiException;



}
