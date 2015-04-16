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

package org.killbill.billing.security;

public enum Permission {

    /*
     * Account
     */
    ACCOUNT_CAN_CHARGE("account", "charge"),
    ACCOUNT_CAN_CREDIT("account", "credit"),

    /*
     * Catalog
     */
    CATALOG_CAN_UPLOAD("catalog", "config_upload"),

    /*
     * Entitlement
     */
    ENTITLEMENT_CAN_CREATE("entitlement", "create"),

    /*
     * Invoice
     */
    INVOICE_CAN_CREDIT("invoice", "credit"),
    INVOICE_CAN_ADJUST("invoice", "adjust"),
    INVOICE_CAN_ITEM_ADJUST("invoice", "item_adjust"),
    INVOICE_CAN_DELETE_CBA("invoice", "delete_cba"),

    /*
     * Overdue
     */
    OVERDUE_CAN_UPLOAD("overdue", "config_upload"),

    /*
     * Payment
     */
    PAYMENT_CAN_TRIGGER_PAYMENT("payment", "trigger"),
    PAYMENT_CAN_REFUND("payment", "refund"),
    PAYMENT_CAN_CHARGEBACK("payment", "chargeback"),
    PAYMENT_CAN_CREATE_EXTERNAL_PAYMENT("payment", "external_payment"),

    /*
     * Tag
     */
    TAG_CAN_CREATE_TAG_DEFINITION("tag", "create_tag_definition"),
    TAG_CAN_DELETE_TAG_DEFINITION("tag", "delete_tag_definition"),
    TAG_CAN_ADD("tag", "add"),
    TAG_CAN_REMOVE("tag", "remove"),

    /*
     * Tenants
     */
    TENANT_CAN_VIEW("tenant", "view"),
    TENANT_CAN_CREATE("tenant", "create"),

    /*
     * Users (authentication, authorization)
    */
    USER_CAN_VIEW("user", "view"),
    USER_CAN_CREATE("user", "create");

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
