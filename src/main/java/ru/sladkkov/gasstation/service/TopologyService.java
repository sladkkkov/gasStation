package ru.sladkkov.gasstation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sladkkov.gasstation.model.Topology;
import ru.sladkkov.gasstation.repository.TopologyRepository;

import java.util.List;

@Service
public class TopologyService {

    private final TopologyRepository topologyRepository;

    @Autowired
    public TopologyService(TopologyRepository topologyRepository) {
        this.topologyRepository = topologyRepository;
    }

    public List<Topology> findAll() {
        return topologyRepository.findAll();
    }
}
