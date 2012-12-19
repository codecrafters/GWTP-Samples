/**
 * Copyright 2011 ArcBees Inc.
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

package com.gwtplatform.samples.basic.server;

import com.google.inject.servlet.ServletModule;
import org.atmosphere.guice.GuiceManagedAtmosphereServlet;

import java.util.HashMap;

/**
 * @author Philippe Beaudoin
 */
public class DispatchServletModule extends ServletModule {

    @Override
    public void configureServlets() {
        serve("/dispatch-async/*").with(
                GuiceManagedAtmosphereServlet.class, new HashMap<String, String>() {
            {
                put("org.atmosphere.useWebSocket", "false");
                put("org.atmosphere.useNative", "true");
                put("org.atmosphere.disableOnStateEvent", "true");
            }
        });
    }
}
