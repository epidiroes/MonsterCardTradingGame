package org.example.apps.mctg.service;

import org.example.apps.mctg.entity.Stat;
import org.example.apps.mctg.entity.User;
import org.example.apps.mctg.repository.StatsRepository;

import java.util.List;
import java.util.Optional;

public class StatsService {
    private final StatsRepository statsRepository;
    public StatsService(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public Stat read(User user) {
        return statsRepository.find(user);
    }

    public List<Stat> readAll() {
        return statsRepository.findAll();
    }
    public void createStats(User user) {
        statsRepository.create(user);
    }
}
