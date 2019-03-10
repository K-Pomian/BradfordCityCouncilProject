package uk.co.bradford.iot.dao;

import java.util.List;

import uk.co.bradford.iot.model.Flood;

public interface IFloodDAO {
    List<Flood> getAllFlood();

    List<Flood> getFloodBetweenDates(String fromDate, String toDate);
    //void addFlood(Flood flood);
    //void updateFlood(Flood flood);
    //void deleteFlood();
    //boolean floodExists();
}
