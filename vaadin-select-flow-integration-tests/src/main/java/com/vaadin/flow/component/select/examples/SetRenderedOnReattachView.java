/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.component.select.examples;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("render-reattach")
public class SetRenderedOnReattachView extends Div {

    public SetRenderedOnReattachView() {
        Select<String> select = new Select<>();
        select.setRenderer(new ComponentRenderer<>(item -> new Span(item)));
        select.setItems("a", "b", "c");
        select.setValue("a");
        add(select);

        NativeButton remove = new NativeButton("Remove", event -> {
            remove(select);
            NativeButton add = new NativeButton("add", ev -> {
                add(select);
                select.setRenderer(
                        new ComponentRenderer<>(item -> new Span(item)));
            });
            add(add);
            add.setId("add");
            remove(event.getSource());
        });
        remove.setId("remove");
        add(remove);
    }

}
