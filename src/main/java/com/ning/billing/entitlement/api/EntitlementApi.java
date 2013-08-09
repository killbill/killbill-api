/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.entitlement.api;

import com.ning.billing.catalog.api.BillingActionPolicy;
import com.ning.billing.catalog.api.PlanPhaseSpecifier;
import com.ning.billing.util.callcontext.CallContext;
import com.ning.billing.util.callcontext.TenantContext;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.UUID;

/**
 * Primary API to manage the creation and retrieval of <code>Entitlement</code>.
 *
 */
public interface EntitlementApi {

    /**
     *
     * Create a new entitlement for that account.
     * <p>
     * The <code>PlanPhaseSpecifier<code/> should refer to a <code>ProductCategory.BASE</code> of
     * <code>ProductCategory.STANDALONE</code>.
     *
     * @param accountId     the account id
     * @param spec          the product specification for that new entitlement
     * @param externalKey   the external key for that entitlement
     * @param context       the context
     * @return              a new entitlement
     *
     * @throws EntitlementApiException if the system fail to create the <code>Entitlement</code>
     */
    public Entitlement createBaseEntitlement(UUID accountId, PlanPhaseSpecifier spec, String externalKey, CallContext context)
            throws EntitlementApiException;

    /**
     *
     * Adds an ADD_ON entitlement to previously created entitlement.
     * <p>
     * The <code>PlanPhaseSpecifier<code/> should refer to a <code>ProductCategory.ADD_ON</code>.
     * The new entitlement will be bundled using the externalKey that was specified when creating the
     * base entitlement.
     *
     * @param baseEntitlementId the id of the base entitlement
     * @param spec              the product specification for that new entitlement
     * @param context           the context
     * @return                  a new entitlement
     *
     * @throws EntitlementApiException if the system fail to create the <code>Entitlement</code>
     */
    public Entitlement addEntitlement(UUID baseEntitlementId, PlanPhaseSpecifier spec, CallContext context)
            throws EntitlementApiException;


    /**
     * Will block all entitlements associated with the base entitlement. If there are no ADD_ONN this is only the base entitlement.
     *
     * @param baseEntitlementId the id of the base entitlement
     * @param serviceName       the service name of who is blocking
     * @param effectiveDate     the date at which the operation should occur
     * @param context           the context
     *
     * @throws EntitlementApiException if the system fail to find the base <code>Entitlement</code>
     */
    public void block(UUID baseEntitlementId, String serviceName, LocalDate effectiveDate, CallContext context)
            throws EntitlementApiException;

    /**
     * Will unblock all entitlements associated with the base entitlement. If there are no ADD_ONN this is only the base entitlement.
     *
     * @param baseEntitlementId the id of the base entitlement
     * @param serviceName       the service name of who is blocking
     * @param effectiveDate     the date at which the operation should occur
     * @param context           the context
     *
     * @throws EntitlementApiException if the system fail to find the base <code>Entitlement</code>
     */
    public void unblock(UUID baseEntitlementId, String serviceName, LocalDate effectiveDate, CallContext context)
            throws EntitlementApiException;

    /**
     *
     * Retrieves an <code>Entitlement</code> using its id.
     *
     * @param id        the id of the entitlement
     * @param context   the context
     * @return          the entitlement
     *
     * @throws EntitlementApiException if the entitlement does not exist
     */
    public Entitlement getEntitlementForId(UUID id, TenantContext context) throws EntitlementApiException;

    /**
     *
     * Retrieves all the <code>Entitlement</code> attached to the base entitlement.
     *
     * @param baseEntitlementId     the id of the base entitlement
     * @param context               the context
     * @return                      a list of entitlements
     *
     * @throws EntitlementApiException if the entitlement does not exist
     */
    public List<Entitlement> getAllEntitlementsForBaseId(UUID baseEntitlementId, TenantContext context)
            throws EntitlementApiException;

    /**
     *
     * Retrieves all the <code>Entitlement</code> for a given account and matching an external key.
     *
     * @param accountId     the account id
     * @param externalKey   the external key
     * @param context       the context
     *
     * @return              a list of entitlements
     *
     * @throws EntitlementApiException if the account does not exist
     */
    public List<Entitlement> getAllEntitlementsForAccountIdAndExternalKey(UUID accountId, String externalKey, TenantContext context)
            throws EntitlementApiException;

    /**
     *
     * Retrieves all the <code>Entitlement</code> for a given account.
     *
     * @param accountId     the account id
     * @param context       the context
     *
     * @return              a list of entitlements
     *
     * @throws EntitlementApiException if the account does not exist
     */
    public List<Entitlement> getAllEntitlementsForAccountId(UUID accountId, TenantContext context) throws EntitlementApiException;

    /**
     * Transfer all the <code>Enitlement</code> For the source account and matching the external key to the destination account.
     * <p>
     * The date is interpreted by the system to be in the timezone specified at the destination <code>Account</code>.
     * <p>
     * The <code>Entitlement</code> on the source account will be cancelled at effective date and the <code>Entitlement</code>
     * on the destination account will be created at the effectiveDate.
     *
     * @param sourceAccountId   the unique id for the account on which the bundle will be transferred For
     * @param destAccountId     the unique id for the account on which the bundle will be transferred to
     * @param externalKey       the externalKey for the bundle
     * @param effectiveDate     the date at which this transfer should occur
     * @param context           the user context
     *
     * @return                  the id of the newly created base entitlement for the destination account
     *
     * @throws EntitlementApiException if the system could not transfer the entitlements
     *
     */
    public UUID transferEntitlements(final UUID sourceAccountId, final UUID destAccountId, final String externalKey, final LocalDate effectiveDate,
                                     final CallContext context)
            throws EntitlementApiException;

    /**
     * Transfer all the <code>Entitlement</code> for the source account and matching the external key to the destination account.
     * <p>
     * The date is interpreted by the system to be in the timezone specified at the destination <code>Account</code>.
     * <p>
     * The <code>Entitlement</code> on the source account will be cancelled at effective date and the <code>Entitlement</code>
     * on the destination account will be created at the effectiveDate. The <tt>billingPolicy</tt> will be used to override
     * the default billing behavior for the cancellation of the subscriptions on the source account.
     *
     *
     * @param sourceAccountId   the unique id for the account on which the bundle will be transferred For
     * @param destAccountId     the unique id for the account on which the bundle will be transferred to
     * @param externalKey       the externalKey for the bundle
     * @param effectiveDate     the date at which this transfer should occur
     * @param billingPolicy     the override billing policy
     * @param context           the user context
     *
     * @return                  the id of the newly created base entitlement for the destination account
     *
     * @throws EntitlementApiException if the system could not transfer the entitlements
     *
     */
    public UUID transferEntitlementsOverrideBillingPolicy(final UUID sourceAccountId, final UUID destAccountId, final String externalKey, final LocalDate effectiveDate,
                                                          final BillingActionPolicy billingPolicy, final CallContext context)
            throws EntitlementApiException;

}
