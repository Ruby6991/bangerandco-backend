package com.apiit.bangerandco.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig
{
    @Bean
    public SimpleMailMessage emailTemplate()
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("rabiyaf1@gmail.com");
        message.setFrom("ruby.dev96@gmail.com");
        message.setSubject("Important email");
        message.setText("FATAL - Someone Just Got Blacklisted !!");
        return message;
    }
}
