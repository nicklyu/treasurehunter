package game.treasurehunter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.treasurehunter.model.Level;
import game.treasurehunter.service.LevelService;


import game.treasurehunter.wrapper.GeoJsonWoloWrapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.wololo.geojson.GeoJSON;

@RestController
@RequestMapping("/levels")
public class LevelController {

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @RequestMapping("/{levelId}/area")
    public GeoJSON getLevelArea(@PathVariable Long levelId){
        Level targetLevel = levelService.findById(levelId);
        return GeoJsonWoloWrapper.GeometryWrapper(targetLevel.getLevelData().getArea());
    }

    @RequestMapping("/{levelId}/treasure")
    public GeoJSON getLevelTreasure(@PathVariable Long levelId){
        Level targetLevel = levelService.findById(levelId);
        return GeoJsonWoloWrapper.GeometryWrapper(targetLevel.getLevelData().getTreasureLocation());
    }

    @RequestMapping("/{levelId}/tips")
    public GeoJSON getTipPoints(@PathVariable Long levelId){
        Level targetLevel = levelService.findById(levelId);
        return GeoJsonWoloWrapper.GeometryWrapper(targetLevel.getLevelData().getTipsLocation());
    }

    @RequestMapping("/{levelId}/tips/{tip}")
    public GeoJSON getTipPoint(@PathVariable Long levelId, @PathVariable Integer tip){
        Level targetLevel = levelService.findById(levelId);
        return GeoJsonWoloWrapper.GeometryWrapper(targetLevel.getLevelData().getTipsLocation().getGeometryN(tip));
    }

}
