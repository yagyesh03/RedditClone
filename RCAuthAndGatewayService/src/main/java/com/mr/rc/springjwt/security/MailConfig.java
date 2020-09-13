package com.mr.rc.springjwt.security;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;



@Configuration 
public class MailConfig {
	
	@Autowired
    private Environment env;

    @Bean
    public JavaMailSender javaMailService() {
        
        	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
     
            mailSender.setHost(env.getProperty("spring.mail.host"));
            mailSender.setUsername(env.getProperty("spring.mail.username"));
            mailSender.setPassword(env.getProperty("spring.mail.password"));
     
            Properties javaMailProperties = new Properties();
            javaMailProperties.put("mail.smtp.ssl.enable", "true");
            javaMailProperties.put("mail.smtp.starttls.enable", "false");
            javaMailProperties.put("mail.smtp.auth", "true");
            javaMailProperties.put("mail.transport.protocol", "smtp");
            javaMailProperties.put("mail.debug", "true");
            javaMailProperties.put("mail.smtp.ssl.checkserveridentity", "true");
            javaMailProperties.put("mail.smtp.socketFactory.port", "465");

            mailSender.setJavaMailProperties(javaMailProperties);
        
        
        return mailSender;
    }


}
