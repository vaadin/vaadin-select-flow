package com.vaadin.flow.component.select;

import java.util.List;
import java.util.Objects;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.data.CountryData;
import com.vaadin.flow.component.select.data.DepartmentData;
import com.vaadin.flow.component.select.data.SelectListDataView;
import com.vaadin.flow.component.select.data.TeamData;
import com.vaadin.flow.component.select.entity.Department;
import com.vaadin.flow.component.select.entity.Team;
import com.vaadin.flow.component.select.entity.Weekday;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

@Route("vaadin-select")
public class SelectView extends DemoView {


    // begin-source-example
    // source-example-heading: Select example model
    public static class Country {
        private String name;
        private String continent;
        private String capital;

        public Country(String name, String continent, String capital) {
            this.name = name;
            this.continent = continent;
            this.capital = capital;
        }

        public String getName() {
            return name;
        }

        public String getContinent() {
            return continent;
        }

        public String getCapital() {
            return capital;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Country country = (Country) o;
            return name.equals(country.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    // end-source-example


    @Override
    protected void initView() {
        basicDemo();// Basic usage
        disabledAndReadonly();
        entityList();
        valueChanged();
        disabledItem();
        simpleDataNavigationDemo();
        configurationForRequiredDemo();// Validation
        formFieldDemo();
        separatorDemo();// Presentation
        customOptionsDemo();
        themeVariantsTextAlign(); // ThemeVariants
        themeVariantsSmallSize();
        styling();// Styling
    }

    private void basicDemo() {
        Div div = new Div();

        // begin-source-example
        // source-example-heading: Basic usage
        Select<String> labelSelect = new Select<>();
        labelSelect.setDataProvider("Option one", "Option two");
        labelSelect.setLabel("Label");

        Select<String> placeholderSelect = new Select<>();
        placeholderSelect.setDataProvider("Option one", "Option two");
        placeholderSelect.setPlaceholder("Placeholder");

        Select<String> valueSelect = new Select<>();
        valueSelect.setDataProvider("Value", "Option one", "Option two");
        valueSelect.setValue("Value");

        // end-source-example
        labelSelect.getStyle().set("margin-right", "5px");
        placeholderSelect.getStyle().set("margin-right", "5px");
        div.add(labelSelect, placeholderSelect, valueSelect);
        addCard("Basic usage", div);
    }

    private void disabledAndReadonly() {
        // begin-source-example
        // source-example-heading: Disabled and read-only
        Select<String> disabledSelect = new Select<>("Value");
        disabledSelect.setEnabled(false);
        disabledSelect.setValue("Value");
        disabledSelect.setLabel("Disabled");

        Select<String> readOnlySelect = new Select<>("Value");
        readOnlySelect.setReadOnly(true);
        readOnlySelect.setValue("Value");
        readOnlySelect.setLabel("Read-only");
        // end-source-example
        disabledSelect.getStyle().set("margin-right", "5px");
        HorizontalLayout layout = new HorizontalLayout(disabledSelect,
                readOnlySelect);
        layout.getStyle().set("flex-wrap", "wrap");
        addCard("Disabled and read-only", layout);
    }

    private List<Department> getDepartments() {

        DepartmentData departmentData = new DepartmentData();
        return departmentData.getDepartments();
    }

    private List<Team> getTeams() {
        TeamData teamData = new TeamData();
        return teamData.getTeams();
    }

    private List<Country> getCountries() {
        CountryData countryData = new CountryData();
        return countryData.getCountries();
    }

    private void entityList() {
        // begin-source-example
        // source-example-heading: Entity list
        Select<Department> select = new Select<>();
        select.setLabel("Department");
        List<Department> departmentList = getDepartments();

        // Choose which property from Department is the presentation value
        select.setItemLabelGenerator(Department::getName);
        select.setDataProvider(departmentList);
        // end-source-example
        addCard("Entity list", select);
    }

    private void valueChanged() {
        // begin-source-example
        // source-example-heading: Value change event
        Select<String> select = new Select<>();
        select.setLabel("Name");
        select.setDataProvider("Option one", "Option two");

        Div value = new Div();
        value.setText("Select a value");
        select.addValueChangeListener(
                event -> value.setText("Selected: " + event.getValue()));
        // end-source-example
        VerticalLayout verticalLayout = new VerticalLayout(select, value);
        verticalLayout.setAlignItems(FlexComponent.Alignment.START);
        addCard("Value change event", verticalLayout);
    }

    private void disabledItem() {
        // begin-source-example
        // source-example-heading: Disabled item
        Select<Team> select = new Select<>();
        select.setLabel("Team");
        List<Team> teamList = getTeams();

        // Convenience setter for creating a TextRenderer from the given
        // function that converts the item to a string.
        select.setTextRenderer(Team::getName);
        select.setDataProvider(teamList);
        select.setItemEnabledProvider(
                item -> !"Developers Journey and Onboarding"
                        .equals(item.getName()));

        // end-source-example
        addCard("Disabled item", select);
    }

    private void simpleDataNavigationDemo() {
        // begin-source-example
        // source-example-heading: Data navigation
        Select<Country> select = new Select<>();
        select.setLabel("Country");
        select.setTextRenderer(Country::getName);

        //
        final SelectListDataView<Country> dataView = select
                .setDataProvider(getCountries());

        // Navigate data using dataView selectNextItem and selectPreviousItem
        Button previous = new Button(VaadinIcon.ARROW_LEFT.create(),
                event -> dataView.selectPreviousItem());
        Button next = new Button(VaadinIcon.ARROW_RIGHT.create(),
                event -> dataView.selectNextItem());

        // Arrow buttons to the side and select in the middle
        HorizontalLayout horizontalLayout = new HorizontalLayout(previous,
                select, next);
        horizontalLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.END);

        Span continent = new Span();
        Span capital = new Span();

        select.addValueChangeListener(event -> {
            capital.setText("Capital: " + event.getValue().getCapital());
            continent.setText("Continent: " + event.getValue().getContinent());

            // enabled/disable buttons depending on if we have next or previous item
            previous.setEnabled(dataView.hasPreviousItem(event.getValue()));
            next.setEnabled(dataView.hasNextItem(event.getValue()));
        });

        dataView.selectItem(0);
        // end-source-example

        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout,
                continent, capital);
        addCard("Data navigation", verticalLayout);
    }

