package com.thebest.ui.views;

import com.thebest.app.Constants;
import com.thebest.app.security.CustomRequestCache;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@Tag("sa-login-view")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends Div {
	public static final String ROUTE = "login";

	private LoginOverlay login = new LoginOverlay();

	@Autowired
	public LoginView(AuthenticationManager authenticationManager,
					 CustomRequestCache requestCache) {
		// configures login dialog and adds it to the main view

		//Image titleComponent = new Image("frontend/images/background.png", "The Best");
		login.setI18n(getLoginI18n());
		login.setTitle(getLoginTitle());
		//login.setTitle(titleComponent);
		login.setOpened(true);
		add(login);

		login.addLoginListener(e -> {
			try {

				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));

				// if authentication was successful we will update the security context and redirect to the page requested first
				if(authentication != null ) {
					login.close();
					SecurityContextHolder.getContext().setAuthentication(authentication);

					//Access to view by role
					if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals(Constants.ADMIN))) {
						UI.getCurrent().navigate(AdminView.class);
					} else if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals(Constants.USER))) {
						UI.getCurrent().navigate(BoardView.class);
					} else {
						UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
					}
				}
			} catch (AuthenticationException ex) {
				ex.printStackTrace();
				login.setError(true);
			}
		});

	}

	private H1 getLoginTitle() {
		H1 title = new H1();
		title.getStyle().set("color", "white");
		title.getStyle().set("back", "red");
		Image i = new Image("icons/logo-leantech.png", "LeanTech");
		i.setHeight("35%");
		i.setWidth("70%");
		title.add(i);
		title.add(new VerticalLayout());
		title.add(new Text("The Best"));
		Button registerButton = new Button("Register");
		registerButton.getStyle().set("color", "white");
		registerButton.getStyle().set("back", "white");
		registerButton.addClickListener(e -> {
			login.close();
			UI.getCurrent().navigate(RegisterView.class);

		});
		title.add(registerButton);

		return title;
	}

	private LoginI18n getLoginI18n() {
		LoginI18n i18n = LoginI18n.createDefault();
		i18n.setHeader(new LoginI18n.Header());
		i18n.getHeader().setTitle("The Best of Lean Tech");
		i18n.getHeader().setDescription("Choose the best");
		i18n.getForm().setUsername("User");
		i18n.getForm().setTitle("Login");
		i18n.getForm().setSubmit("Enter");
		i18n.getForm().setPassword("Password");
		i18n.getForm().setForgotPassword("Forgot Password?");
		i18n.getErrorMessage().setTitle("Invalid User/Pass combination");
		i18n.getErrorMessage().setMessage("Try again");
		i18n.setAdditionalInformation("A Lean Tech app");
		return i18n;
	}
}
