package com.vaadin.flow.component.select.test;

import org.junit.Test;

public class ItemLabelGeneratorIT extends AbstractSelectIT {

    @Test
    public void testItemLabelGenerator_setGenerator_updatesItemLabel() {
        page.toggleItemLabelGenerator(true);
        verifyItems("-LABEL");

        page.toggleItemLabelGenerator(false);
        verifyItems("");
    }

    private void verifyItems(String labelPostfix) {
        for (int i = 0; i < getInitialNumberOfItems(); i++) {
            selectElement.selectItemByIndex(i);
            verify.selectedItem("Item-" + i, "Item-" + i + labelPostfix);
        }
    }

    @Test
    public void testItemLabelGenerator_initialItemLabelGenerator_setsItemLabels() {
        openWithExtraParameter("itemLabelGenerator", true);
        verifyItems("-LABEL");
    }

    @Override
    protected int getInitialNumberOfItems() {
        return 10;
    }
}