    private void configurationForRequiredDemo() {
        // begin-source-example
        // source-example-heading: Required
        Select<String> requiredSelect = new Select<>();
        requiredSelect.setRequiredIndicatorVisible(true);
        requiredSelect.setLabel("Required");

        requiredSelect.setDataProvider("Option one", "Option two", "Option three");

        // The empty selection item is the first item that maps to an null item.
        // As the item is not selectable, using it also as placeholder
        requiredSelect.setPlaceholder("Select an option");
        requiredSelect.setEmptySelectionCaption("Select an option");
        requiredSelect.setEmptySelectionAllowed(true);

        // add a divider after the empty selection item
        requiredSelect.addComponents(null, new Hr());
        // end-source-example
        FlexLayout layout = new FlexLayout(requiredSelect);
        layout.getStyle().set("flex-wrap", "wrap");
        addCard("Validation", "Required", layout);
    }

    private void formFieldDemo() {
        // begin-source-example
        // source-example-heading: Using with Binder
        Employee employee = new Employee();
        Binder<Employee> binder = new Binder<>();

        Select<String> titleSelect = new Select<>();
        titleSelect.setLabel("Title");
        titleSelect.setDataProvider("Account Manager", "Designer", "Marketing Manager",
                "Developer");

        titleSelect.setEmptySelectionAllowed(true);
        titleSelect.setEmptySelectionCaption("Select your title");
        titleSelect.addComponents(null, new Hr());

        binder.forField(titleSelect)
                .asRequired(
                        "Please choose the option closest to your profession")
                .bind(Employee::getTitle, Employee::setTitle);

        // Note that another option of handling the unselected item (null) is:
        // binding.withNullRepresentation("Select your title")
        // That only works when the select item type is String.

        Button button = new Button("Submit", event -> {
            if (binder.writeBeanIfValid(employee)) {
                Notification.show("Submit successful", 2000,
                        Notification.Position.MIDDLE);
            }
        });
        // end-source-example
        HorizontalLayout layout = new HorizontalLayout(titleSelect, button);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        addCard("Validation", "Using with Binder", layout);
    }

