package game.treasurehunter.service;

import game.treasurehunter.model.Level;

import java.util.List;

public interface LevelService {
    List<Level> findAll();
    Level findById(Long id);
}
