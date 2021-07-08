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
package org.vanilladb.core.storage.file.io;

import java.io.IOException;

public interface IoChannel {
	
	int read(IoBuffer buffer, long position) throws IOException;
	
	int write(IoBuffer buffer, long position) throws IOException;
	
	long append(IoBuffer buffer) throws IOException;
	
	long size() throws IOException;
	
	void close() throws IOException;
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
package org.vanilladb.core.storage.file.io;

import java.io.IOException;

public interface IoChannel {
	
	int read(IoBuffer buffer, long position) throws IOException;
	
	int write(IoBuffer buffer, long position) throws IOException;
	
	long append(IoBuffer buffer) throws IOException;
	
	long size() throws IOException;
	
	void close() throws IOException;
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
