package org.vanilladb.bench.server.procedure.as2;

import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;
import org.vanilladb.bench.server.param.as2.As2UpdateItemPriceProcParamHelper;
import org.vanilladb.bench.server.procedure.StoredProcedureHelper;
import org.vanilladb.core.query.algebra.Scan;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

public class As2UpdateItemPriceTxnProc extends StoredProcedure<As2UpdateItemPriceProcParamHelper>{
    public As2UpdateItemPriceTxnProc() {
		super(new As2UpdateItemPriceProcParamHelper());
	}

	@Override
	protected void executeSql() {
		As2UpdateItemPriceProcParamHelper paramHelper = getParamHelper();
		Transaction tx = getTransaction();
		
		// SELECT
		for (int idx = 0; idx < paramHelper.getUpdateCount(); idx++) {
			int iid = paramHelper.getUpdateItemId(idx);
			double raisePrice = paramHelper.getUpdateItemRaisePrice(idx);

			Scan s = StoredProcedureHelper.executeQuery(
				"SELECT i_name, i_price FROM item WHERE i_id = " + iid,
				tx
			);
			s.beforeFirst();
			if (s.next()) {
				String name = (String) s.getVal("i_name").asJavaVal();
				double price = (Double) s.getVal("i_price").asJavaVal();

				if(price >= As2BenchConstants.MAX_PRICE){
					price = As2BenchConstants.MIN_PRICE;
				}else{
					price = price + raisePrice;
				}
				
				// Update
				StoredProcedureHelper.executeUpdate("UPDATE item SET i_price=" + price + " WHERE i_id="+iid, tx);

				paramHelper.setItemName(name, idx);
				paramHelper.setItemPrice(price, idx);
			} else
				throw new RuntimeException("Cloud not find item record with i_id = " + iid);

			s.close();
		}
	}
}
