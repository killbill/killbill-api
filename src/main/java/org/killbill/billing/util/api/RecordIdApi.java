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

import java.util.UUID;

import org.killbill.billing.KillbillApi;
import org.killbill.billing.ObjectType;
import org.killbill.billing.util.callcontext.TenantContext;

public interface RecordIdApi extends KillbillApi {

    /**
     * This can be used by external plugins to keep the mapping between UUID  and recordId
     *
     * @param objectId      the uuid of the object
     * @param objectType    the object type
     * @param tenantContext the context associated with the call
     * @return the record id associated with that object
     */
    Long getRecordId(UUID objectId, ObjectType objectType, final TenantContext tenantContext);
}
