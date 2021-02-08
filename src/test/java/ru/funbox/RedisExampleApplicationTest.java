package ru.funbox;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.funbox.model.Link;
import ru.funbox.repository.RedisRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisExampleApplicationTest {
    private final List<String> list = new ArrayList<>();
    private final Link links = new Link();

    @Autowired
    private RedisRepository redisRepository;

    @Before
    public void init() {
        list.add("domain1");
        list.add("domain2");
        list.add("domain3");
        links.setLinks(list);
    }

    @Test
    public void addLinks() {
        int createdLinks = redisRepository.add(links);
        assertEquals(createdLinks, links.getLinks().size());
    }
}
