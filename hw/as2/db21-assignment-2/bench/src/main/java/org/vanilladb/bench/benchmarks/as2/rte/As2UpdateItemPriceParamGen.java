package org.vanilladb.bench.benchmarks.as2.rte;

import java.util.ArrayList;

import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.bench.rte.TxParamGenerator;
import org.vanilladb.bench.util.BenchProperties;
import org.vanilladb.bench.util.RandomValueGenerator;

public class As2UpdateItemPriceParamGen implements TxParamGenerator<As2BenchTransactionType>{
    // Update Counts
	private static final int TOTAL_UPDATE_COUNT;

	static {
		TOTAL_UPDATE_COUNT = BenchProperties.getLoader()
				.getPropertyAsInteger(As2UpdateItemPriceParamGen.class.getName() + ".TOTAL_UPDATE_COUNT", 10);
	}

	@Override
	public As2BenchTransactionType getTxnType() {
		return As2BenchTransactionType.UPDATE_ITEM;
	}

	@Override
	public Object[] generateParameter() {
		RandomValueGenerator id_rvg = new RandomValueGenerator();
		RandomValueGenerator price_rvg = new RandomValueGenerator();
		ArrayList<Object> paramList = new ArrayList<Object>();
		
		// Set read count
		paramList.add(TOTAL_UPDATE_COUNT);
		paramList.add(As2BenchConstants.MAX_PRICE);
		paramList.add(As2BenchConstants.MIN_PRICE);
		for (int i = 0; i < TOTAL_UPDATE_COUNT; i++){
			paramList.add(id_rvg.number(1, As2BenchConstants.NUM_ITEMS));
			paramList.add(price_rvg.randomDoubleIncrRange(0, 5, 1));
		}

		return paramList.toArray(new Object[0]);
	}
}
