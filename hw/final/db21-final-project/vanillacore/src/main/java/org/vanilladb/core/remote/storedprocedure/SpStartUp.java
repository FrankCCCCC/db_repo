<<<<<<< HEAD
/*******************************************************************************
 * Copyright 2016, 2017 vanilladb.org contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.vanilladb.core.remote.storedprocedure;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SpStartUp {
	/**
	 * Starts up the stored procedure call driver in server side by binding the
	 * remote driver object to local registry.
	 * 
	 * @param port
	 *            the network port for the server
	 * @throws Exception
	 *             if the registry could not be exported
	 */
	public static void startUp(int port) throws Exception {
		// create a registry specific for the server on the default port
		Registry reg = LocateRegistry.createRegistry(port);

		// and post the server entry in it
		RemoteDriver d = new RemoteDriverImpl();
		reg.rebind("vanilladb-sp", d);
	}
}
=======
/*******************************************************************************
 * Copyright 2016, 2017 vanilladb.org contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.vanilladb.core.remote.storedprocedure;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SpStartUp {
	/**
	 * Starts up the stored procedure call driver in server side by binding the
	 * remote driver object to local registry.
	 * 
	 * @param port
	 *            the network port for the server
	 * @throws Exception
	 *             if the registry could not be exported
	 */
	public static void startUp(int port) throws Exception {
		// create a registry specific for the server on the default port
		Registry reg = LocateRegistry.createRegistry(port);

		// and post the server entry in it
		RemoteDriver d = new RemoteDriverImpl();
		reg.rebind("vanilladb-sp", d);
	}
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
