/*
 * Copyright 2014 The Billing Project, Inc.
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

package org.killbill.billing.catalog.api;

public interface Block {

    /**
     * @return the {@code BlockType}
     */
    public BlockType getType();

    /**
     * @return the unit for that {@code Block} section.
     */
    public Unit getUnit();

    /**
     * @return the size of the block
     */
    public Double getSize();

    /**
     * @return the recurring {@code InternationalPrice} for that {@code Block} section.
     */
    public InternationalPrice getPrice();

    /**
     * @return the minimum number of {@code Unit} credits after which TopUp kicks in.
     * @throws CatalogApiException if the {#code Block} is not of type {@code BlockType.TOP_UP}
     */
    public Double getMinTopUpCredit() throws CatalogApiException;
}
