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

package org.killbill.billing.util.tag;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.killbill.billing.ObjectType;

public enum ControlTagType {

    AUTO_PAY_OFF(new UUID(0, 1), "Suspends payments until removed.", true, false, Collections.<ObjectType>singletonList(ObjectType.ACCOUNT)),
    AUTO_INVOICING_OFF(new UUID(0, 2), "Suspends invoicing until removed.", false, true, Collections.<ObjectType>singletonList(ObjectType.ACCOUNT)),
    OVERDUE_ENFORCEMENT_OFF(new UUID(0, 3), "Suspends overdue enforcement behaviour until removed.", false, false, Collections.<ObjectType>singletonList(ObjectType.ACCOUNT)),
    WRITTEN_OFF(new UUID(0, 4), "Indicates that an invoice is written off. No billing or payment effect.", false, false, Collections.<ObjectType>singletonList(ObjectType.INVOICE)),
    MANUAL_PAY(new UUID(0, 5), "Indicates that Killbill doesn't process payments for that account (external payments only)", true, false, Collections.<ObjectType>singletonList(ObjectType.ACCOUNT)),
    TEST(new UUID(0, 6), "Indicates that this is a test account", false, false, Collections.<ObjectType>singletonList(org.killbill.billing.ObjectType.ACCOUNT)),
    PARTNER(new UUID(0, 7), "Indicates that this is a partner account", false, false, Collections.<ObjectType>singletonList(org.killbill.billing.ObjectType.ACCOUNT)),
    AUTO_INVOICING_DRAFT(new UUID(0, 8), "Generate account invoices in DRAFT mode.", false, false, Collections.<ObjectType>singletonList(org.killbill.billing.ObjectType.ACCOUNT)),
    AUTO_INVOICING_REUSE_DRAFT(new UUID(0, 9), "Use existing draft invoice if exists.", false, false, Collections.<ObjectType>singletonList(org.killbill.billing.ObjectType.ACCOUNT));

    private final UUID id;
    private final String description;
    private final boolean autoPaymentOff;
    private final boolean autoInvoicingOff;
    private final List<ObjectType> applicableObjectTypes;

    ControlTagType(final UUID id, final String description, final boolean autoPaymentOff, final boolean autoInvoicingOff,
                   final List<ObjectType> applicableObjectTypes) {
        this.id = id;
        this.description = description;
        this.autoPaymentOff = autoPaymentOff;
        this.autoInvoicingOff = autoInvoicingOff;
        this.applicableObjectTypes = applicableObjectTypes;
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getAutoPaymentOff() {
        return this.autoPaymentOff;
    }

    public boolean getAutoInvoicingOff() {
        return this.autoInvoicingOff;
    }

    public List<ObjectType> getApplicableObjectTypes() {
        return List.copyOf(applicableObjectTypes);
    }

    public static ControlTagType getTypeFromId(final UUID targetId) {
        for (ControlTagType cur : values()) {
            if (cur.getId().equals(targetId)) {
                return cur;
            }
        }
        return null;
    }

    /**
     * Convenience method to return whether an account is configured with auto_pay_off
     * (information of which control tag is configured with which behavior stays here)
     *
     * @param input tagDefinitionIds for all the tags associated to a given account
     * @return whether that account is set with auto_pay_off
     */
    public static boolean isAutoPayOff(final Collection<UUID> input) {
        for (UUID cur : input) {
            for (ControlTagType controlTag : values()) {
                if (controlTag.getId().equals(cur) && controlTag.getAutoPaymentOff()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Convenience method to return whether an account is configured with auto_invoicing_off
     * (information of which control tag is configured with which behavior stays here)
     *
     * @param input tagDefinitionIds for all the tags associated to a given account
     * @return whether that account is set with auto_invoicing_off
     */
    public static boolean isAutoInvoicingOff(final Collection<UUID> input) {
        for (UUID cur : input) {
            for (ControlTagType controlTag : values()) {
                if (controlTag.getId().equals(cur) && controlTag.getAutoInvoicingOff()) {
                    return true;
                }
            }
        }
        return false;
    }
}
