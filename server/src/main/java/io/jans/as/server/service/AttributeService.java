/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2020, Janssen Project
 */

package io.jans.as.server.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import io.jans.as.model.configuration.AppConfiguration;

/**
 * @author Javier Rojas Blum
 * @version May 30, 2018
 */
@ApplicationScoped
public class AttributeService extends io.jans.as.common.service.AttributeService {

	@Inject
	private AppConfiguration appConfiguration;

    protected boolean isUseLocalCache() {
    	return appConfiguration.getUseLocalCache();
    }

}