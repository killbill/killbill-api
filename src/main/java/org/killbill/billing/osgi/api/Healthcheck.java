/*
 * Copyright 2014-2018 Groupon, Inc
 * Copyright 2014-2018 The Billing Project, LLC
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

package org.killbill.billing.osgi.api;

import java.util.Collections;
import java.util.Map;

import org.killbill.billing.tenant.api.Tenant;

public interface Healthcheck {

    public HealthStatus getHealthStatus(/* @Nullable */ final Tenant tenant, /* @Nullable */ final Map properties);

    public class HealthStatus {

        private final boolean healthy;
        private final Map details;

        public HealthStatus(final boolean healthy, final Map details) {
            this.details = Map.copyOf(details);
            this.healthy = healthy;
        }

        public static HealthStatus healthy() {
            return new HealthStatus(true, Collections.EMPTY_MAP);
        }

        public static HealthStatus healthy(final String message) {
            return new HealthStatus(true, Collections.singletonMap("message", message));
        }

        public static HealthStatus unHealthy(final String message) {
            return new HealthStatus(false, Collections.singletonMap("message", message));
        }

        public boolean isHealthy() {
            return healthy;
        }

        public Map getDetails() {
            return Map.copyOf(details);
        }
    }
}
