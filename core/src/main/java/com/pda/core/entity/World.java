package com.pda.core.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class World {

    private  Long id;
    private  Double latitude;
    private  Double longitude;
    private  Long stockmonId;
    private  Boolean isCaught;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

}
