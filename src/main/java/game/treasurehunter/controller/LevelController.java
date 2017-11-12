package game.treasurehunter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.treasurehunter.model.Level;
import game.treasurehunter.service.LevelService;


import game.treasurehunter.wrapper.FeatureWrapper;
import game.treasurehunter.wrapper.GeoJsonWrapper;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/levels")
public class LevelController {
    @Autowired
    private FeatureWrapper featureWrapper;

    @Autowired
    private GeoJsonWrapper jsonWrapper;

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @RequestMapping("/{levelId}")
    public String getLevel(@PathVariable Long levelId) throws IOException {
        Level level = levelService.findById(levelId);
        SimpleFeature areaFeature = featureWrapper.getAreaFeature(level);
        return jsonWrapper.wrapFeature(areaFeature);
    }



}
