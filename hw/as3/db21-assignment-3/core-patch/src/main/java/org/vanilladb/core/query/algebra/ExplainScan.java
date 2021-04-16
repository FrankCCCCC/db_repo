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
package org.vanilladb.core.query.algebra;

import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.VarcharConstant;

/**
 * The scan class corresponding to the <em>project</em> relational algebra
 * operator. All methods except hasField delegate their work to the underlying
 * scan.
 */
public class ExplainScan implements Scan {
	// private Scan s;
	private int count;
	private String explain;
	private String fieldName;

	/**
	 * Creates a project scan having the specified underlying scan and field
	 * list.
	 * 
	 * @param s
	 *            the underlying scan
	 * @param fieldList
	 *            the list of field names
	 */
	public ExplainScan(String explain, String fieldName) {
		// this.s = s;
		this.count = 0;
		this.explain = explain;
		this.fieldName = fieldName;
	}

	@Override
	public void beforeFirst() {
		// s.beforeFirst();
		count = 0;
	}

	@Override
	public boolean next() {
		count++;
		return count > 1? false : true;
	}

	@Override
	public void close() {
		// s.close();
	}

	@Override
	public Constant getVal(String fldName) {
		if (hasField(fldName)){
			return new VarcharConstant(explain);
		}
		else{
			throw new RuntimeException("field " + fldName + " not found.");
		}
			
	}


	/**
	 * Returns true if the specified field is in the projection list.
	 * 
	 * @see Scan#hasField(java.lang.String)
	 */
	@Override
	public boolean hasField(String fldName) {
		return fieldName.equals(fldName);
	}
}
