/*
 * Copyright 2010-2013 Ning, Inc.
 * Copyright 2015 Groupon, Inc
 * Copyright 2015 The Billing Project, LLC
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

package org.killbill.billing.invoice.api;

public enum DryRunType {
    /* User specifies the target date, and invoice returns a possible invoice for that date */
    TARGET_DATE,
    /* User does not specify date instead the system compute the date:
       - If a subscriptionId or bundleId is specified system, will look for next date where that bundle or subscription would get invoices
       - If not first invoice for the account
     */
    UPCOMING_INVOICE,
    /* If a user were to create, cancel or change plan associated with a subscription, the system computes invoice that would be generated */
    SUBSCRIPTION_ACTION
}
