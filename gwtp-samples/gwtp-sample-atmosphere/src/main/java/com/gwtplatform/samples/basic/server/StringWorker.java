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

import com.gwtplatform.dispatch.server.atmosphere.PollCallback;
import com.gwtplatform.samples.basic.shared.SendTextToServer;
import com.gwtplatform.samples.basic.shared.SendTextToServerResult;

class StringWorker extends Thread {

    private final String input, serverInfo, userAgent;

    private final PollCallback<SendTextToServer, SendTextToServerResult> callback;

    StringWorker(final String input, final String serverInfo, final String userAgent, final PollCallback<SendTextToServer, SendTextToServerResult> callback) {
        this.input = input;
        this.serverInfo = serverInfo;
        this.userAgent = userAgent;
        this.callback = callback;
    }

    @Override
    public void run() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Hello, ");
        sb.append(input);
        sb.append("!<br><br>I am running ");
        sb.append(serverInfo);
        sb.append(".<br><br>It looks like you are using:<br>");
        sb.append(userAgent);

        try {
           sleep(5000L);
        } catch (InterruptedException e) {
            throw new RuntimeException("StringWorker interrupted");
        }

        callback.onSuccess(new SendTextToServerResult(sb.toString()));
    }
}
