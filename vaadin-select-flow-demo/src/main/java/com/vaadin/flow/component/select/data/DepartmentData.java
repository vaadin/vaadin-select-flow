package com.vaadin.flow.component.select.data;

import com.vaadin.flow.component.select.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentData {

    public final List<Department> DEPARTMENT_LIST = new ArrayList<>();

    {
        DEPARTMENT_LIST.add(new Department(1, "Product"));
        DEPARTMENT_LIST.add(new Department(2, "Service"));
        DEPARTMENT_LIST.add(new Department(1, "HR"));
        DEPARTMENT_LIST.add(new Department(1, "Accounting"));
    }

    public List<Department> getDepartments() {
        return DEPARTMENT_LIST;
    }
}
