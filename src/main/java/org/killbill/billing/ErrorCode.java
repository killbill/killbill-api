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

package org.killbill.billing;

public enum ErrorCode {

    /*
     * Range 0 : COMMON EXCEPTIONS
     */
    NOT_IMPLEMENTED(1, "Api not implemented yet"),
    DATA_TRUNCATION(2, "Data truncation error. (%s)"),
    CURRENCY_INVALID(3, "Invalid currency %s, expected %s"),
    UNEXPECTED_ERROR(4, "%s"),
    /*
     *
     * Range 1000 : SUBSCRIPTIONS
     *
     */
    /* Generic through APIs */
    SUB_INVALID_REQUESTED_FUTURE_DATE(1001, "Requested date %s in the future is not allowed"),
    SUB_INVALID_REQUESTED_DATE(1001, "Requested date %s is not allowed to be prior to the previous transition %s"),

    /* Creation */
    SUB_CREATE_BAD_PHASE(1011, "Can't create plan initial phase %s"),
    SUB_CREATE_NO_BUNDLE(1012, "Bundle %s does not exist"),
    SUB_CREATE_NO_BP(1013, "Missing Base Subscription for bundle %s"),
    SUB_CREATE_ACTIVE_BUNDLE_KEY_EXISTS(1014, "Active bundle with external key %s already exists"),
    SUB_CREATE_BP_EXISTS(1015, "Subscription bundle %s already has a base subscription"),
    SUB_CREATE_AO_BP_NON_ACTIVE(1017, "Can't create AddOn %s for non active Base Plan"),
    SUB_CREATE_AO_ALREADY_INCLUDED(1018, "Can't create AddOn %s for BasePlan %s (Already included)"),
    SUB_CREATE_AO_NOT_AVAILABLE(1019, "Can't create AddOn %s for BasePlan %s (Not available)"),

    /* Change plan */
    SUB_CHANGE_NON_ACTIVE(1021, "Subscription %s is in state %s: Failed to change plan"),
    SUB_CHANGE_FUTURE_CANCELLED(1022, "Subscription %s is future cancelled: Failed to change plan"),
    SUB_CHANGE_DRY_RUN_NOT_BP(1023, "Change DryRun API is only available for BP"),

    /* Cancellation */
    SUB_CANCEL_BAD_STATE(1031, "Subscription %s is in state %s: Failed to cancel"),
    /* Recreation */
    SUB_RECREATE_BAD_STATE(1041, "Subscription %s is in state %s: Failed to recreate"),

    /* Un-cancellation */
    SUB_UNCANCEL_BAD_STATE(1070, "Subscription %s was not in a cancelled state: Failed to uncancel plan"),

    /* Fetch */
    SUB_GET_NO_BUNDLE_FOR_SUBSCRIPTION(1080, "Could not find a bundle for subscription %s"),
    SUB_GET_INVALID_BUNDLE_ID(1081, "Could not find a bundle matching id %s"),
    SUB_INVALID_SUBSCRIPTION_ID(1082, "Unknown subscription %s"),
    SUB_GET_INVALID_BUNDLE_KEY(1083, "Could not find a bundle matching key %s"),
    SUB_GET_NO_SUCH_BASE_SUBSCRIPTION(1084, "Could not find base subscription for bundle %s"),
    SUB_GET_INVALID_ACCOUNT_ID(1085, "Could not find subscriptions for account %s"),

