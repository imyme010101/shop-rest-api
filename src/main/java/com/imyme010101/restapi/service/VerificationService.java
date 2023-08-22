package com.imyme010101.restapi.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.imyme010101.restapi.common.response.ApiResponse;
import com.imyme010101.restapi.util.RedisUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:application.properties")
@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationService {
  @Autowired
  private final JavaMailSender javaMailSender;
  @Autowired
  private RedisUtil redisUtil;

  @Value("${spring.mail.username}")
  private String id;

  public MimeMessage createMessage(String to, String secretKey)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = javaMailSender.createMimeMessage();

    message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
    message.setSubject("ㅇㅇㅇ 회원가입 인증 코드: "); // 메일 제목

    // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
    String msg = "";
    msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
    msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 확인 코드를 회원가입 화면에서 입력해주세요.</p>";
    msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
    msg += secretKey;
    msg += "</td></tr></tbody></table></div>";

    message.setText(msg, "utf-8", "html"); // 내용, charset타입, subtype
    message.setFrom(new InternetAddress(id, "prac_Admin")); // 보내는 사람의 메일 주소, 보내는 사람 이름

    return message;
  }

  public static String createKey() {
    StringBuffer key = new StringBuffer();
    Random rnd = new Random();

    for (int i = 0; i < 6; i++) { // 인증코드 6자리
      key.append((rnd.nextInt(10)));
    }
    return key.toString();
  }

  public Long sendMail(String to) throws Exception {
    String secretKey = createKey();
    MimeMessage message = createMessage(to, secretKey);

    try {
      javaMailSender.send(message); // 메일 발송
      redisUtil.set(to, secretKey, 180);
    } catch (MailException es) {
      es.printStackTrace();
      throw new IllegalArgumentException();
    }

    return redisUtil.ttl(to);
  }

  public Integer check(String key, Integer code) {
    Long ttl = redisUtil.ttl(key);
    
    if (ttl <= 0) {
      return 202;
    } else {
      String secretKey = redisUtil.get(key).toString();
      
      if (secretKey.equals(code)) {
        return 200;
      } else {
        return 201;
      }
    }
  }
}