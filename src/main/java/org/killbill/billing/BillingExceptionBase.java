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

public class BillingExceptionBase extends Exception {

    private static final long serialVersionUID = 165720101383L;

    private final Throwable cause;
    private final int code;
    private final String formattedMsg;

    public BillingExceptionBase(final Throwable cause, final int code, final String msg) {
        this.formattedMsg = msg;
        this.code = code;
        this.cause = cause;
    }

    public BillingExceptionBase(final BillingExceptionBase cause) {
        this.formattedMsg = cause.getMessage();
        this.code = cause.getCode();
        this.cause = cause;
    }

    public BillingExceptionBase(/* @Nullable */ final Throwable cause, final ErrorCode code, final Object... args) {
        final String tmp;
        tmp = String.format(code.getFormat(), args);
        this.formattedMsg = tmp;
        this.code = code.getCode();
        this.cause = cause;
    }

    public BillingExceptionBase(final ErrorCode code, final Object... args) {
        this(null, code, args);
    }

    @Override
    public String getMessage() {
        return formattedMsg;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{cause=").append(cause);
        sb.append(", code=").append(code);
        sb.append(", formattedMsg='").append(formattedMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