    /* Repair */
    SUB_REPAIR_INVALID_DELETE_SET(1091, "Event %s is not deleted for subscription %s but prior events were"),
    SUB_REPAIR_NON_EXISTENT_DELETE_EVENT(1092, "Event %s does not exist for subscription %s"),
    SUB_REPAIR_MISSING_AO_DELETE_EVENT(1093, "Event %s should be in deleted set for subscription %s because BP events got deleted earlier"),
    SUB_REPAIR_NEW_EVENT_BEFORE_LAST_BP_REMAINING(1094, "New event %s for subscription %s is before last remaining event for BP"),
    SUB_REPAIR_NEW_EVENT_BEFORE_LAST_AO_REMAINING(1095, "New event %s for subscription %s is before last remaining event"),
    SUB_REPAIR_UNKNOWN_TYPE(1096, "Unknown new event type %s for subscription %s"),
    SUB_REPAIR_UNKNOWN_BUNDLE(1097, "Unknown bundle %s"),
    SUB_REPAIR_UNKNOWN_SUBSCRIPTION(1098, "Unknown subscription %s"),
    SUB_REPAIR_NO_ACTIVE_SUBSCRIPTIONS(1099, "No active subscriptions on bundle %s"),
    SUB_REPAIR_VIEW_CHANGED(1100, "View for bundle %s has changed from %s to %s"),
    SUB_REPAIR_SUB_RECREATE_NOT_EMPTY(1101, "Subscription %s with recreation for bundle %s should specify all existing events to be deleted"),
    SUB_REPAIR_SUB_EMPTY(1102, "Subscription %s with recreation for bundle %s should specify all existing events to be deleted"),
    SUB_REPAIR_BP_RECREATE_MISSING_AO(1103, "BP recreation for bundle %s implies repair all subscriptions"),
    SUB_REPAIR_BP_RECREATE_MISSING_AO_CREATE(1104, "BP recreation for bundle %s implies that all AO should be start also with a CREATE"),
    SUB_REPAIR_AO_CREATE_BEFORE_BP_START(1105, "Can't recreate AO %s for bundle %s before BP starts"),

    SUB_BUNDLE_IS_OVERDUE_BLOCKED(1090, "Changes to this bundle are blocked by overdue enforcement (%s :  %s)"),
    SUB_ACCOUNT_IS_OVERDUE_BLOCKED(1091, "Changes to this account are blocked by overdue enforcement (%s)"),

    /* Transfer */
    SUB_TRANSFER_INVALID_EFF_DATE(1106, "Invalid effective date for transfer: %s"),

    /*
    *
    * Range 2000 : CATALOG
    *
    */

    /*
    * Rules exceptions
    */

    /* Plan change is disallowed by the catalog */
    CAT_ILLEGAL_CHANGE_REQUEST(2001, "Attempting to change plan from (product: '%s', billing period: '%s', " +
                                     "pricelist '%s') to (product: '%s', billing period: '%s', pricelist '%s'). This transition is not allowed by catalog rules"),

    /*
      * Price list
      */

    /*Attempt to reference a price that is not present - should only happen if it is a currency not available in the catalog */
    CAT_NO_PRICE_FOR_CURRENCY(2010, "This price does not have a value for the currency '%s'."),

    /* Price value explicitly set to NULL meaning there is no price available in that currency */
    CAT_PRICE_VALUE_NULL_FOR_CURRENCY(2011, "The value for the currency '%s' is NULL. This plan cannot be bought in this currency."),
    CAT_NULL_PRICE_LIST_NAME(2012, "Price list name was null"),
    CAT_PRICE_LIST_NOT_FOUND(2013, "Could not find a pricelist with name '%s'"),
    /*
     * Plans
     */
    CAT_PLAN_NOT_FOUND(2020, "Could not find a plan matching: (product: '%s', billing period: '%s', pricelist '%s')"),
    CAT_NO_SUCH_PLAN(2021, "Could not find any plans named '%s'"),

    /*
     * Products
     */
    CAT_NO_SUCH_PRODUCT(2030, "Could not find any product named '%s'"),
    CAT_NULL_PRODUCT_NAME(2031, "Product name was null"),
    /*
     * Phases
     */
    CAT_NO_SUCH_PHASE(2040, "Could not find any phases named '%s'"),
    CAT_BAD_PHASE_NAME(2041, "Bad phase name '%s'"),
    /*
     * Versioned Catalog
     */
    CAT_NO_CATALOG_FOR_GIVEN_DATE(2050, "There is no catalog version that applies for the given date '%s'"),
    CAT_NO_CATALOG_ENTRIES_FOR_GIVEN_DATE(2051, "The are no catalog entries that apply for the given date '%s'"),
    CAT_CATALOG_NAME_MISMATCH(2052, "The catalog name '%s' does not match the name of the catalog we are trying to add '%s'"),
    CAT_CATALOG_RECURRING_MODE_MISMATCH(2053, "The catalog recurring billing mode '%s' does not match the one of the catalog we are trying to add '%s'"),
    /*
     * Billing Alignment
     */
    CAT_INVALID_BILLING_ALIGNMENT(2060, "Invalid billing alignment '%s'"),
    /*
     * Overdue
     */
    CAT_NO_SUCH_OVEDUE_STATE(2070, "No such overdue state '%s'"),
    CAT_MISSING_CLEAR_STATE(2071, "Missing a clear state"),
    CAT_NO_OVERDUEABLE_TYPE(2072, "No such overdueable type: "),

