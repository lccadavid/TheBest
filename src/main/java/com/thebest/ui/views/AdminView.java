package com.thebest.ui.views;

import com.thebest.app.Constants;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import com.thebest.app.security.SecuredByRole;

@Route("admin")
@SecuredByRole(Constants.ADMIN)
public class AdminView extends VerticalLayout {
    @Autowired
    public AdminView() {
        Label label = new Label("Looks like you are admin!");
        add(label);
    }

}
