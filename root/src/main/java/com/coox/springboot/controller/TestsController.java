package com.coox.springboot.controller;

import com.coox.springboot.TopicRepository;
import com.coox.springboot.dto.HelloDto;
import com.coox.springboot.dto.MailSendRequest;
import com.coox.springboot.model.Topic;
import com.coox.springboot.service.CooxMailSender;
import com.coox.springboot.transaction.TransactionTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequiredArgsConstructor
public class TestsController {

    private final CooxMailSender cooxMailSender;
    private final TransactionTest transactionTest;
    private final TopicRepository topicRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/mail/send")
    public void sendTestMail(@RequestBody MailSendRequest request) {
        cooxMailSender.sendEmail(request);
    }

    @PostMapping(("/transaction/outer"))
    public Topic transactionTestExceptionInOuterMethod() {
        Topic topic = transactionTest.doCall1();
        entityManager.detach(topic);
        return topicRepository.findById(topic.getId()).get();
    }

    @PutMapping(("/transaction/update/{id}"))
    public Topic updateToNull(@PathVariable Long id) {
        Topic topic = transactionTest.doUpdate(id);
        entityManager.detach(topic);
        return topicRepository.findById(topic.getId()).get();
    }

    @GetMapping(("/transaction/inner"))
    public void transactionTestExceptionInMethod() {
        transactionTest.doCall2();
    }

    @GetMapping(("/transaction/template"))
    public void transactionTemplateTest() {
        transactionTest.doCallTransactionTemplate();
    }

    @GetMapping(("/transaction/async"))
    public void doAsyncRead() {
        transactionTest.doAsyncRead();
    }

    @GetMapping("/hello")
    public HelloDto sayHallo() {
        return new HelloDto("Hello");
    }

    @GetMapping(("/bug"))
    public void hiberBug() {
        transactionTest.hiberBug();
    }
}
