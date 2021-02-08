package ru.funbox.controller;

import ru.funbox.model.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.funbox.model.Link;
import ru.funbox.repository.RedisRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class WebController {

    private RedisRepository redisRepository;

    @Autowired
    public WebController(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @PostMapping(path = "/visited_links")
    public ResponseEntity<String> add(HttpServletRequest request, @RequestBody Link links) {
        redisRepository.add(links);
        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(),HttpStatus.OK);
    }

    @GetMapping(path = "/visited_domains")
    public ResponseEntity<Domain> add(HttpServletRequest request, @RequestParam String from, @RequestParam String to) {
        Domain domains = redisRepository.getLinks(from, to);
        domains.setStatus(!domains.getDomains().isEmpty() ? HttpStatus.OK.getReasonPhrase() : "No domains between " + from + " and " + to);
        return new ResponseEntity<>(domains, HttpStatus.OK);
    }
}
