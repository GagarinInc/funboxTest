package ru.funbox.repository;

import ru.funbox.model.Domain;
import ru.funbox.model.Link;

public interface RedisRepository {
    int add(Link link);
    Domain getLinks(String from, String to);
}
