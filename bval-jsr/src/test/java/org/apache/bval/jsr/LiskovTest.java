/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.bval.jsr;

import java.lang.reflect.Method;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("requires some revisiting of Liskov impl - discuss in progress")
public class LiskovTest {
    @Test // this test was throwing a Liskov exception, here to ensure it is not the case
    public void validateParams() throws NoSuchMethodException {
        final Api service = new Impl();
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            final ExecutableValidator validator = factory.getValidator().forExecutables();
            final Method method = Api.class.getMethod("read", String.class);
            validator.validateParameters(service, method, new Object[]{""});
        }
    }

    public interface Api {
        String read(@NotNull String key);
    }

    public static abstract class Base {
        public String read(final String key) {
            return null;
        }
    }

    public static class Impl extends Base implements Api {
        @Override
        public String read(final String key) {
            return super.read(key);
        }
    }
}
