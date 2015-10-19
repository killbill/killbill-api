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
    ACCOUNT_CAN_CREATE("account", "create"),
    ACCOUNT_CAN_UPDATE("account", "update"),
    ACCOUNT_CAN_ADD_EMAILS("account", "add_emails"),
    ACCOUNT_CAN_DELETE_EMAILS("account", "delete_emails"),
    ACCOUNT_CAN_CHARGE("account", "charge"),
    ACCOUNT_CAN_CREDIT("account", "credit"),

    /*
     * Catalog
     */
    CATALOG_CAN_UPLOAD("catalog", "config_upload"),

    /**
     * Custom fields
     */
    CUSTOM_FIELDS_CAN_ADD("custom_field", "add"),
    CUSTOM_FIELDS_CAN_DELETE("custom_field", "delete"),

    /*
     * Entitlement
     */
    ENTITLEMENT_CAN_CREATE("entitlement", "create"),
    ENTITLEMENT_CAN_CHANGE_PLAN("entitlement", "change_plan"),
    ENTITLEMENT_CAN_PAUSE_RESUME("entitlement", "pause_resume"),
    ENTITLEMENT_CAN_CANCEL("entitlement", "cancel"),
    ENTITLEMENT_CAN_TRANSFER("entitlement", "transfer"),

    /*
     * Invoice
     */
    INVOICE_CAN_CREDIT("invoice", "credit"),
    INVOICE_CAN_ADJUST("invoice", "adjust"),
    INVOICE_CAN_ITEM_ADJUST("invoice", "item_adjust"),
    INVOICE_CAN_DELETE_CBA("invoice", "delete_cba"),
    INVOICE_CAN_TRIGGER_INVOICE("invoice", "trigger"),

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

    /**
     * Payment methods
     */
    PAYMENT_METHOD_CAN_CREATE("payment_method", "create"),
    PAYMENT_METHOD_CAN_UPDATE("payment_method", "update"),
    PAYMENT_METHOD_CAN_DELETE("payment_method", "delete"),

    /*
     * Tag
     */
    TAG_CAN_CREATE_TAG_DEFINITION("tag", "create_tag_definition"),
    TAG_CAN_DELETE_TAG_DEFINITION("tag", "delete_tag_definition"),
    TAG_CAN_ADD("tag", "add"),
    TAG_CAN_DELETE("tag", "delete"),

    /*
     * Tenants
     */
    TENANT_CAN_VIEW("tenant", "view"),
    TENANT_CAN_CREATE("tenant", "create"),

    /**
     * Tenant keys
     */
    TENANT_KEYS_CAN_ADD("tenant_kvs", "add"),
    TENANT_KEYS_CAN_DELETE("tenant_kvs", "delete"),

    /**
     * Usage
     */
    USAGE_CAN_RECORD("usage", "record"),


    /*
     * Users (authentication, authorization)
    */
    USER_CAN_VIEW("user", "view"),
    USER_CAN_CREATE("user", "create"),


    /*
     * Administrator that can update state (to correct data associated to bugs, ...)
     */
    ADMIN_CAN_FIX_DATA("admin", "update");

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
