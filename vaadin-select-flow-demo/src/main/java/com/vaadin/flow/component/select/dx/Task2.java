package com.vaadin.flow.component.select.dx;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectView;
import com.vaadin.flow.component.select.data.CountryData;
import com.vaadin.flow.component.select.data.SelectListDataView;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("dx-test1-task2")
public class Task2 extends DemoView {

    private Button next;
    private Button previous;
    private Button delete;
    private Button save;
    private SelectView.Country currentCountry;
    private Span data;

    private Select<SelectView.Country> select;

    private TextField countryName;
    private TextField countryContinent;
    private TextField countryCapital;
    private Binder<SelectView.Country> binder;

    @Override
    protected void initView() {
        List<SelectView.Country> countries = getCountries();

        select = new Select<>();
        select.setLabel("Country");
        select.setTextRenderer(country -> String.join(", ",
                country.getName(),
                country.getCapital(),
                country.getContinent()));

        data = new Span();

        /* TODO: Bind 'countries' to a select component and obtain the Data
            View object */

        next = new Button("Next Country", event -> {
            /* TODO: Get the next item and select it */
            // TIP: 'currentCountry' points to current country
            // TIP: use 'selectItem' to select and store the current item
        });

        previous = new Button("Previous Country", event -> {
            /* TODO: Get the previous item and select it */
            // TIP: 'currentCountry' points to current country
            // TIP: use 'selectItem' to select and store the current item
        });

        initCountryForm();
        selectItem(/* TODO: get the first country */ null);

        delete = new Button("Remove", event -> {
            /* TODO: Delete selected country and select previous if any*/
        });

        save = new Button("Save", event -> {
            SelectView.Country newCountry = new SelectView.Country();
            binder.writeBeanIfValid(newCountry);

            /* TODO: Update or create a new country based on country form
                values, then select it */
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.START);

        verticalLayout.add(select, getFormLayout());

        addCard("Task2", verticalLayout);
    }

    private void selectItem(SelectView.Country country) {
        currentCountry = country;
        binder.readBean(currentCountry);
        data.setText(currentCountry.toString());
        select.setValue(currentCountry);

        /* TODO: Disable or enable 'next' and 'previous' buttons depending on
             counties available */
    }

    private void initCountryForm() {
        countryName = new TextField("Country Name");
        countryContinent = new TextField("Continent");
        countryCapital = new TextField("Capital");
        binder = new Binder<>();
        binder.forField(countryName).bind(SelectView.Country::getName,
                SelectView.Country::setName);
        binder.forField(countryContinent).bind(SelectView.Country::getContinent,
                SelectView.Country::setContinent);
        binder.forField(countryCapital).bind(SelectView.Country::getCapital,
                SelectView.Country::setCapital);
    }

    private VerticalLayout getFormLayout() {
        HorizontalLayout layout = new HorizontalLayout(previous, data, next);
        layout.expand(data);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setWidth("400px");

        FormLayout formLayout = new FormLayout(countryName, countryCapital,
                countryContinent);
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(delete, save);

        return new VerticalLayout(layout, formLayout, actions);
    }

    private void selectFirstCountry(SelectListDataView<SelectView.Country> dataView) {
        select.setValue(dataView.getItemOnIndex(0));
    }

    private List<SelectView.Country> getCountries() {
        CountryData countryData = new CountryData();
        return new ArrayList<>(countryData.getCountries());
    }
}
