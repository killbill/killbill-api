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

package org.killbill.billing.util.audit;

import java.util.UUID;

import org.joda.time.DateTime;
import org.killbill.billing.ObjectType;
import org.killbill.billing.util.entity.Entity;

public interface AuditLog extends Entity {

    /**
     * Get the original Entity id for this log entry
     *
     * @return the original Entity id
     */
    public UUID getAuditedEntityId();

    /**
     * Get the original Entity object type for this log entry
     *
     * @return the original Entity object type
     */
    public ObjectType getAuditedObjectType();

    /**
     * Get the type of change for this log entry
     *
     * @return the ChangeType
     */
    public ChangeType getChangeType();

    /**
     * Get the name of the requestor
     *
     * @return the requestor user name
     */
    public String getUserName();

    /**
     * Get the time when this change was effective
     *
     * @return the created date of this log entry
     */
    public DateTime getCreatedDate();

    /**
     * Get the reason code for this change
     *
     * @return the reason code
     */
    public String getReasonCode();

    /**
     * Get the user token of this change requestor
     *
     * @return the user token
     */
    public String getUserToken();

    /**
     * Get the comment for this change
     *
     * @return the comment
     */
    public String getComment();
}
