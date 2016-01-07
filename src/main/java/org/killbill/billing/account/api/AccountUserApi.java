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

package org.killbill.billing.account.api;

import java.util.List;
import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.ACCOUNT_CAN_CREATE;
import static org.killbill.billing.security.Permission.ACCOUNT_CAN_UPDATE;
import static org.killbill.billing.security.Permission.ACCOUNT_CAN_ADD_EMAILS;
import static org.killbill.billing.security.Permission.ACCOUNT_CAN_DELETE_EMAILS;

/**
 * The interface {@code AccountUserApi} offers APIs related to account operations.
 */
public interface AccountUserApi extends KillbillApi {

    /**
     * @param data    the account data
     * @param context the user context
     * @return the created Account
     * @throws AccountApiException
     */
    @RequiresPermissions(ACCOUNT_CAN_CREATE)
    public Account createAccount(AccountData data, CallContext context) throws AccountApiException;

    /**
     * Updates the account by specifying the destination {@code Account} object
     * <p/>
     *
     * @param account account to be updated
     * @param context contains specific information about the call
     * @throws AccountApiException if a failure occurs
     */
    @RequiresPermissions(ACCOUNT_CAN_UPDATE)
    public void updateAccount(Account account, CallContext context) throws AccountApiException;

    /**
     * Updates the account by specifying the {@code AccountData} object
     * <p/>
     *
     * @param key     account external key
     * @param context contains specific information about the call
     * @throws AccountApiException if a failure occurs
     */
    @RequiresPermissions(ACCOUNT_CAN_UPDATE)
    public void updateAccount(String key, AccountData accountData, CallContext context) throws AccountApiException;

    /**
     * Updates the account by specifying the {@code AccountData} object
     * <p/>
     *
     * @param accountId account unique id
     * @param context   contains specific information about the call
     * @throws AccountApiException if a failure occurs
     */
    @RequiresPermissions(ACCOUNT_CAN_UPDATE)
    public void updateAccount(UUID accountId, AccountData accountData, CallContext context) throws AccountApiException;

    /**
     * Retrieves an account by specifying its external key.
     * <p/>
     * Note that the system will enforce that only one account for a given external key exists in the system
     *
     * @param key     the externalKey for the account
     * @param context the user context
     * @return the account
     * @throws AccountApiException if there is no such account
     */
    public Account getAccountByKey(String key, TenantContext context) throws AccountApiException;

    /**
     * @param accountId the unique id for the account
     * @param context   the user context
     * @return the account
     * @throws AccountApiException if there is no such account
     */
    public Account getAccountById(UUID accountId, TenantContext context) throws AccountApiException;

    /**
     * Find all accounts having their name, email, external_key or company_name matching the search key
     *
     * @param searchKey the search key
     * @param offset    the offset of the first result
     * @param limit     the maximum number of results to retrieve
     * @param context   the user context
     * @return the list of accounts matching this search key for that tenant
     */
    public Pagination<Account> searchAccounts(String searchKey, Long offset, Long limit, TenantContext context);

    /**
     * @param context the user context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return the list of accounts for that tenant
     */
    public Pagination<Account> getAccounts(Long offset, Long limit, TenantContext context);

    /**
     * @param externalKey the externalKey for the account
     * @param context     the user context
     * @return the unique id for that account
     * @throws AccountApiException if there is no such account
     */
    public UUID getIdFromKey(String externalKey, TenantContext context) throws AccountApiException;

    /**
     * @param accountId the account unique id
     * @param context   the user context
     * @return the list of emails configured for that account
     */
    public List<AccountEmail> getEmails(UUID accountId, TenantContext context);

    /**
     * @param accountId the account unique id
     * @param email     the email to be added
     * @param context   the user context
     */
    @RequiresPermissions(ACCOUNT_CAN_ADD_EMAILS)
    public void addEmail(UUID accountId, AccountEmail email, CallContext context) throws AccountApiException;

    /**
     * @param accountId the account unique id
     * @param email     the email to be removed
     * @param context   the user context
     */
    @RequiresPermissions(ACCOUNT_CAN_DELETE_EMAILS)
    public void removeEmail(UUID accountId, AccountEmail email, CallContext context);

    /**
     * @param parentAccountId the parent account unique id
     * @param context   the user context
     * @return the list of children accounts for that parent account id
     */
    public List<Account> getChildrenAccounts(UUID parentAccountId, TenantContext context) throws AccountApiException;

}
