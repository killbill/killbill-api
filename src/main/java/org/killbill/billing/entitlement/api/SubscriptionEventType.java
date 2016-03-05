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

package org.killbill.billing.entitlement.api;

import org.killbill.billing.ObjectType;

public enum SubscriptionEventType {
    /* Start the entitlement */
    START_ENTITLEMENT(ObjectType.BLOCKING_STATES),
    /* Start the billing  */
    START_BILLING(ObjectType.SUBSCRIPTION_EVENT),
    /* Pause entitlement. User has no access to the resource until resumed/stopped */
    PAUSE_ENTITLEMENT(ObjectType.BLOCKING_STATES),
    /* Pause billing. User is not billed from there until resumed/stopped */
    PAUSE_BILLING(ObjectType.BLOCKING_STATES),
    /* Resume entitlement. User has now access to the resource */
    RESUME_ENTITLEMENT(ObjectType.BLOCKING_STATES),
    /* Resume billing. User is now billed again */
    RESUME_BILLING(ObjectType.BLOCKING_STATES),
    /* Phase transition */
    PHASE(ObjectType.SUBSCRIPTION_EVENT),
    /* User generated change plan */
    CHANGE(ObjectType.SUBSCRIPTION_EVENT),
    /* User generated cancel */
    STOP_ENTITLEMENT(ObjectType.BLOCKING_STATES),
    STOP_BILLING(ObjectType.SUBSCRIPTION_EVENT),
    /* Transition state change for a given service */
    SERVICE_STATE_CHANGE(ObjectType.BLOCKING_STATES);

    private ObjectType objectType;

    SubscriptionEventType(ObjectType type) {
        this.objectType = type;
    }

    public ObjectType getObjectType() {
        return objectType;
    }
}
