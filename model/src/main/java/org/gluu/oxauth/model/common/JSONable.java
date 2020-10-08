/*
 * Janssen Project software is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2020, Janssen Project
 */

package org.gluu.oxauth.model.common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Rojas Blum Date: 13.01.2013
 */
public interface JSONable {

    JSONObject toJSONObject() throws JSONException;
}