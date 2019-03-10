package uk.co.bradford.iot.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FloodRowMapper implements RowMapper<Flood> {
    @Override
    public Flood mapRow(ResultSet row, int rowNum) throws SQLException {
        Flood flood = new Flood();
        flood.setDate(row.getString("date"));
        //flood.setTime(row.getString("time"));
        flood.setValue(row.getDouble("value"));
        return flood;
    }
}