    private void separatorDemo() {
        // begin-source-example
        // source-example-heading: Separators
        Select<Weekday> select = new Select<>();
        select.setLabel("Weekday");
        select.setEmptySelectionCaption("Weekdays");
        select.setItemEnabledProvider(Objects::nonNull);
        select.setEmptySelectionAllowed(true);
        select.setDataProvider(Weekday.values());
        select.addComponents(null, new Hr());
        select.addComponents(Weekday.FRIDAY, new Hr());
        // end-source-example
        addCard("Presentation", "Separators", select);
    }

    private void customOptionsDemo() {
        // begin-source-example
        // source-example-heading: Customizing drop down options
        Select<Emotion> select = new Select<>();
        select.setLabel("How are you feeling today?");

        select.setDataProvider(new Emotion("Good", VaadinIcon.THUMBS_UP),
                new Emotion("Bad", VaadinIcon.THUMBS_DOWN),
                new Emotion("Meh", VaadinIcon.MEH_O),
                new Emotion("This is fine", VaadinIcon.FIRE));

        select.setRenderer(new ComponentRenderer<>(emotion -> {
            Div text = new Div();
            text.setText(emotion.getText());

            FlexLayout wrapper = new FlexLayout();
            text.getStyle().set("margin-left", "0.5em");
            wrapper.add(emotion.getIcon().create(), text);
            return wrapper;
        }));

        // Note that if the setItemLabelGenerator(...) is applied, the label
        // string is shown in the input field instead of the components (icon +
        // text)
        // end-source-example

        addCard("Presentation", "Customizing drop down options", select);
    }

    private void themeVariantsTextAlign() {
        Div div = new Div();
        // begin-source-example
        // source-example-heading: Text align
        Select<String> leftSelect = new Select<>();
        leftSelect.setDataProvider("Left", "Center", "Right");
        leftSelect.setValue("Left");
        leftSelect.getElement().setAttribute("theme", "align-left");

        Select<String> centerSelect = new Select<>();
        centerSelect.setDataProvider("Left", "Center", "Right");
        centerSelect.setValue("Center");
        centerSelect.getElement().setAttribute("theme", "align-center");

        Select<String> rightSelect = new Select<>();
        rightSelect.setDataProvider("Left", "Center", "Right");
        rightSelect.setValue("Right");
        rightSelect.getElement().setAttribute("theme", "align-right");
        // end-source-example
        div.add(leftSelect, centerSelect, rightSelect);
        leftSelect.getStyle().set("margin-right", "5px");
        centerSelect.getStyle().set("margin-right", "5px");
        addCard("Theme Variants", "Text align", div);

    }

    private void themeVariantsSmallSize() {
        // begin-source-example
        // source-example-heading: Small size
        Select<String> select = new Select<>("Label");
        select.setDataProvider("Option one", "Option two");
        select.setPlaceholder("Placeholder");
        select.getElement().setAttribute("theme", "small");
        // end-source-example
        addCard("Theme Variants", "Small size", select);
    }

    private void styling() {

        Div firstDiv = new Div();
        firstDiv.setText(
                "To read about styling you can read the related tutorial in");
        Anchor firstAnchor = new Anchor(
                "https://vaadin.com/docs/flow/theme/using-component-themes.html",
                "Using Component Themes");

        Div secondDiv = new Div();
        secondDiv.setText("To know about styling in html you can read the ");
        Anchor secondAnchor = new Anchor(
                "https://vaadin.com/components/vaadin-select/html-examples/select-styling-demos",
                "HTML Styling Demos");

        HorizontalLayout firstHorizontalLayout = new HorizontalLayout(firstDiv,
                firstAnchor);
        HorizontalLayout secondHorizontalLayout = new HorizontalLayout(
                secondDiv, secondAnchor);
        // begin-source-example
        // source-example-heading: Styling references

        // end-source-example
        addCard("Styling", "Styling references", firstHorizontalLayout,
                secondHorizontalLayout);
    }

    private static class Employee {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private static class Emotion {
        private String text;
        private VaadinIcon icon;

        private Emotion(String text, VaadinIcon icon) {
            this.text = text;
            this.icon = icon;
        }

        public String getText() {
            return text;
        }

        public VaadinIcon getIcon() {
            return icon;
        }
    }
}
