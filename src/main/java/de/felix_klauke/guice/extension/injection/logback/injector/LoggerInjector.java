/*
 * MIT License
 *
 * Copyright (c) 2017 Felix Klauke
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.felix_klauke.guice.extension.injection.logback.injector;

import com.google.inject.MembersInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * The injector that will set the field via reflection.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class LoggerInjector<T> implements MembersInjector<T> {

    /**
     * The target field that should contain the logger.
     */
    private final Field targetField;

    /**
     * The class that declares the {@link #targetField}.
     */
    private final Class<?> declaringClass;

    /**
     * The logger instance we will inject in {@link #injectMembers(Object)}.
     */
    private Logger logger;

    /**
     * Create a new logger injector.
     *
     * @param targetField The field we want to inject in.
     */
    LoggerInjector(Field targetField) {
        this.targetField = targetField;
        declaringClass = targetField.getDeclaringClass();

        initLogger();
    }

    /**
     * Initialize the logger instance itself.
     */
    private void initLogger() {
        logger = LoggerFactory.getLogger(declaringClass);
    }

    @Override
    public void injectMembers(T instance) {
        try {
            targetField.setAccessible(true);
            targetField.set(instance, logger);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
