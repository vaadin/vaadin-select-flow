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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("dx-test1-task2")
public class Task2 extends DemoView {

    private Button next;
    private Button previous;
    private Button delete;
    private Button save;
    private SelectView.Country currentCountry;
    private Span data = new Span();

    private Select<SelectView.Country> select;

    private TextField countryName;
    private TextField countryContinent;
    private TextField countryCapital;
    private Binder<SelectView.Country> binder;

    @Override
    protected void initView() {
        // Show the provided list of data in the Select component
        List<SelectView.Country> countries = getCountries();

        select = new Select<>();
        select.setLabel("Country");
        select.setTextRenderer(SelectView.Country::getName);

        /* TODO: Add data binding code here */

        // Add buttons that switch and select the next and previous items from the
        // Select component

        next = new Button("Next Country", event -> {
            /* TODO: Add code here */
        });

        previous = new Button("Previous Country", event -> {
            /* TODO: Add code here */
        });

        initCountryForm();
        selectItem(countries.get(0));

        // Add a button that deletes the item from Select component

        delete = new Button("Remove", event -> {
            /* TODO: Add delete button code here */
        });

        // Make the save button add the current Country to the Select component in case
        // it is not there yet

        save = new Button("Save", event -> {
            /* TODO: Add save button code here */
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.START);

        verticalLayout.add(select, getFormLayout());

        addCard("Task2", verticalLayout);
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

        return new VerticalLayout(new Span("Click outside to close"), layout,
                formLayout, actions);
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

    private void selectItem(SelectView.Country country) {
        currentCountry = country;
        binder.readBean(currentCountry);
        data.setText(currentCountry.toString());

        // Disable the buttons respectively when the first or last item is selected

        /* TODO: Add code here */
    }

    private List<SelectView.Country> getCountries() {
        CountryData countryData = new CountryData();
        return countryData.getCountries();
    }
}
