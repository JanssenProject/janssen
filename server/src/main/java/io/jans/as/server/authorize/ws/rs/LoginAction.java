/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2020, Janssen Project
 */

package io.jans.as.server.authorize.ws.rs;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;


/**
 * @author Javier Rojas Blum
 * @version May 24, 2016
 */
@RequestScoped
@Named
public class LoginAction {

    @Inject
    private Logger log;

    private String loginHint;

    public String getLoginHint() {
        return loginHint;
    }

    public void setLoginHint(String loginHint) {
        this.loginHint = loginHint;
    }

}
