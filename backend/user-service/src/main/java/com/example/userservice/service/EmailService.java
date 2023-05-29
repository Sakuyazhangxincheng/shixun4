package com.example.userservice.service;

import com.example.userservice.util.VerCodeGenerateUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendEmailVerification(String email) throws MessagingException {
        // 验证码
        String verCode = VerCodeGenerateUtil.getVerCode();

        // 创建邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // ... 组装邮件内容
        helper.setSubject("【校园单车共享系统】 注册账号验证码");
        helper.setText("您好，您的【校园单车共享系统】注册账号验证码为：" + verCode + "。该验证码五分钟内过期，请尽快使用", true);  // 使用你自己的邮件内容
        helper.setTo(email);
        helper.setFrom("911124552@qq.com");

        // 发送邮件
        mailSender.send(mimeMessage);

        // 将验证码存入Redis，并设置过期时间为5分钟
        System.out.println("生成的验证码是：" + verCode);
        redisTemplate.opsForValue().set(email, verCode, 5, TimeUnit.MINUTES);
        System.out.println("存储到 Redis 的验证码是：" + redisTemplate.opsForValue().get(email));

    }
}

