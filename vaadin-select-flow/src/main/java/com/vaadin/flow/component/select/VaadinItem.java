package com.vaadin.flow.component.select;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.data.binder.HasItemsAndComponents;

@Tag("vaadin-item")
public class VaadinItem<T> extends Component implements HasItemsAndComponents.ItemComponent<T>, HasComponents, HasStyle, HasText {

    private T item;

    VaadinItem(String key, T item) {
        this.item = item;
        getElement().setProperty("value", key);
        getElement().setAttribute("value", key);
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void onEnabledStateChanged(boolean enabled) {
        // Not setting the disabled attribute because vaadin-item's that are
        // disabled cannot be selected at the same time.
        // the Element.setEnabled(...) that calls this method will handle
        // the disabling of the state node and triggering disabled attribute
        // for any child items.
        // When an item is disabled with the item enabled provider, select will
        // add the disabled attribute.
    }
}
