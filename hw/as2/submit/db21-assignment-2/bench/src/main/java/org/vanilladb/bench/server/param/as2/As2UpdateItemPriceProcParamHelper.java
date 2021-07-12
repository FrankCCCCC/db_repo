package org.vanilladb.bench.server.param.as2;

import org.vanilladb.core.sql.DoubleConstant;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureParamHelper;

public class As2UpdateItemPriceProcParamHelper extends StoredProcedureParamHelper {
    private int updateCount;
	private double maxPrice;
	private double minPrice;
	private int[] updateItemId;
	private String[] itemName;
	private double[] itemPrice;
	private double[] raisePrice;

	public int getUpdateCount() {
		return updateCount;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public int getUpdateItemId(int index) {
		return updateItemId[index];
	}

	public double getUpdateItemRaisePrice(int index) {
		return raisePrice[index];
	}

	public void setItemName(String s, int idx) {
		itemName[idx] = s;
	}

	public void setItemPrice(double d, int idx) {
		itemPrice[idx] = d;
	}

	@Override
	public void prepareParameters(Object... pars) {

		// Show the contents of paramters
	   //System.out.println("Params: " + Arrays.toString(pars));

		int indexCnt = 0;

		updateCount = (Integer) pars[indexCnt++];
		maxPrice = (Double) pars[indexCnt++];
		minPrice = (Double) pars[indexCnt++];
		updateItemId = new int[updateCount];
		itemName = new String[updateCount];
		itemPrice = new double[updateCount];
		raisePrice = new double[updateCount];

		for (int i = 0; i < updateCount; i++){
			updateItemId[i] = (Integer) pars[indexCnt++];
			raisePrice[i] = (Double) pars[indexCnt++];
		}
	}

	@Override
	public Schema getResultSetSchema() {
		Schema sch = new Schema();
		Type intType = Type.INTEGER;
		Type itemPriceType = Type.DOUBLE;
		Type itemNameType = Type.VARCHAR(24);
		sch.addField("rc", intType);
		for (int i = 0; i < itemName.length; i++) {
			sch.addField("i_name_" + i, itemNameType);
			sch.addField("i_price_" + i, itemPriceType);
		}
		return sch;
	}

	@Override
	public SpResultRecord newResultSetRecord() {
		SpResultRecord rec = new SpResultRecord();
		rec.setVal("rc", new IntegerConstant(itemName.length));
		for (int i = 0; i < itemName.length; i++) {
			rec.setVal("i_name_" + i, new VarcharConstant(itemName[i], Type.VARCHAR(24)));
			rec.setVal("i_price_" + i, new DoubleConstant(itemPrice[i]));
		}
		return rec;
	}
}
