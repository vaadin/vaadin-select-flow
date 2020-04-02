package com.vaadin.flow.component.select.osgi;


import com.vaadin.flow.osgi.support.OsgiVaadinStaticResource;
import org.osgi.service.component.annotations.Component;

import java.io.Serializable;

@Component(immediate = true, service = OsgiVaadinStaticResource.class)
public class SelectConnectorResource implements OsgiVaadinStaticResource, Serializable {
    @Override
    public String getPath() {
        return "/META-INF/resources/frontend/selectConnector.js";
    }

    @Override
    public String getAlias() {
        return "/frontend/selectConnector.js";
    }
}
