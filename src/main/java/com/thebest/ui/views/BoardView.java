package com.thebest.ui.views;

import com.thebest.app.Constants;
import com.thebest.app.security.SecuredByRole;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("board")
@SecuredByRole(Constants.USER)
public class BoardView extends VerticalLayout {
    @Autowired
    public BoardView() {
        Label label = new Label("Hello Paul!");
        add(label);
    }
}
