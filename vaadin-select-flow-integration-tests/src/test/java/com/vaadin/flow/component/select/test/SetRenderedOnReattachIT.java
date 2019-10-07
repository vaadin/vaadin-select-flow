/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.select.test;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import com.vaadin.flow.component.select.testbench.SelectElement;
import com.vaadin.flow.testutil.AbstractComponentIT;
import com.vaadin.flow.testutil.TestPath;
import com.vaadin.testbench.TestBenchElement;

@TestPath("render-reattach")
public class SetRenderedOnReattachIT extends AbstractComponentIT {

    @Test
    public void setRenderer_reAttachComponent_noClientSideErrors() {
        open();
        assertNoClientSideError();

        SelectElement select = $(SelectElement.class).first();
        assertItems(select);

        findElement(By.id("remove")).click();
        Assert.assertEquals(0, $(SelectElement.class).all().size());
        assertNoClientSideError();

        findElement(By.id("add")).click();
        Assert.assertEquals(1, $(SelectElement.class).all().size());
        assertItems($(SelectElement.class).first());

        assertNoClientSideError();
    }

    private void assertNoClientSideError() {
        Assert.assertEquals(0, $(TestBenchElement.class)
                .attribute("class", "v-system-error").all().size());
        LogEntries logs = driver.manage().logs().get("browser");
        if (logs != null) {
            Optional<LogEntry> anyError = StreamSupport
                    .stream(logs.spliterator(), true)
                    .filter(entry -> entry.getLevel().intValue() > Level.INFO
                            .intValue())
                    .filter(entry -> !entry.getMessage()
                            .contains("favicon.ico"))
                    .filter(entry -> !entry.getMessage()
                            .contains("HTML Imports is deprecated"))
                    .filter(entry -> !entry.getMessage()
                            .contains("sockjs-node"))
                    .filter(entry -> !entry.getMessage()
                            .contains("[WDS] Disconnected!"))
                    .findAny();
            anyError.ifPresent(entry -> Assert.fail(entry.getMessage()));
        }
    }

    private void assertItems(SelectElement select) {
        List<SelectElement.ItemElement> items = select.getItems();
        Assert.assertEquals("invalid number of items", 3, items.size());
    }
}
