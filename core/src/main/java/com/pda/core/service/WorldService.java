package com.pda.core.service;


import com.pda.core.entity.World;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorldService {

//    private final WorldRepository worldRepository;

    public List<World> getNearWorlds() {

        // todo: 근처 계산하는 로직 Redis 연결 후 JPQL로 작성하기
//        List<World> worlds = worldRepository.findAll();
//
//        return worlds;

        return null;
    }
}
