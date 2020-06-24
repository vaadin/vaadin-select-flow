package com.vaadin.flow.component.select.dx;

import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.select.SelectView;
import com.vaadin.flow.component.select.data.CountryData;
import com.vaadin.flow.component.select.data.SelectListDataView;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("dx-test1-task1")
public class Task1 extends DemoView {

    private Select<SelectView.Country> select;

    private TextField byNameTextField;

    private TextField byContinentTextField;

    private final SerializablePredicate<SelectView.Country> byNameFilter =
            c -> c.getName().toLowerCase()
                    .contains(byNameTextField.getValue().toLowerCase());

    private final SerializablePredicate<SelectView.Country> byContinentFilter =
            c -> c.getContinent().toLowerCase()
                    .contains(byContinentTextField.getValue().toLowerCase());

    @Override
    protected void initView() {
        List<SelectView.Country> countries = getCountries();

        select = new Select<>();
        select.setLabel("Country");
        select.setTextRenderer(country -> String.join(", ",
                country.getName(),
                country.getCapital(),
                country.getContinent()));

        /* TODO: Bind 'countries' to a select component and obtain the Data
            View object */

        // TODO: Use 'selectFirstCountry' method to select the first item

        byNameTextField = new TextField("Filter By Name",
                event -> {
                    /*
                    *  TODO: Add countries filtering by name
                    *  TIP: Use 'applyFilter'
                    */
                });
        byContinentTextField = new TextField("Filter By Continent",
                event -> {
                    /*
                     *  TODO: Add countries filtering by continent
                     *  TIP: Use 'applyFilter'
                     */
                });

        Checkbox sortByCapital = new Checkbox("Sort By Capital",
                event -> {
                    /* TODO: Add countries sorting by capital ascending
                        if this 'sortByCapital' checkbox is ticket, clear
                        sorting otherwise */

                    // TODO: Use 'selectFirstCountry' method to select the first item
                });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setDefaultHorizontalComponentAlignment(
                FlexComponent.Alignment.START);

        verticalLayout.add(select, byNameTextField, byContinentTextField,
                sortByCapital);

        addCard("Task1", verticalLayout);
    }

    private void selectFirstCountry(SelectListDataView<SelectView.Country> dataView) {
        // TODO: select the first country in the list
    }

    private void applyFilter(SelectListDataView<SelectView.Country> dataView) {
        // TODO: apply the filters and select the first item in the list
        // TIP: Use byNameFilter and byContinentFilter
    }

    private List<SelectView.Country> getCountries() {
        CountryData countryData = new CountryData();
        return new ArrayList<>(countryData.getCountries());
    }
}
