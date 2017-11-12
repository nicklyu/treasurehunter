package game.treasurehunter.controller;

import game.treasurehunter.model.LevelData;
import game.treasurehunter.service.LevelService;
import game.treasurehunter.wrapper.FeatureWrapper;
import game.treasurehunter.wrapper.GeoJsonWrapper;
import org.opengis.feature.simple.SimpleFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/treasure")
public class TreasureController {

    @Autowired
    private FeatureWrapper featureWrapper;

    @Autowired
    private GeoJsonWrapper jsonWrapper;

    private LevelService levelService;

    public TreasureController(LevelService levelService) {
        this.levelService = levelService;
    }

    @RequestMapping("/{levelId}")
    public String getTreasure(@PathVariable Long levelId) throws IOException {
        LevelData levelData = levelService.findById(levelId).getLevelData();
        SimpleFeature feature = featureWrapper.getTreasureFeature(levelData.getTreasureLocation());
        return jsonWrapper.wrapFeature(feature);
    }
}
