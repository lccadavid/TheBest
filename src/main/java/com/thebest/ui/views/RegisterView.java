package com.thebest.ui.views;

import com.thebest.app.exceptions.DomainLogicException;
import com.thebest.model.User;
import com.thebest.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route(value="register")
public class RegisterView extends Div {

    @Autowired
    UserService userService;

    public RegisterView() {
        FormLayout fl = new FormLayout();
        fl.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));

        TextField firstName = new TextField();
        firstName.setPlaceholder("First Name");
        TextField lastName = new TextField();
        lastName.setPlaceholder("Last Name");
        TextField email = new TextField();
        email.setPlaceholder("Email");
        PasswordField pass = new PasswordField();
        pass.setPlaceholder("Password");
        TextField description = new TextField();
        description.setPlaceholder("Enter a short description about yourself");

        NativeButton save = new NativeButton("Save");
        NativeButton cancel = new NativeButton("Cancel");

        HorizontalLayout actions = new HorizontalLayout();

        save.addClickListener(e -> {
            User user = new User();
            user.setEmail(email.getValue());
            user.setName(firstName.getValue());
            user.setLastName(lastName.getValue());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(pass.getValue()));
            user.setDescriptionProfile(description.getValue());
            user.setUserType(User.UserType.USER);
            try {
                userService.register(user);
            } catch (DomainLogicException ex) {
                Notification.show("Error",2000, Notification.Position.BOTTOM_CENTER);
            }
            Notification.show("User registered successfully",3000, Notification.Position.BOTTOM_CENTER);
            UI.getCurrent().navigate("login");
        });

        actions.add(save, cancel);
        save.getStyle().set("marginRight", "10px");

        fl.add(firstName, lastName,  pass, email);
        fl.add(description, 3);
        fl.add(actions);
        add(fl);
    }


}
