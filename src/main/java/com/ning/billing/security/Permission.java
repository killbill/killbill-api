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

package com.ning.billing.security;

public enum Permission {

    /*
     * Invoice
     */
    INVOICE_CAN_CHARGE("invoice", "charge"),
    INVOICE_CAN_CREDIT("invoice", "credit"),
    INVOICE_CAN_ADJUST("invoice", "adjust"),
    INVOICE_CAN_ITEM_ADJUST("invoice", "item_adjust"),

    /*
     * Payment
     */
    PAYMENT_CAN_REFUND("payment", "refund"),
    PAYMENT_CAN_CREATE_EXTERNAL_PAYMENT("payment", "external_payment");

    private final String group;
    private final String value;

    Permission(final String group, final String value) {
        this.group = group;
        this.value = value;
    }

    public String getGroup() {
        return group;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", group, value);
    }
}
