package org.elasql.bench.server.param.ycsb;

import java.util.Arrays;
import java.util.HashMap;

import org.vanilladb.bench.benchmarks.ycsb.YcsbConstants;
import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class ElasqlYcsbProcParamHelper extends StoredProcedureParamHelper {
	
	private int dbType;
	private int readCount;
	private int writeCount;
	private int insertCount;
	private Integer[] readTenantIds;
	private Integer[] writeTenantIds;
	private Integer[] insertTenantIds;
	private Integer[] readIds;
	private Integer[] writeIds;
	private Integer[] insertIds;
	private String[] writeVals;
	private String[] insertVals; // All fields use the same value to reduce transmission cost
	private String[] readVals;

	public int getReadCount() {
		return readCount;
	}

	public int getWriteCount() {
		return writeCount;
	}
	
	public int getInsertCount() {
		return insertCount;
	}
	
	public String getReadTableName(int index) {
		if (dbType == 2)
			return String.format("ycsb%d", readTenantIds[index]);
		return "ycsb";
	}
	
	public String getWriteTableName(int index) {
		if (dbType == 2)
			return String.format("ycsb%d", writeTenantIds[index]);
		return "ycsb";
	}
	
	public String getInsertTableName(int index) {
		if (dbType == 2)
			return String.format("ycsb%d", insertTenantIds[index]);
		return "ycsb";
	}
	
	public Integer getReadId(int index) {
		return readIds[index];
	}
	
	public Integer getWriteId(int index) {
		return writeIds[index];
	}
	
	public String getWriteValue(int index) {
		return writeVals[index];
	}
	
	public Integer getInsertId(int index) {
		return insertIds[index];
	}
	
	public void setReadVal(String s, int idx) {
		readVals[idx] = s;
	}
	
	public HashMap<String, Constant> getInsertVals(int index) {
		HashMap<String, Constant> fldVals = new HashMap<String, Constant>();
		String tableName = getInsertTableName(index);
		
		fldVals.put(tableName + "_id", new VarcharConstant(
				String.format(YcsbConstants.ID_FORMAT, insertIds[index])));
		for (int count = 1; count < YcsbConstants.FIELD_COUNT; count++)
			fldVals.put(tableName + "_" + count, new VarcharConstant(insertVals[index]));
		
		return fldVals;
	}

	@Override
	public void prepareParameters(Object... pars) {
		// Parameter format:
		// [dbType, read count, (read id array),
		// write count, (write id array), (write value array),
		// insert count, (insert id array), (insert value array)]
		// dbType = 1 (single table)
		// => id = [record id]
		// dbType = 2 (multi-tenants)
		// => id = [tenant id, record id]
		
		int indexCnt = 0;

		dbType = (Integer) pars[indexCnt++];
		
		readCount = (Integer) pars[indexCnt++];
		readTenantIds = new Integer[readCount];
		readIds = new Integer[readCount];
		readVals = new String[readCount];
		for (int i = 0; i < readCount; i++) {
			if (dbType == 2)
				readTenantIds[i] = (Integer) pars[indexCnt++];
			readIds[i] = (Integer) pars[indexCnt++];
		}

		writeCount = (Integer) pars[indexCnt++];
		writeTenantIds = new Integer[writeCount];
		writeIds = new Integer[writeCount];
		for (int i = 0; i < writeCount; i++) {
			if (dbType == 2)
				writeTenantIds[i] = (Integer) pars[indexCnt++];
			writeIds[i] = (Integer) pars[indexCnt++];
		}
		writeVals = new String[writeCount];
		for (int i = 0; i < writeCount; i++)
			writeVals[i] = (String) pars[indexCnt++];
		
		insertCount = (Integer) pars[indexCnt++];
		insertTenantIds = new Integer[insertCount];
		insertIds = new Integer[insertCount];
		for (int i = 0; i < insertCount; i++) {
			if (dbType == 2)
				insertTenantIds[i] = (Integer) pars[indexCnt++];
			insertIds[i] = (Integer) pars[indexCnt++];
		}
		insertVals = new String[insertCount];
		for (int i = 0; i < insertCount; i++)
			insertVals[i] = (String) pars[indexCnt++];

		if (writeCount == 0 && insertCount == 0)
			setReadOnly(true);
	}

	@Override
	public Schema getResultSetSchema() {
		Schema sch = new Schema();
		Type ycsb1Type = Type.VARCHAR(YcsbConstants.CHARS_PER_FIELD);
		for (int i = 0; i < readCount; i++)
			sch.addField("read_val_" + i, ycsb1Type);
		return sch;
	}

	@Override
	public SpResultRecord newResultSetRecord() {
		SpResultRecord rec = new SpResultRecord();
		Type ycsb1Type = Type.VARCHAR(YcsbConstants.CHARS_PER_FIELD);
		for (int i = 0; i < readCount; i++)
			rec.setVal("read_val_" + i, new VarcharConstant(readVals[i], ycsb1Type));
		return rec;
	}
}
