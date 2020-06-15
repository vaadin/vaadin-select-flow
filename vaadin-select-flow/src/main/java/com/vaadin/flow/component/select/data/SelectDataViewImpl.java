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

import java.util.Objects;
import java.util.stream.Stream;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SizeChangeEvent;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.shared.Registration;

/**
 * Implementation of the base DataView applicable for use with any
 * DataProvider.
 * Note that this has a minimal API and using {@link SelectListDataView}
 * is advised.
 *
 * @param <T>
 *         item type
 * @since
 */
public class SelectDataViewImpl<T> implements SelectDataView<T> {

    protected SerializableSupplier<? extends DataProvider<T, ?>> dataProviderSupplier;
    protected Select<T> select;

    /**
     * Constructs a new DataView.
     *
     * @param dataProviderSupplier
     *         data provider supplier
     * @param select
     *         select instance for this DataView
     */
    public SelectDataViewImpl(
            SerializableSupplier<DataProvider<T, ?>> dataProviderSupplier,
            Select<T> select) {
        this.dataProviderSupplier = dataProviderSupplier;
        this.select = select;
    }

    @Override
    public T getItemOnIndex(int index) {
        final int dataSize = getSize();
        if (index < 0 || index >= dataSize) {
            throw new IndexOutOfBoundsException(String.format(
                    "Given index %d is outside of the accepted range '0 - %d'",
                    index, dataSize - 1));
        }
        return getItems().skip(index).findFirst().orElse(null);
    }

    @Override
    public Stream<T> getItems() {
        return dataProviderSupplier.get().fetch(new Query<>());
    }

    @Override
    public int getSize() {
        return dataProviderSupplier.get().size(new Query<>());
    }

    @Override
    public boolean contains(T item) {
        final DataProvider<T, ?> dataProvider = dataProviderSupplier.get();
        final Object itemIdentifier = dataProvider.getId(item);
        return getItems()
                .anyMatch(i -> itemIdentifier.equals(dataProvider.getId(i)));
    }

    @Override
    public Registration addSizeChangeListener(
            ComponentEventListener<SizeChangeEvent<?>> listener) {
        Objects.requireNonNull(listener, "SizeChangeListener cannot be null");
        return ComponentUtil.addListener(select, SizeChangeEvent.class,
                (ComponentEventListener) listener);
    }

}
