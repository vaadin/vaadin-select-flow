package com.vaadin.flow.component.select.data;

import java.util.stream.Stream;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.DataController;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SizeChangeListener;
import com.vaadin.flow.shared.Registration;

/**
 * DataController implementation for Select component.
 * 
 * @param <T>
 *            item type
 * @since
 */
public class SelectDataController<T> implements DataController<T> {
    private Select<T> select;

    /**
     * Constructs a new SelectDataController
     * 
     * @param select
     *            the select component
     */
    public SelectDataController(Select<T> select) {
        this.select = select;
    }

    @Override
    public DataProvider<T, ?> getDataProvider() {
        return select.getDataProvider();
    }

    @Override
    public Registration addSizeChangeListener(
            SizeChangeListener sizeChangeListener) {
        throw new UnsupportedOperationException(
                "SelectDataController doesn't support addSizeChangeListener.");
    }

    @Override
    public int getDataSize() {
        return getDataProvider().size(new Query<>());
    }

    @Override
    public Stream<T> getAllItems() {
        return getDataProvider().fetch(new Query<>());
    }

    /**
     * Selects given item in controller select component.
     *
     * @param item
     *            item to select
     */
    public void select(T item) {
        select.setValue(item);
    }

    /**
     * Gets the selected item in controller select component.
     * 
     * @return the selected item in controller select component
     */
    public T getSelectedItem() {
        return select.getValue();
    }
}
