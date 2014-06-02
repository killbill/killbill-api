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

import org.killbill.billing.security.Logical;
import org.killbill.billing.security.Permission;
import org.killbill.billing.security.SecurityApiException;
import org.killbill.billing.util.callcontext.TenantContext;

public interface SecurityApi {

    /**
     * Perform a login attempt.
     *
     * @param principal   account identity (e.g. username)
     * @param credentials account credentials (e.g. password)
     */
    public void login(Object principal, Object credentials);

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
}
