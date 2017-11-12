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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tips")
public class TipsController {

    @Autowired
    private FeatureWrapper featureWrapper;

    @Autowired
    private GeoJsonWrapper jsonWrapper;

    private LevelService levelService;

    public TipsController(LevelService levelService) {
        this.levelService = levelService;
    }

    @RequestMapping("/{levelId}")
    public void getStartTip(@PathVariable Long levelId, HttpServletResponse response) throws IOException {
         response.sendRedirect("/tips/"+levelId+"/0");
    }

    @RequestMapping("/{levelId}/all")
    public String getAllTips(@PathVariable Long levelId) throws IOException {
        LevelData level = levelService.findById(levelId).getLevelData();
        List<SimpleFeature> features = new ArrayList<>();
        for(int i = 0;i<level.getTips().size();i++)
            features.add(featureWrapper.getTipFeature(level.getTips().get(i),i));
        return jsonWrapper.wrapFeatureCollection(features);
    }


    @RequestMapping("/{levelId}/{tipId}")
    public String getTipById(@PathVariable Long levelId, @PathVariable Integer tipId) throws IOException {
        LevelData level = levelService.findById(levelId).getLevelData();
        if (tipId < level.getTips().size()) {
            SimpleFeature feature = featureWrapper.getTipFeature(level.getTips().get(tipId), tipId);
            return jsonWrapper.wrapFeature(feature);
        }
        return null;
    }
}
