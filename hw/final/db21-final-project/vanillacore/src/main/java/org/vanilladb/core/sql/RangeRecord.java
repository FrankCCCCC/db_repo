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
package org.vanilladb.core.sql;

/**
 * A record whose each field value is a {@link ConstantRange constant range}.
 */
public interface RangeRecord {
	/**
	 * Returns the {@link ConstantRange value range} of the specified field.
	 * 
	 * @param fldName
	 *            the name of the field
	 * @return the value range of that field
	 */
	ConstantRange getVal(String fldName);
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
package org.vanilladb.core.sql;

/**
 * A record whose each field value is a {@link ConstantRange constant range}.
 */
public interface RangeRecord {
	/**
	 * Returns the {@link ConstantRange value range} of the specified field.
	 * 
	 * @param fldName
	 *            the name of the field
	 * @return the value range of that field
	 */
	ConstantRange getVal(String fldName);
}
>>>>>>> d2c99998475a1754675654f3bd7ea496db923224
