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

package de.felix_klauke.guice.extension.injection.logback.listener;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import de.felix_klauke.guice.extension.injection.logback.annotation.InjectLogback;
import de.felix_klauke.guice.extension.injection.logback.injector.LoggerInjector;
import org.slf4j.Logger;

import java.lang.reflect.Field;

/**
 * The type listener that registers the members injector.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class LogbackTypeListener implements TypeListener {

    @Override
    public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
        Class<?> clazz = type.getRawType();

        processClazzIterative(clazz, encounter);
    }

    /**
     * Process the clazz and all its super classes until none more could be find.
     *
     * @param clazz     The class.
     * @param encounter the type encounter.
     * @param <I>       The generic type.
     */
    private <I> void processClazzIterative(Class<?> clazz, TypeEncounter<I> encounter) {
        while (clazz != null) {
            processClazz(clazz, encounter);
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Process the fields of the given clazz.
     *
     * @param clazz     The clazz.
     * @param encounter The type encounter.
     * @param <I>       The generic type.
     */
    private <I> void processClazz(Class<?> clazz, TypeEncounter<I> encounter) {
        for (Field currentField : clazz.getDeclaredFields()) {
            if (currentField.getType() != Logger.class || !currentField.isAnnotationPresent(InjectLogback.class)) {
                continue;
            }

            encounter.register(new LoggerInjector<>(currentField));
        }
    }
}
