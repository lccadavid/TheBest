package com.thebest.app.security;

import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.NotFoundException;
import org.ilay.Access;
import org.ilay.AccessEvaluator;
import com.thebest.ui.views.LoginView;

public class RoleBasedEvaluator implements AccessEvaluator<SecuredByRole> {
    @Override
    public Access evaluate(Location location, Class navigationTarget, SecuredByRole annotation) {
        if(!SecurityUtils.isAccessGranted(navigationTarget, annotation)) {
            if(SecurityUtils.isUserLoggedIn()) {
                return Access.restricted(NotFoundException.class);
            } else {
                return Access.restricted(LoginView.ROUTE);
            }
        }

        return Access.granted();
    }
}