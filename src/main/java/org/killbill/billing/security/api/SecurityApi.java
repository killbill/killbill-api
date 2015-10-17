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

package org.killbill.billing.security.api;

import java.util.List;
import java.util.Set;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.security.Logical;
import org.killbill.billing.security.Permission;
import org.killbill.billing.security.RequiresPermissions;
import org.killbill.billing.security.SecurityApiException;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;

import static org.killbill.billing.security.Permission.TENANT_CAN_CREATE;
import static org.killbill.billing.security.Permission.USER_CAN_CREATE;

public interface SecurityApi extends KillbillApi {

    /**
     * Perform a login attempt.
     *
     * @param principal   account identity (e.g. username)
     * @param credentials account credentials (e.g. password)
     */
    public void login(Object principal, Object credentials);

    /**
     * Log out the current user and invalidate and/or remove any associated entities (e.g. session).
     */
    public void logout();

    /**
     *
     * @return true if the subscjet is authenticated
     */
    public boolean isSubjectAuthenticated();

    /**
     * Return the set of permissions for the currently logged-in user.
     *
     * @param context tenant context
     * @return the set of permissions for the current user
     */
    Set<Permission> getCurrentUserPermissions(TenantContext context);

    /**
     * Check the current user has the set of permissions.
     *
     * @param permissions set of permissions to check
     * @param logical     rule to use for multiple permissions
     * @param context     tenant context
     * @throws SecurityException
     */
    void checkCurrentUserPermissions(List<Permission> permissions, Logical logical, TenantContext context) throws SecurityApiException;

    /**
     * Add a user with roles in the Shiro store (JDBCRealm)
     *
     * @param username       the username
     * @param clearPassword  the password (in clear)
     * @param roles          the list of (existing) roles
     * @param context        context (does not include tenant nor account info)
     * @throws SecurityApiException
     */
    @RequiresPermissions(USER_CAN_CREATE)
    public void addUserRoles(String username, String clearPassword, List<String> roles, CallContext context) throws SecurityApiException;

    /**
     * Update password for the user
     *
     * @param username       the username
     * @param clearPassword  the password (in clear)
     * @param context        context (does not include tenant nor account info)
     * @throws SecurityApiException
     */
    @RequiresPermissions(USER_CAN_CREATE)
    public void updateUserPassword(String username, String clearPassword, CallContext context) throws SecurityApiException;

    /**
     *
     * Update the roles associated with the user (only the specified roles will be in effect)
     *
     * @param username       the username
     * @param roles          the new roles
     * @param context        context (does not include tenant nor account info)
     * @throws SecurityApiException
     */
    @RequiresPermissions(USER_CAN_CREATE)
    public void updateUserRoles(String username, List<String> roles, CallContext context) throws SecurityApiException;

    /**
     * Invalidate a user
     *
     * @param username       the username
     * @param context        context (does not include tenant nor account info)
     * @throws SecurityApiException
     */
    @RequiresPermissions(USER_CAN_CREATE)
    public void invalidateUser(String username, CallContext context) throws SecurityApiException;

    /**
     * Retrieves the roles associated to a user in the Shiro store (JDBCRealm)
     *
     * @param username      the username
     * @param tenantContext dummy context
     * @return
     */
    public List<String> getUserRoles(String username, final TenantContext tenantContext);

    /**
     * Add a role definition  in the Shiro store (JDBCRealm)
     * @param role        the role name
     * @param permissions the list of permissions
     * @param context     context (does not include tenant nor account info)
     * @throws SecurityApiException
     *
     * @see org.killbill.billing.security.Permission
     */
    @RequiresPermissions(USER_CAN_CREATE)
    public void addRoleDefinition(String role, List<String> permissions, CallContext context) throws SecurityApiException;

    /**
     * Retrieves the list of permissions associated to that role  in the Shiro store (JDBCRealm)
     * @param role           the role name
     * @param tenantContext  dummy context
     * @return
     */
    public List<String> getRoleDefinition(final String role, final TenantContext tenantContext);

}
