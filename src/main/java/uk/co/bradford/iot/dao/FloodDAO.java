package uk.co.bradford.iot.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.bradford.iot.model.Flood;
import uk.co.bradford.iot.model.FloodRowMapper;

@Transactional
@Repository
public class FloodDAO implements IFloodDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Flood> getFloodBetweenDates(String fromDate, String toDate) {
        String sql = "SELECT date, avg(value) AS 'value' FROM flood WHERE DATE BETWEEN ? AND ? GROUP BY DATE";
        RowMapper<Flood> rowMapper = new BeanPropertyRowMapper<Flood>(Flood.class);
        List<Flood> flood = jdbcTemplate.query(sql, rowMapper, fromDate, toDate);
        return flood;
    }

    @Override
    public List<Flood> getAllFlood() {
        String sql = "SELECT date, AVG(value) AS value FROM flood GROUP BY date";
        //RowMapper<Flood> rowMapper = new BeanPropertyRowMapper<Flood>(Flood.class);
        RowMapper<Flood> rowMapper = new FloodRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }
    /*@Override
    public void addFlood(Flood flood) {
        //Add flood
        String sql = "INSERT INTO floods (floodId, title, category) values (?, ?, ?)";
        jdbcTemplate.update(sql, flood.getFloodId(), flood.getTitle(), flood.getCategory());

        //Fetch flood id
        sql = "SELECT floodId FROM floods WHERE title = ? and category=?";
        int floodId = jdbcTemplate.queryForObject(sql, Integer.class, flood.getTitle(), flood.getCategory());

        //Set flood id 
        flood.setFloodId(floodId);
    }
    @Override
    public void updateFlood(Flood flood) {
        String sql = "UPDATE floods SET title=?, category=? WHERE floodId=?";
        jdbcTemplate.update(sql, flood.getTitle(), flood.getCategory(), flood.getFloodId());
    }
    @Override
    public void deleteFlood(int floodId) {
        String sql = "DELETE FROM floods WHERE floodId=?";
        jdbcTemplate.update(sql, floodId);
    }
    @Override
    public boolean floodExists(String title, String category) {
        String sql = "SELECT count(*) FROM floods WHERE title = ? and category=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, title, category);
        if(count == 0) {
            return false;
        } else {
            return true;
        }
    }*/
}
