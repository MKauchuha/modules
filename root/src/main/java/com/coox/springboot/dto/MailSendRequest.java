package com.coox.springboot.dto;

public record MailSendRequest(String recipient,
                              String body) {
}
