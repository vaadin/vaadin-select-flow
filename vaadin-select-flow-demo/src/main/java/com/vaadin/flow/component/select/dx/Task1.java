package com.vaadin.flow.component.select.dx;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectView;
import com.vaadin.flow.component.select.data.CountryData;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("dx-test1-task1")
public class Task1 extends DemoView {

    @Override
    protected void initView() {
        // Show the provided list of data in the Select component
        List<SelectView.Country> countries = getCountries();

        Select<SelectView.Country> select = new Select<>();
        select.setLabel("Country");
        select.setTextRenderer(SelectView.Country::getName);

        /* TODO: Add data binding code here */

        // Add a label that shows the number of items in the Select component
        Label size = new Label("Total size: " /* TODO: Add data size code here */);

        // Make the text field value change event filter the items in the Select component

        TextField filter = new TextField("Filter",
                event -> {
                    /* TODO: Add data filtering code here */
                });

        Checkbox sortByContinent = new Checkbox("Sort By Continent",
                event -> {
                    /* TODO: Add data sorting code here */
                });

        // Make the number of items label update when the data in the Select component
        // changes due to filtering

        /* TODO: Add code here */

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.START);

        verticalLayout.add(select, filter, sortByContinent, size);

        addCard("Task1", verticalLayout);
    }

    private List<SelectView.Country> getCountries() {
        CountryData countryData = new CountryData();
        return countryData.getCountries();
    }
}