    CAT_NOT_TOP_UP_BLOCK(2075, "Block for phase %s defines a TOP_UP property for a non TOP_UP block"),

    CAT_INVALID_FOR_TENANT(2080, "Invalid catalog for tenant : %s"),
    CAT_INVALID_INVALID_PRICE_OVERRIDE(2081, "Cannot override price for plan %s : %s"),

    CAT_INVALID_DEFAULT(2999, "Invalid default catalog : %s"),

    /*
    *
    * Range 3000 : ACCOUNT
    *
    */
    ACCOUNT_ALREADY_EXISTS(3000, "Account already exists for key %s"),
    ACCOUNT_INVALID_NAME(3001, "An invalid name was specified when creating or updating an account."),
    ACCOUNT_DOES_NOT_EXIST_FOR_ID(3002, "Account does not exist for id %s"),
    ACCOUNT_DOES_NOT_EXIST_FOR_KEY(3003, "Account does not exist for key %s"),
    ACCOUNT_CANNOT_MAP_NULL_KEY(3004, "An attempt was made to get the id for a <null> external key."),
    ACCOUNT_CANNOT_CHANGE_EXTERNAL_KEY(3005, "External keys cannot be updated. Original key remains: %s"),
    ACCOUNT_CREATION_FAILED(3006, "Account creation failed."),
    ACCOUNT_UPDATE_FAILED(3007, "Account update failed."),
    ACCOUNT_DOES_NOT_EXIST_FOR_RECORD_ID(3008, "Account does not exist for recordId %s"),

    ACCOUNT_EMAIL_ALREADY_EXISTS(3500, "Account email already exists %s"),

    /*
    *
    * Range 3900: Tag definitions
    *
    */
    TAG_DEFINITION_CONFLICTS_WITH_CONTROL_TAG(3900, "The tag definition name conflicts with a reserved %s"),
    TAG_DEFINITION_ALREADY_EXISTS(3901, "The tag definition name already exists %s"),
    TAG_DEFINITION_DOES_NOT_EXIST(3902, "The tag definition id does not exist %s"),
    TAG_DEFINITION_IN_USE(3903, "The tag definition name is currently in use %s"),
    CONTROL_TAG_DOES_NOT_EXIST(3904, "The control tag does not exist %s"),
    TAG_DEFINITION_HAS_UPPERCASE(3905, "The tag definition name must be in lowercase %s"),

    /*
    *
    * Range 3950: Tags
    *
    */
    TAG_DOES_NOT_EXIST(3950, "The tag does not exist (name: %s)"),
    TAG_CANNOT_BE_REMOVED(3951, "The tag %s cannot be removed because %s"),
    TAG_ALREADY_EXISTS(3952, "The tag %s already exists"),

    /*
    *
    * Range 4000: INVOICE
    *
    */
    INVOICE_ACCOUNT_ID_INVALID(4001, "No account could be retrieved for id %s"),
    INVOICE_INVALID_TRANSITION(4002, "Transition did not contain a subscription id."),
    INVOICE_NO_ACCOUNT_ID_FOR_SUBSCRIPTION_ID(4003, "No account id was retrieved for subscription id %s"),
    INVOICE_INVALID_DATE_SEQUENCE(4004, "Date sequence was invalid. Start Date: %s; End Date: %s; Target Date: %s"),
    INVOICE_TARGET_DATE_TOO_FAR_IN_THE_FUTURE(4005, "The target date was too far in the future. Target Date: %s"),
    INVOICE_NOT_FOUND(4006, "No invoice could be found for id %s."),
    INVOICE_NOTHING_TO_DO(4007, "No invoice to generate for account %s and date %s"),
    INVOICE_NO_SUCH_CREDIT(4008, "Credit item for id %s does not exist"),
    CREDIT_AMOUNT_INVALID(4009, "Credit amount %s should be strictly positive"),
    INVOICE_ITEM_ADJUSTMENT_AMOUNT_SHOULD_BE_POSITIVE(4010, "Invoice adjustment amount %s should be strictly positive"),
    INVOICE_ITEM_NOT_FOUND(4011, "No invoice item could be found for id %s."),
    INVOICE_INVALID_FOR_INVOICE_ITEM_ADJUSTMENT(4012, "Invoice item %s doesn't belong to invoice %s."),
    INVOICE_NO_SUCH_EXTERNAL_CHARGE(4014, "External charge item for id %s does not exist"),
    EXTERNAL_CHARGE_AMOUNT_INVALID(4015, "External charge amount %s should be strictly positive"),
    INVOICE_WOULD_BE_NEGATIVE(4016, "Cannot execute operation, the invoice balance would become negative"),
    INVOICE_ALREADY_EXISTS(4017, "The invoice already exists %s"),
    INVOICE_NUMBER_NOT_FOUND(4018, "No invoice could be found for number %s."),
    INVOICE_INVALID_NUMBER(4019, "Invalid invoice number %s."),
    INVOICE_ITEM_ADJUSTMENT_AMOUNT_INVALID(4020, "Invoice adjustment amount %s should be lower than %s"),
    INVOICE_ITEM_ADJUSTMENT_ITEM_INVALID(4021, "Invoice item %s cannot be adjusted"),
    INVOICE_ITEM_TYPE_INVALID(4022, "Invalid invoice item type %s"),

