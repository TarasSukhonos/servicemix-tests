/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicemix.examples.drools.camel.blueprint.osgi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.servicemix.examples.drools.camel.blueprint.model.Customer;
import org.kie.api.command.Command;
import org.kie.internal.command.CommandFactory;



public class Utils {

    private static final Random rand = new Random(12345);

    public Customer customer() {
        return new Customer(rand.nextInt(9999));
    }


    public void insertAndFireAll(Exchange exchange) {
        final Message in = exchange.getIn();
        final Object body = in.getBody();

        final List<Command<?>> commands = new ArrayList<Command<?>>(2);
        commands.add(CommandFactory.newInsert(body));
        commands.add(CommandFactory.newFireAllRules());

        Command<?> batch = CommandFactory.newBatchExecution(commands);
        in.setBody(batch);
    }

}
