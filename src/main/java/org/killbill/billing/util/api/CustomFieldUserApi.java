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

package org.killbill.billing.util.api;

import java.util.List;
import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.ObjectType;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.customfield.CustomField;
import org.killbill.billing.util.entity.Pagination;

import static org.killbill.billing.security.Permission.CUSTOM_FIELDS_CAN_ADD;
import static org.killbill.billing.security.Permission.CUSTOM_FIELDS_CAN_DELETE;

public interface CustomFieldUserApi extends KillbillApi {

    /**
     * Find all custom fields having their object type, field name or value matching the search key
     *
     * @param searchKey the search key
     * @param offset    the offset of the first result
     * @param limit     the maximum number of results to retrieve
     * @param context   the user context
     * @return the list of custom fields matching this search key for that tenant
     */
    public Pagination<CustomField> searchCustomFields(String searchKey, Long offset, Long limit, TenantContext context);

    /**
     * @param context the user context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return the list of custom fields for that tenant
     */
    public Pagination<CustomField> getCustomFields(Long offset, Long limit, TenantContext context);

    /**
     * @param fields  the list of fields to add
     * @param context the call context
     * @throws CustomFieldApiException
     */
    @RequiresPermissions(CUSTOM_FIELDS_CAN_ADD)
    void addCustomFields(List<CustomField> fields, CallContext context) throws CustomFieldApiException;

    /**
     * @param fields
     * @param context
     * @throws CustomFieldApiException
     */
    @RequiresPermissions(CUSTOM_FIELDS_CAN_DELETE)
    void removeCustomFields(List<CustomField> fields, CallContext context) throws CustomFieldApiException;

    /**
     * @param objectId   the object id
     * @param objectType the object type
     * @param context    the call context
     * @return the list of custom fields associated with that object
     */
    List<CustomField> getCustomFieldsForObject(UUID objectId, ObjectType objectType, TenantContext context);

    /**
     * @param accountId  the account id
     * @param objectType the object type
     * @param context    the call context
     * @return the list of custom fields associated with that account for the specified type
     */
    List<CustomField> getCustomFieldsForAccountType(UUID accountId, ObjectType objectType, TenantContext context);

    /**
     * @param accountId the account id
     * @param context   the call context
     * @return the list of custom fields associated with that account
     */
    List<CustomField> getCustomFieldsForAccount(UUID accountId, TenantContext context);
}
