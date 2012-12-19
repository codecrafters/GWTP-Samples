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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.server.atmosphere.PollCallback;
import com.gwtplatform.dispatch.server.atmosphere.PollExecutionContext;
import com.gwtplatform.dispatch.server.atmosphere.actionhandler.AbstractPollActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.gwtplatform.samples.basic.shared.FieldVerifier;
import com.gwtplatform.samples.basic.shared.SendTextToServer;
import com.gwtplatform.samples.basic.shared.SendTextToServerResult;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Philippe Beaudoin
 */
public class SendTextToServerHandler extends
        AbstractPollActionHandler<SendTextToServer, SendTextToServerResult> {

    private Provider<HttpServletRequest> requestProvider;
    private ServletContext servletContext;

    @Inject
    SendTextToServerHandler(ServletContext servletContext,
                            Provider<HttpServletRequest> requestProvider) {
        super(SendTextToServer.class);
        this.servletContext = servletContext;
        this.requestProvider = requestProvider;
    }

    @Override
    public void execute(SendTextToServer action, PollExecutionContext context, PollCallback<SendTextToServer, SendTextToServerResult> callback) throws ActionException {

        String input = action.getTextToServer();

        // Verify that the input is valid.
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new ActionException("Name must be at least 4 characters long");
        }

        String serverInfo = servletContext.getServerInfo();
        String userAgent = requestProvider.get().getHeader("User-Agent");

        // delegate the heavy work of string-building to a new thread
        new StringWorker(input, serverInfo, userAgent, callback).start();
    }

    @Override
    public void undo(SendTextToServer action, SendTextToServerResult result, PollExecutionContext context, PollCallback<SendTextToServer, SendTextToServerResult> callback) throws ActionException {
        // Not undoable
    }
}
