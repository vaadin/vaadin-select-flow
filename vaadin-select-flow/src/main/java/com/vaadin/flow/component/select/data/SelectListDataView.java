package com.vaadin.flow.component.select.data;

import java.util.Optional;
import java.util.function.Function;

import com.vaadin.flow.data.provider.AbstractListDataView;

/**
 * An implementation of {@link SelectDataView} for in-memory list data handling
 * 
 * @param <T>
 *            item type
 */
public class SelectListDataView<T> extends AbstractListDataView<T>
        implements SelectDataView<T> {
    /**
     * Constructs a new SelectListDataView
     * 
     * @param dataController
     *            a SelectDataController
     */
    public SelectListDataView(SelectDataController<T> dataController) {
        super(dataController);
    }

    @Override
    public T getItem(int index) {
        return getAllItems().skip(index).findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);
    }

    @Override
    public T selectItem(int index) {
        T item = getItem(index);
        getDataController().select(item);

        return item;
    }

    @Override
    public Optional<T> selectNextItem() {
        return selectAnotherItem(this::getNextItem);
    }

    @Override
    public Optional<T> selectPreviousItem() {
        return selectAnotherItem(this::getPreviousItem);
    }

    private Optional<T> selectAnotherItem(Function<T, T> otherItem) {
        T selectedItem = getDataController().getSelectedItem();
        if (selectedItem == null)
            return Optional.empty();
        T previousItem = otherItem.apply(selectedItem);
        if (previousItem == null)
            return Optional.empty();
        getDataController().select(previousItem);
        return Optional.of(previousItem);
    }

    @Override
    protected SelectDataController<T> getDataController() {
        return (SelectDataController<T>) super.getDataController();
    }
}
