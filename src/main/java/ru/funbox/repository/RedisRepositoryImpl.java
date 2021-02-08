package ru.funbox.repository;

import ru.funbox.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import ru.funbox.model.Link;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RedisRepositoryImpl implements RedisRepository {
    private SetOperations<String, String> setOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.setOperations = redisTemplate.opsForSet();
    }

    @Override
    public int add(Link links) {
        int addedCount = 0;
        String creationTime = String.valueOf(new Date().getTime());
        for (String s : links.getLinks()) {
            addedCount += setOperations.add(creationTime, s);
        }
        return addedCount;
    }

    @Override
    public Domain getLinks(String from, String to) {
        Set<String> times = new HashSet<>();
        for (Long i = Long.parseLong(from) + 1; i <= Long.parseLong(to); i++) {
            times.add(String.valueOf(i));
        }
        Domain domain = new Domain();
        domain.setDomains(setOperations.union(from, times));
        return domain;
    }

}
