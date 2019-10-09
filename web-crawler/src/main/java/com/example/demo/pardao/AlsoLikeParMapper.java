package com.example.demo.pardao;

import com.example.demo.model.AlsoLike;

public interface AlsoLikeParMapper {
    Integer selectMaxId();
    Integer insertAndReturnId(AlsoLike alsoLike);
}
