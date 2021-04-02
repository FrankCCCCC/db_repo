/*******************************************************************************
 * Copyright 2016, 2018 elasql.org contributors
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
package org.elasql.bench.server.procedure.calvin.tpcc;

import java.util.HashMap;
import java.util.Map;

import org.elasql.bench.benchmarks.tpcc.ElasqlTpccBenchmark;
import org.elasql.cache.CachedRecord;
import org.elasql.procedure.calvin.CalvinStoredProcedure;
import org.elasql.schedule.calvin.ReadWriteSetAnalyzer;
import org.elasql.sql.RecordKey;
import org.elasql.sql.RecordKeyBuilder;
import org.vanilladb.bench.benchmarks.tpcc.TpccConstants;
import org.vanilladb.bench.server.param.tpcc.PaymentProcParamHelper;
import org.vanilladb.core.sql.BigIntConstant;
import org.vanilladb.core.sql.Constant;
import org.vanilladb.core.sql.DoubleConstant;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.VarcharConstant;


public class PaymentProc extends CalvinStoredProcedure<PaymentProcParamHelper> {

	// XXX: hard code the history id
	// TODO: This should be another way to solve the problem
	private static int[][][] historyIds;
	static {
		int warehouseCount = ElasqlTpccBenchmark.getNumOfWarehouses();
		historyIds = new int[warehouseCount][TpccConstants.DISTRICTS_PER_WAREHOUSE][TpccConstants.CUSTOMERS_PER_DISTRICT];
		for (int i = 0; i < warehouseCount; i++)
			for (int j = 0; j < TpccConstants.DISTRICTS_PER_WAREHOUSE; j++)
				for (int k = 0; k < TpccConstants.CUSTOMERS_PER_DISTRICT; k++)
					historyIds[i][j][k] = 2;
	}

	/**
	 * This method should be accessed by the thread of the scheduler.
	 * 
	 * @param wid
	 * @param did
	 * @return
	 */
	public static int getNextHistoryId(int wid, int did, int cid) {
		return historyIds[wid - 1][did - 1][cid - 1];
	}

	public PaymentProc(long txNum) {
		super(txNum, new PaymentProcParamHelper());

	}

	private RecordKey warehouseKey, districtKey, customerKey;
	private RecordKey historyKey;
	// SQL Constants
	Constant widCon, didCon, cwidCon, cdidCon, cidCon, hidCon;
	private double Hamount;

	@Override
	protected void prepareKeys(ReadWriteSetAnalyzer analyzer) {
		RecordKeyBuilder builder;
		
		// XXX: hard code the history id
		int cwid = paramHelper.getCwid();
		int cdid = paramHelper.getCdid();
		int cid = paramHelper.getcid();
		int hid = historyIds[cwid - 1][cdid - 1][cid - 1];
		historyIds[cwid - 1][cdid - 1][cid - 1] = hid + 1;
		
		widCon = new IntegerConstant(paramHelper.getWid());
		didCon = new IntegerConstant(paramHelper.getDid());
		cwidCon = new IntegerConstant(cwid);
		cdidCon = new IntegerConstant(cdid);
		cidCon = new IntegerConstant(cid);
		hidCon = new IntegerConstant(hid);
		Hamount = paramHelper.getHamount();

		// SELECT ... FROM warehouse WHERE w_id = wid
		builder = new RecordKeyBuilder("warehouse");
		builder.addFldVal("w_id", widCon);
		warehouseKey = builder.build();
		analyzer.addReadKey(warehouseKey);
		// UPDATE ... FROM warehouse WHERE w_id = wid
		analyzer.addUpdateKey(warehouseKey);

		// SELECT ... FROM district WHERE d_w_id = wid AND d_id = did
		builder = new RecordKeyBuilder("district");
		builder.addFldVal("d_w_id", widCon);
		builder.addFldVal("d_id", didCon);
		districtKey = builder.build();
		analyzer.addReadKey(districtKey);

		// UPDATE ... WHERE d_w_id = wid AND d_id = did
		analyzer.addUpdateKey(districtKey);

		// SELECT ... FROM customer WHERE c_w_id = cwid AND c_d_id = cdid
		// AND c_id = cidInt
		builder = new RecordKeyBuilder("customer");
		builder.addFldVal("c_w_id", cwidCon);
		builder.addFldVal("c_d_id", cdidCon);
		builder.addFldVal("c_id", cidCon);
		customerKey = builder.build();
		analyzer.addReadKey(customerKey);

		// UPDATE ... FROM customer WHERE c_w_id = cwid AND c_d_id = cdid
		// AND c_id = cidInt
		analyzer.addUpdateKey(customerKey);

		// INSERT INTO history INSERT INTO history h_id, h_c_id, h_c_d_id,
		// h_c_w_id, h_d_id, h_w_id
		builder = new RecordKeyBuilder("history");
		builder.addFldVal("h_id", hidCon);
		builder.addFldVal("h_c_id", cidCon);
		builder.addFldVal("h_c_d_id", cdidCon);
		builder.addFldVal("h_c_w_id", cwidCon);
		historyKey = builder.build();
		analyzer.addInsertKey(historyKey);
	}

	@Override
	protected void executeSql(Map<RecordKey, CachedRecord> readings) {
		CachedRecord rec = null;
		double wYtd;
		String wName;
		// SELECT w_name, w_street_1, w_street_2, w_city,w_state, w_zip, w_ytd
		// FROM warehouse WHERE w_id =" + wid;

		rec = readings.get(warehouseKey);

		wName = (String) rec.getVal("w_name").asJavaVal();
		rec.getVal("w_street_1").asJavaVal();
		rec.getVal("w_street_2").asJavaVal();
		rec.getVal("w_city").asJavaVal();
		rec.getVal("w_state").asJavaVal();
		rec.getVal("w_zip").asJavaVal();

		wYtd = (Double) rec.getVal("w_ytd").asJavaVal();

		// UPDATE warehouse SET w_ytd = DoublePlainPrinter.toPlainString(wYtd +
		// hAmount) WHERE w_id = + wid;
		rec.setVal("w_ytd", new DoubleConstant(wYtd + Hamount));
		update(warehouseKey, rec);

		double dYtd;
		String dName;
		// SELECT d_name, d_street_1, d_street_2, d_city, d_state, d_zip,d_ytd
		// FROM district WHERE d_w_id = " + wid + " AND d_id = did;
		rec = readings.get(districtKey);

		dName = (String) rec.getVal("d_name").asJavaVal();
		rec.getVal("d_street_1").asJavaVal();
		rec.getVal("d_street_2").asJavaVal();
		rec.getVal("d_city").asJavaVal();
		rec.getVal("d_state").asJavaVal();
		rec.getVal("d_zip").asJavaVal();
		rec.getVal("d_ytd").asJavaVal();

		dYtd = (Double) rec.getVal("d_ytd").asJavaVal();

		// UPDATE district SET d_ytd = DoublePlainPrinter.toPlainString(dYtd +
		// hAmount) WHERE d_w_id =" + wid + " AND d_id = " + did;
		rec.setVal("d_ytd", new DoubleConstant(dYtd + Hamount));
		update(districtKey, rec);

		int cPaymentCnt;
		double cYtdPayment;
		// "SELECT c_first, c_middle, c_last, c_street_1, c_street_2, c_city,
		// c_state, c_zip, c_phone, c_since, c_credit, c_credit_lim,
		// "c_discount, c_balance, c_ytd_payment, c_payment_cnt FROM "customer
		// WHERE c_w_id = cwid AND c_d_id = cdid AND c_id = cidInt;
		rec = readings.get(customerKey);

		paramHelper.setcFirst((String) rec.getVal("c_first").asJavaVal());
		if (rec.getVal("c_first") == null)
			System.out.println(rec.toString());
		paramHelper.setcMiddle((String) rec.getVal("c_middle").asJavaVal());
		paramHelper.setcLast((String) rec.getVal("c_last").asJavaVal());
		paramHelper.setcStreet1((String) rec.getVal("c_street_1").asJavaVal());
		paramHelper.setcStreet2((String) rec.getVal("c_street_2").asJavaVal());
		paramHelper.setcCity((String) rec.getVal("c_city").asJavaVal());
		paramHelper.setcState((String) rec.getVal("c_state").asJavaVal());
		paramHelper.setcZip((String) rec.getVal("c_zip").asJavaVal());
		paramHelper.setcPhone((String) rec.getVal("c_phone").asJavaVal());
		paramHelper.setcCredit((String) rec.getVal("c_credit").asJavaVal());
		paramHelper.setcSince((long) rec.getVal("c_since").asJavaVal());
		paramHelper.setcBalance((double) rec.getVal("c_balance").asJavaVal());
		paramHelper.setcCreditLim((double) rec.getVal("c_credit_lim").asJavaVal());
		paramHelper.setcDiscount((double) rec.getVal("c_discount").asJavaVal());

		cYtdPayment = (Double) rec.getVal("c_ytd_payment").asJavaVal();
		cPaymentCnt = (Integer) rec.getVal("c_payment_cnt").asJavaVal();
		double cBalanceDouble = (Double) rec.getVal("c_discount").asJavaVal();
		// UPDATE customer SET c_balance =
		// DoublePlainPrinter.toPlainString(cBalanceDouble - hAmount),
		// c_ytd_payment = DoublePlainPrinter.toPlainString(cYtdPayment +
		// hAmount), c_payment_cnt = " + (cPaymentCnt + 1) + " WHERE c_w_id ="
		// cwid + " AND c_d_id = " + cdid + " AND c_id = " + cidInt;
		rec.setVal("c_balance", new DoubleConstant(cBalanceDouble - Hamount));
		rec.setVal("c_ytd_payment", new DoubleConstant(cYtdPayment + Hamount));
		rec.setVal("c_payment_cnt", new IntegerConstant(cPaymentCnt + 1));
		update(customerKey, rec);

		String cCreditStr = (String) rec.getVal("c_credit").asJavaVal();
		if (cCreditStr.equals("BC")) {
			paramHelper.setisBadCredit(true);
			// "SELECT c_data FROM customer WHERE c_w_id = " + cwid+ " AND
			// c_d_id = " + cdid + " AND c_id = " + cidInt;

			String cDataStr = (String) rec.getVal("c_data").asJavaVal();
			cDataStr = paramHelper.getcid() + " " + paramHelper.getCdid() + " " + paramHelper.getCwid() + " "
					+ paramHelper.getDid() + " " + paramHelper.getWid() + " " + Hamount + " " + cDataStr;
			if (cDataStr.length() > 500)
				cDataStr = cDataStr.substring(0, 499);
			// UPDATE customer SET c_data = 'cDataStr' WHERE c_w_id = + cwid +
			// AND c_d_id = + cdid AND c_id = + cidInt;
			rec.setVal("c_data", new VarcharConstant(cDataStr));
			if (cDataStr.length() > 200)
				cDataStr = cDataStr.substring(0, 199);
			paramHelper.setcData(cDataStr);
		}

		// INSERT INTO history (h_c_id, h_c_d_id, h_c_w_id, h_d_id, h_w_id,
		// h_date, h_amount, h_data) VALUES ( cidInt , cdid , cwid
		// , did , wid , hDateLong ,DoublePlainPrinter.toPlainString(hAmount),
		// hData );

		String hData = wName + " " + dName;
		long hDateLong = System.currentTimeMillis();
		paramHelper.sethDate(hDateLong);

		Map<String, Constant> fldVals = null;
		fldVals = new HashMap<String, Constant>();
		fldVals.put("h_id", hidCon);
		fldVals.put("h_c_id", cidCon);
		fldVals.put("h_c_d_id", cdidCon);
		fldVals.put("h_c_w_id", cwidCon);
		fldVals.put("h_d_id", didCon);
		fldVals.put("h_w_id", widCon);
		fldVals.put("h_date", new BigIntConstant(hDateLong));
		fldVals.put("h_amount", new DoubleConstant(Hamount));
		fldVals.put("h_data", new VarcharConstant(hData));
		insert(historyKey, fldVals);

	}

}
