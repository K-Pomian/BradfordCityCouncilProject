package uk.co.bradford.iot.controller;

import uk.co.bradford.iot.model.Flood;
import uk.co.bradford.iot.service.IFloodService;
import be.ceau.chart.LineChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.LineData;
import be.ceau.chart.dataset.LineDataset;
import be.ceau.chart.options.elements.Fill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/chart")
public class ChartController {

    private IFloodService floodService;

    @Autowired
    public ChartController(IFloodService floodService) {
        this.floodService = floodService;
    }

    private static final int seed = 15;
    private static final Random rng = new Random(seed);

    @GetMapping("/chart")
    public String chart(@RequestParam String test) {
        List<Flood> flood = floodService.getFloodBetweenDates("2017-02-17", "2017-03-17");

        LineDataset dataset = new LineDataset()
                .setLabel("Flood")
                .setBorderColor(colorGenerator())
                .setFill(new Fill(false));

        for (Flood singleFlood : flood) {
            dataset.addData(singleFlood.getValue());
        }

        LineData data = new LineData()
                .addDataset(dataset);

        for (Flood singleFlood : flood) {
            data.addLabel(singleFlood.getDate());
        }

        return new LineChart(data).toJson();
    }

    private Color colorGenerator() {
        int r = rng.nextInt(255);
        int g = rng.nextInt(255);
        int b = rng.nextInt(255);

        return new Color(r, g, b);
    }

}