    /*
     *
     * Range 4900: Invoice payment
     *
     */
    INVOICE_PAYMENT_NOT_FOUND(4900, "No invoice payment could be found for id %s."),
    CHARGE_BACK_AMOUNT_TOO_HIGH(4901, "Tried to charge back %s of a %s payment."),
    CHARGE_BACK_AMOUNT_IS_NEGATIVE(4902, "Charge backs for negative amounts are not permitted"),
    CHARGE_BACK_COULD_NOT_FIND_ACCOUNT_ID(4003, "Could not find chargeback for id %s."),
    CHARGE_BACK_DOES_NOT_EXIST(4004, "Could not find chargeback for id %s."),
    INVOICE_PAYMENT_BY_ATTEMPT_NOT_FOUND(4905, "No invoice payment could be found for paymentAttempt id %s."),
    REFUND_AMOUNT_TOO_HIGH(4906, "Tried to refund %s of a %s payment."),
    REFUND_AMOUNT_DONT_MATCH_ITEMS_TO_ADJUST(4907, "You can't specify a refund amount of %s that is less than the sum of the invoice items amount of %s."),

    /*
     *
     * Range 5000: Overdue system
     *
     */
    OVERDUE_CAT_ERROR_ENCOUNTERED(5001, "Catalog error encountered on Overdueable: id='%s', type='%s'"),
    OVERDUE_TYPE_NOT_SUPPORTED(5002, "Overdue of this type is not supported: id='%s', type='%s'"),
    OVERDUE_NO_REEVALUATION_INTERVAL(5003, "No valid reevaluation interval for state (name: %s)"),
    OVERDUE_NOT_CONFIGURED(5004, "No configuration was found for the overdue system"),

    OVERDUE_INVALID_FOR_TENANT(5010, "Invalid overdue config for tenant : %s"),

    /*
     *
     * Range 6000: Blocking system
     *
     */
    BLOCK_BLOCKED_ACTION(6000, "The action %s is block on this %s with id=%s"),
    BLOCK_TYPE_NOT_SUPPORTED(6001, "The Blockable type '%s' is not supported"),

    /*
    * Range 7000 : Payment
    */

    PAYMENT_NO_SUCH_PAYMENT_METHOD(7000, "Payment method %s does not exist"),
    PAYMENT_REFUND_AMOUNT_NEGATIVE_OR_NULL(7001, "Refund on payment %s not allowed with amount %s"),
    PAYMENT_GET_PAYMENT_METHODS(7004, "Failed to retrieve payment method for account %s : %s"),
    PAYMENT_ADD_PAYMENT_METHOD(7005, "Failed to add payment method for account %s : %s"),
    PAYMENT_REFRESH_PAYMENT_METHOD(7006, "Failed to resfresh payment methods for account %s : %s"),
    PAYMENT_DEL_PAYMENT_METHOD(7007, "Failed to delete payment method for account %s : %s"),
    PAYMENT_UPD_PAYMENT_METHOD(7008, "Failed to update payment method for account %s : %s"),
    PAYMENT_CREATE_PAYMENT(7009, "Failed to create payment for account %s : %s"),
    PAYMENT_NULL_INVOICE(7017, "Invoice %s has a balance <= 0 "),
    PAYMENT_INTERNAL_ERROR(7019, "Internal payment error : %s"),
    PAYMENT_NO_SUCH_PAYMENT(7020, "Payment %s does not exist"),
    PAYMENT_NO_DEFAULT_PAYMENT_METHOD(7021, "Account %s does not have a default payment method set"),
    PAYMENT_DEL_DEFAULT_PAYMENT_METHOD(7022, "Cannot delete default payment method for account %s"),
    PAYMENT_NO_SUCH_SUCCESS_PAYMENT(7024, "Payment %s did not succeed"),
    PAYMENT_NO_SUCH_PAYMENT_PLUGIN(7028, "Payment plugin %s is not registered"),
    PAYMENT_ACTIVE_TRANSACTION_KEY_EXISTS(7030, "Successful transaction with external key %s already exists"),
    PAYMENT_INVALID_PARAMETER(7031, "Invalid parameter %s: %s"),
    PAYMENT_INVALID_OPERATION(7032, "Invalid payment transition %s from state %s"),

