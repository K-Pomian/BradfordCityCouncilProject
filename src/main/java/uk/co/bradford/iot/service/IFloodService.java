package uk.co.bradford.iot.service;

import java.util.List;

import uk.co.bradford.iot.model.Flood;

public interface IFloodService {
    List<Flood> getAllFlood();

    List<Flood> getFloodBetweenDates(String fromDate, String toDate);
    /*boolean addFlood(Flood flood);
    void updateFlood(Flood flood);
    void deleteFlood( some kind of id );*/
}
