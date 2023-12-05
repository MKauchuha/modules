package com.coox.springboot.controller;

import com.coox.springboot.dto.HelloDto;
import com.coox.springboot.dto.MailSendRequest;
import com.coox.springboot.service.CooxMailSender;
import com.coox.springboot.transaction.TransactionTest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestsController {

    private final CooxMailSender cooxMailSender;
    private final TransactionTest transactionTest;

    @PostMapping("/mail/send")
    public void sendTestMail(@RequestBody MailSendRequest request) {
        cooxMailSender.sendEmail(request);
    }

    @GetMapping(("/transaction/outer"))
    public void transactionTestExceptionInOuterMethod() {
        transactionTest.doCall1();
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
