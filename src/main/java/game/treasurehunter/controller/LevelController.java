package game.treasurehunter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import game.treasurehunter.service.LevelService;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LevelController {

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String seeLevel() throws JsonProcessingException {
        return "It works!";
    }
}
