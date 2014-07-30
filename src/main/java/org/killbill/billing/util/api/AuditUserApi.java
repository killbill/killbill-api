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
import org.killbill.billing.util.audit.AccountAuditLogs;
import org.killbill.billing.util.audit.AccountAuditLogsForObjectType;
import org.killbill.billing.util.audit.AuditLog;
import org.killbill.billing.util.callcontext.TenantContext;

public interface AuditUserApi extends KillbillApi {

    /**
     * Retrieve all audit logs (for all objects) for a given account
     *
     * @param accountId  account id
     * @param auditLevel audit level (verbosity)
     * @param context    the tenant context
     * @return all audit logs for this account
     */
    public AccountAuditLogs getAccountAuditLogs(UUID accountId, AuditLevel auditLevel, TenantContext context);

    /**
     * Retrieve all audit logs (for all objects of a given type) for a given account
     *
     * @param accountId  account id
     * @param objectType the type of object
     * @param auditLevel audit level (verbosity)
     * @param context    the tenant context
     * @return all audit logs for this account
     */
    public AccountAuditLogsForObjectType getAccountAuditLogs(UUID accountId, ObjectType objectType, AuditLevel auditLevel, TenantContext context);

    /**
     * Get all the audit entries for a given object.
     *
     * @param objectId   the object id
     * @param objectType the type of object
     * @param auditLevel audit level (verbosity)
     * @param context    the tenant context
     * @return all audit entries for that object
     */
    public List<AuditLog> getAuditLogs(UUID objectId, ObjectType objectType, AuditLevel auditLevel, TenantContext context);
}
