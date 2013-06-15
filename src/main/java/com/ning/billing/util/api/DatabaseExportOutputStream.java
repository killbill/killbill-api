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

package com.ning.billing.util.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DatabaseExportOutputStream {

    /**
     * Notify the stream that the following data is for a different table
     *
     * @param tableName       the following table name
     * @param columnsForTable database columns
     */
    public void newTable(String tableName, List<ColumnInfo> columnsForTable);

    /**
     * Write one row of data
     *
     * @param row row of data
     * @throws IOException generic IOException
     */
    public void write(Map<String, Object> row) throws IOException;
}
