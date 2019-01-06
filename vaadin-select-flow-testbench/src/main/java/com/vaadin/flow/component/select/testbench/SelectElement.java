package com.vaadin.flow.component.select.testbench;

/*
 * #%L
 * Vaadin ComponentSkeleton Testbench API
 * %%
 * Copyright (C) 2018 Vaadin Ltd
 * %%
 * This program is available under Commercial Vaadin Add-On License 3.0
 * (CVALv3).
 *
 * See the file license.html distributed with this software for more
 * information about licensing.
 *
 * You should have received a copy of the CVALv3 along with this program.
 * If not, see <http://vaadin.com/license/cval-3>.
 * #L%
 */

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.common.testbench.HasLabel;
import com.vaadin.flow.component.common.testbench.HasPlaceholder;
import com.vaadin.flow.component.common.testbench.HasSelectByText;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.commands.TestBenchCommandExecutor;
import com.vaadin.testbench.elementsbase.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CommandExecutor;

@Element("vaadin-select")
public class SelectElement extends TestBenchElement implements HasSelectByText, HasLabel, HasPlaceholder {

    @Element("vaadin-item")
    public static class ItemElement extends TestBenchElement {
        public ItemElement() {
            // needed for creating instances inside TB
        }

        // used to convert in streams
        ItemElement(WebElement item, TestBenchCommandExecutor commandExecutor) {
            super(item, commandExecutor);
        }
    }

    /**
     * Opens the popup with options, if it is not already open.
     */
    public void openPopup() {
        setProperty("opened", true);
    }

    /**
     * Closes the popup with options, if it is open.
     */
    public void closePopup() {
        setProperty("opened", false);
    }

    public boolean isOpened() {
        return getPropertyBoolean("opened");
    }

    public void selectItemByIndex(int index) {
        openPopup();
        getItems().get(index).click();
    }

    public Stream<ItemElement> getItemsStream() {
        openPopup();
        List<WebElement> elements = getDriver().findElement(By.tagName("vaadin-select-overlay"))
                .findElement(By.tagName("vaadin-list-box"))
                .findElements(By.tagName("vaadin-item"));
        if (elements.size() == 0) {
            return Stream.<ItemElement>builder().build();
        }
        return elements
                .stream().map(item -> new ItemElement(item, getCommandExecutor()));
    }

    public List<ItemElement> getItems() {
        return getItemsStream().collect(Collectors.toList());
    }

    @Override
    public void selectByText(String text) {
        getItemsStream().filter(item -> text.equals(item.getText())).findFirst().get().click();
    }

    @Override
    public String getSelectedText() {
        return getSelectedItem().getText();
    }

    public ItemElement getSelectedOptionItem() {
        return getItemsStream()
                .filter(element -> element.hasAttribute("selected"))
                .findAny().orElseThrow(() -> new NoSuchElementException("No item selected from popup"));
    }

    public ItemElement getSelectedItem() {
        TestBenchElement textFieldElement = $("vaadin-select-text-field").first();
        TestBenchElement itemSlotElement = wrapElement(textFieldElement
                .findElement(By.xpath("div[@part=\"value\"]")), getCommandExecutor());
        return itemSlotElement.$(ItemElement.class).first();
    }
}
