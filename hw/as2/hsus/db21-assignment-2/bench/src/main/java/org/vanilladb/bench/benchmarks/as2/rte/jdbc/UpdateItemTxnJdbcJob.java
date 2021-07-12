package org.vanilladb.bench.benchmarks.as2.rte.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.bench.remote.SutResultSet;
import org.vanilladb.bench.remote.jdbc.VanillaDbJdbcResultSet;
import org.vanilladb.bench.rte.jdbc.JdbcJob;
import org.vanilladb.bench.server.param.as2.As2UpdateItemPriceProcParamHelper;

public class UpdateItemTxnJdbcJob implements JdbcJob {
    private static Logger logger = Logger.getLogger(UpdateItemTxnJdbcJob.class
            .getName());

    @Override
    public SutResultSet execute(Connection conn, Object[] pars) throws SQLException {
    	As2UpdateItemPriceProcParamHelper paramHelper = new As2UpdateItemPriceProcParamHelper();
        paramHelper.prepareParameters(pars);

        // Output message
        StringBuilder outputMsg = new StringBuilder("[");

        // Execute logic
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = null;
            int sueess_count = 0;

            for (int i = 0; i < paramHelper.getUpdateCount(); i++) {
                int iid = paramHelper.getUpdateItemId(i);
                double i_raise = paramHelper.getUpdateItemRaisePrice(i);
                double MAX_PRICE = paramHelper.getMaxPrice();
                double MIN_PRICE = paramHelper.getMinPrice();
                double i_price;
                // SELECT
                String sql = "SELECT i_name, i_price FROM item WHERE i_id = " + iid;
                rs = statement.executeQuery(sql);
                rs.beforeFirst();
                if (rs.next()) {
                    i_price = rs.getDouble("i_price");
                    //outputMsg.append(String.format("'%s', ", rs.getString("i_name")));
                } else
                    throw new RuntimeException("cannot find the record with i_id = " + iid);
                rs.close();

                //UPDATE
                double adjust_value;
                if(i_price >= MAX_PRICE)
                    adjust_value = MIN_PRICE;
                else
                    adjust_value = i_price + i_raise;
                sql = "UPDATE item SET i_price = "+ adjust_value + " WHERE i_id = "+ iid;
                sueess_count += statement.executeUpdate(sql);
                outputMsg.append(String.format("'%d'", sueess_count));
            }

            conn.commit();

            //outputMsg.deleteCharAt(outputMsg.length() - 2);
            outputMsg.append("]");

            return new VanillaDbJdbcResultSet(true, outputMsg.toString());
        } catch (Exception e) {
            if (logger.isLoggable(Level.WARNING))
                logger.warning(e.toString());
            return new VanillaDbJdbcResultSet(false, "");
        }
    }
}