    PAYMENT_PLUGIN_TIMEOUT(7100, "Plugin timeout for account %s and invoice %s"),
    PAYMENT_PLUGIN_GET_PAYMENT_INFO(7102, "Failed to retrieve payment plugin info for payment %s: %s"),
    PAYMENT_PLUGIN_SEARCH_PAYMENT_METHODS(7103, "Error while searching payment methods in plugin %s for search key %s"),
    PAYMENT_PLUGIN_SEARCH_PAYMENTS(7105, "Error while searching payments in plugin %s for search key %s"),
    PAYMENT_PLUGIN_EXCEPTION(7199, "Plugin exception %s"),

    /*
    *
    * Range 8000: Entitlement
    */
    ENT_ALREADY_BLOCKED(8001, "The blockable entity %s is already blocked"),
    ENT_PLUGIN_API_ABORTED(8002, "Entitlement plugin aborted call"),
    ENT_PLUGIN_API_EXCEPTION(8003, "Exception in plugin : %s"),

    /*
   *
   * Range 9000: Custom Fields
   */
    CUSTOM_FIELD_ALREADY_EXISTS(9000, "The custom field %s already exists"),

    /*
     *
     * Range 10000: Custom Fields
    */
    CURRENCY_NO_SUCH_PAYMENT_PLUGIN(10000, "Currency plugin %s is not registered"),
    CURRENCY_NO_SUCH_RATE_FOR_CURRENCY(10001, "Rate for currency %s is non defined"),

    /*
    *
    * Range 20000: TENANT
    *
    */
    TENANT_ALREADY_EXISTS(20000, "Tenant already exists for key %s"),
    TENANT_DOES_NOT_EXIST_FOR_ID(20001, "Tenant does not exist for id %s"),
    TENANT_DOES_NOT_EXIST_FOR_KEY(20002, "Tenant does not exist for key %s"),
    TENANT_DOES_NOT_EXIST_FOR_API_KEY(20003, "Tenant does not exist for api key %s"),
    TENANT_CREATION_FAILED(20004, "Tenant creation failed."),
    TENANT_UPDATE_FAILED(20005, "Tenant update failed."),
    TENANT_NO_SUCH_KEY(20006, "Tenant %s does not have a key %s"),

    /*
    *
    * Range 30000: Miscellaneous
    *
    */
    EMAIL_SENDING_FAILED(30000, "Sending email failed"),
    EMAIL_PROPERTIES_FILE_MISSING(30001, "The properties file for email configuration could not be found."),
    MISSING_TRANSLATION_RESOURCE(30010, "The resources for %s translation could not be found."),
    MISSING_DEFAULT_TRANSLATION_RESOURCE(30011, "The default resource for %s translation could not be found."),

    /*
     *
     * Range 40000: SECURITY
     *
     */
    SECURITY_NOT_ENOUGH_PERMISSIONS(40000, "User doesn't have enough permissions"),
    SECURITY_INVALID_USER(40001, "The user %s does not exist or has been inactivated"),
    SECURITY_USER_ALREADY_EXISTS(40002, "The user %s already exists"),
    SECURITY_INVALID_ROLE(40003, "The role %s does not exist or has been inactivated"),
    SECURITY_ROLE_ALREADY_EXISTS(40004, "The role %s already exists"),
    SECURITY_INVALID_PERMISSIONS(40005, "The permission %s is invalid"),

    __UNKNOWN_ERROR_CODE(-1, "Unknown ErrorCode");

    private final int code;
    private final String format;

    ErrorCode(final int code, final String format) {
        this.code = code;
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public int getCode() {
        return code;
    }

    public static ErrorCode fromCode(final int code) {
        for (final ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return __UNKNOWN_ERROR_CODE;
    }
}
