/*
 * Janssen Project software is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2020, Janssen Project
 */

package io.jans.as.client.interop;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import io.jans.as.client.BaseTest;
import org.testng.annotations.Test;

/**
 * OC5:FeatureTest-Support WebFinger Discovery
 *
 * @author Javier Rojas Blum
 * @version 0.9, 06/09/2014
 */
public class SupportWebFingerDiscovery extends BaseTest {

    @Test
    public void supportWebFingerDiscovery() {
        showTitle("OC5:FeatureTest-Support WebFinger Discovery");

        assertTrue(StringUtils.isNotBlank(configurationEndpoint));
    }
}