/*
 * Copyright 2000-2020 Vaadin Ltd.
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

package com.vaadin.flow.component.select.data;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SelectListDataViewTest {

    private final String[] items = new String[] { "one", "two", "three",
            "four" };

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void dataViewWithItem_rowOutsideSetRequested_exceptionThrown() {
        expectedException.expect(IndexOutOfBoundsException.class);
        expectedException.expectMessage(
                "Given index 7 is outside of the accepted range '0 - 3'");

        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        dataView.getItemOnIndex(7);
    }

    @Test
    public void dataViewWithItem_negativeRowRequested_exceptionThrown() {
        expectedException.expect(IndexOutOfBoundsException.class);
        expectedException.expectMessage(
                "Given index -7 is outside of the accepted range '0 - 3'");

        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        dataView.getItemOnIndex(-7);
    }

    @Test
    public void dataViewWithoutItems_exceptionThrown() {
        expectedException.expect(IndexOutOfBoundsException.class);
        expectedException.expectMessage("Requested index 5 on empty data.");

        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select
                .setDataProvider(new ListDataProvider<>(new ArrayList<>()));

        dataView.getItemOnIndex(5);
    }

    @Test
    public void selectItemOnIndex_correctItemSelected() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        dataView.selectItem(1);

        Assert.assertEquals("Unexpected item selected", items[1],
                select.getValue());
    }

    @Test
    public void selectNextItem_correctItemSelected() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        dataView.selectItem(1);
        final Optional<String> nextItem = dataView.selectNextItem();

        Assert.assertTrue("No next item found.", nextItem.isPresent());
        Assert.assertEquals("Unexpected item selected as next", items[2],
                select.getValue());
        Assert.assertEquals("Unexpected item selected as next", items[2],
                nextItem.get());
    }

    @Test
    public void selectPreviousItem_correctItemSelected() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        dataView.selectItem(1);
        final Optional<String> previousItem = dataView.selectPreviousItem();

        Assert.assertTrue("No previous item found.", previousItem.isPresent());
        Assert.assertEquals("Unexpected item selected as previous", items[0],
                select.getValue());
        Assert.assertEquals("Unexpected item selected as previous", items[0],
                previousItem.get());
    }

    @Test
    public void addSizeChangeListener_sizeChanged_listenersAreNotified() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView =
                select.setDataProvider(Arrays.stream(items));

        AtomicInteger invocationCounter = new AtomicInteger(0);

        dataView.addSizeChangeListener(event -> invocationCounter.incrementAndGet());

        UI ui = new MockUI();
        ui.add(select);

        dataView.withFilter("one"::equals);
        dataView.withFilter(null);
        dataView.addItemAfter("five", "four");
        dataView.addItemBefore("zero", "one");
        dataView.addItem("last");
        dataView.removeItem("zero");

        fakeClientCall(ui);

        Assert.assertEquals(
                "Unexpected count of size change listener invocations occurred",
                1, invocationCounter.get());
    }

    @Test
    public void addSizeChangeListener_sizeNotChanged_listenersAreNotNotified() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        AtomicBoolean invocationChecker = new AtomicBoolean(false);

        UI ui = new MockUI();
        ui.add(select);

        // Make initial size change
        fakeClientCall(ui);

        dataView.addSizeChangeListener(event ->
                invocationChecker.getAndSet(true));

        dataView.withSortComparator(String::compareTo);

        // Make size change after sort. No event should be sent as size stays the same.
        fakeClientCall(ui);

        Assert.assertFalse("Unexpected size change listener invocation",
                invocationChecker.get());
    }

    @Test
    public void addSizeChangeListener_sizeChanged_newSizeSuppliedInEvent() {
        Select<String> select = new Select<>();
        SelectListDataView<String> dataView = select.setDataProvider(items);

        AtomicBoolean invocationChecker = new AtomicBoolean(false);

        UI ui = new MockUI();
        ui.add(select);

        // Make initial size event
        fakeClientCall(ui);

        dataView.addSizeChangeListener(event -> {
            Assert.assertEquals("Unexpected data size",1, event.getSize());
            invocationChecker.set(true);
        });

        dataView.withFilter("one"::equals);

        // Size change should be sent as size has changed after filtering.
        fakeClientCall(ui);

        Assert.assertTrue("Size change never called", invocationChecker.get());
    }

    private void fakeClientCall(UI ui) {
        ui.getInternals().getStateTree().runExecutionsBeforeClientResponse();
        ui.getInternals().getStateTree().collectChanges(ignore -> {
        });
    }

    private static class MockUI extends UI {

        public MockUI() {
            this(findOrcreateSession());
        }

        public MockUI(VaadinSession session) {
            getInternals().setSession(session);
            setCurrent(this);
        }

        @Override
        protected void init(VaadinRequest request) {
            // Do nothing
        }

        private static VaadinSession findOrcreateSession() {
            VaadinSession session = VaadinSession.getCurrent();
            if (session == null) {
                session = new AlwaysLockedVaadinSession(null);
                VaadinSession.setCurrent(session);
            }
            return session;
        }
    }

    private static class AlwaysLockedVaadinSession extends MockVaadinSession {

        public AlwaysLockedVaadinSession(VaadinService service) {
            super(service);
            lock();
        }

    }

    private static class MockVaadinSession extends VaadinSession {
        /*
         * Used to make sure there's at least one reference to the mock session
         * while it's locked. This is used to prevent the session from being
         * eaten by GC in tests where @Before creates a session and sets it as
         * the current instance without keeping any direct reference to it. This
         * pattern has a chance of leaking memory if the session is not unlocked
         * in the right way, but it should be acceptable for testing use.
         */
        private static final ThreadLocal<MockVaadinSession> referenceKeeper = new ThreadLocal<>();
        private ReentrantLock lock = new ReentrantLock();

        public MockVaadinSession(VaadinService service) {
            super(service);
        }

        @Override
        public void close() {
            super.close();
        }

        @Override
        public Lock getLockInstance() {
            return lock;
        }

        @Override
        public void lock() {
            super.lock();
            referenceKeeper.set(this);
        }

        @Override
        public void unlock() {
            super.unlock();
            referenceKeeper.remove();
        }
    }

}
