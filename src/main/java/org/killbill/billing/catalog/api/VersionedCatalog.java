/*
 * Copyright 2014-2019 Groupon, Inc
 * Copyright 2014-2019 The Billing Project, LLC
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

package org.killbill.billing.catalog.api;

import java.util.Date;
import java.util.List;

public interface VersionedCatalog {

    /**
     *
     * @return All the (ordered) catalog versions
     */
    public List<StaticCatalog> getVersions();

    /**
     * The latest catalog version is the one used by the system when creating
     *  or change the {@code Plan} associated with a subscription.
     *
     * @return the current (latest) catalog version.
     */
    public StaticCatalog getCurrentVersion();

    /**
     *
     * @param targetDate the date used to find the catalog version
     * @return the catalog version matching this date
     *
     *
     * Assuming two StaticCatalog, S1 with a version date of D1, and S2 with a version date of D2:
     * - Specifying a date D such that  D1 <= D < D2 will return S1
     * - Specifying a date D such that  D2 <= D will return S2
     */
    public StaticCatalog getVersion(Date targetDate);



}
