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

package org.killbill.billing.payment.plugin.api;

public enum PaymentPluginStatus {
    /* The payment transaction went through and was successful */
    PROCESSED,
    /* The payment transaction went through and requires a completion step */
    PENDING,
    /* The payment transaction went through but failed (for e.g: insufficient funds on a CC) */
    ERROR,
    /* The payment transaction may or not have succeeded */
    UNDEFINED,
    /* The payment transaction did NOT happen (was not even submitted, for e.g connection failure) */
    CANCELED;
}
