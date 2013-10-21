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

package com.ning.billing.util.entity;

/**
 * Represents a page, i.e. a subset, of results
 *
 * @param <T> type of result, usually an Entity
 */
public interface Pagination<T> extends Iterable<T> {

    /**
     * @return offset from which these results are returned
     */
    public Long getCurrentOffset();

    /**
     * @return offset to use to retrieve the next set of results, null
     *         if this is the last page of results
     */
    public Long getNextOffset();

    /**
     * Approximation of the expected total number of results - the implementation doesn't have to guarantee accuracy
     *
     * @return total number of results (approximation) across all pages, null if unknown
     */
    public Long getTotalNbResults();

    /**
     * Approximation of the number of results in this page - the implementation doesn't have to guarantee accuracy
     *
     * @return number of results in this page (approximation), null if unknown
     */
    public Long getNbResults();

    /**
     * Approximation of the number of remaining results from this offset - the implementation doesn't have to guarantee accuracy
     *
     * @return total number of results (approximation) remaining from this offset, null if unknown
     */
    public Long getNbResultsFromOffset();
}
