package game.treasurehunter.service.impl;

import game.treasurehunter.model.Level;
import game.treasurehunter.repository.LevelRepository;
import game.treasurehunter.service.LevelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {

    private LevelRepository levelRepository;

    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Transactional
    @Override
    public List<Level> findAll() {
        return (List<Level>) levelRepository.findAll();
    }
}
