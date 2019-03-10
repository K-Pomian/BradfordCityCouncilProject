package uk.co.bradford.iot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.bradford.iot.dao.IFloodDAO;
import uk.co.bradford.iot.model.Flood;

@Service
public class FloodService implements IFloodService {

    @Autowired
    private IFloodDAO floodDAO;

    @Override
    public List<Flood> getFloodBetweenDates(String fromDate, String toDate) {
        return floodDAO.getFloodBetweenDates(fromDate, toDate);
    }

    @Override
    public List<Flood> getAllFlood() {
        return floodDAO.getAllFlood();
    }

    /*@Override
    public synchronized boolean addFlood(Flood flood){
        if (floodDAO.floodExists(flood.getTitle(), flood.getCategory())) {
            return false;
        } else {
            floodDAO.addFlood(flood);
            return true;
        }
    }
    @Override
    public void updateFlood(Flood flood) {
        floodDAO.updateFlood(flood);
    }
    @Override
    public void deleteFlood(int floodId) {
        floodDAO.deleteFlood(floodId);
    }*/
} 