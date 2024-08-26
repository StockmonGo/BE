package com.pda.core.repository;


import com.pda.core.entity.World;
import java.time.LocalDateTime;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class WorldRepository {

    private final List<World> worlds = new ArrayList<>();

    public WorldRepository() {
        worlds.add(new World(1,37.54472439904196, 127.05704251535104, 1, false, LocalDateTime.now(),  LocalDateTime.now()));
    }

    public List<World> findAll() {
        return worlds;
    }

}
