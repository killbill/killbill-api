/*
 * Copyright 2010-2014 Ning, Inc.
 * Copyright 2014-2016 Groupon, Inc
 * Copyright 2014-2016 The Billing Project, LLC
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

package org.killbill.billing.util.entity;

/**
 * Represents a page, i.e. a subset, of records
 *
 * <p>The Pagination object will be associated with a database connection. You must iterate through
 * all results to close the connection.
 *
 * @param <T> type of record, usually an Entity
 */
public interface Pagination<T> extends Iterable<T> {

    /**
     * @return offset from which these records are returned
     */
    public Long getCurrentOffset();

    /**
     * @return offset to use to retrieve the next set of records, null
     * if this is the last page of records
     */
    public Long getNextOffset();

    /**
     * Total number of records in the complete data set, regardless of paging and filtering
     * <p/>
     * The implementation doesn't have to guarantee accuracy.
     *
     * @return total number of records (approximation), null if unknown
     */
    public Long getMaxNbRecords();

    /**
     * Total number of records in the data set, regardless of paging, but including filtering
     * <p/>
     * The implementation doesn't have to guarantee accuracy.
     *
     * @return total number of records (approximation), null if unknown
     */
    public Long getTotalNbRecords();
}
