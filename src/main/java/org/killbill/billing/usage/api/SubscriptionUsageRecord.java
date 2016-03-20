/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2014 Groupon, Inc
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

package org.killbill.billing.usage.api;

import java.util.List;
import java.util.UUID;

public class SubscriptionUsageRecord {

    private final UUID subscriptionId;

    private final String trackingId;

    private final List<UnitUsageRecord> unitUsageRecord;

    public SubscriptionUsageRecord(final UUID subscriptionId, final String trackingId, final List<UnitUsageRecord> unitUsageRecord) {
        this.subscriptionId = subscriptionId;
        this.trackingId = trackingId;
        this.unitUsageRecord = unitUsageRecord;
    }

    public UUID getSubscriptionId() {
        return subscriptionId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public List<UnitUsageRecord> getUnitUsageRecord() {
        return unitUsageRecord;
    }
}
