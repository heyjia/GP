package com.heihei.bookrecommendsystem.rabbitmq;

import com.heihei.bookrecommendsystem.util.EmailUtil;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MailRabbitMQConsumer {
    private static Logger logger = LoggerFactory.getLogger(MailRabbitMQConsumer.class);
    @RabbitListener(queues = RabbitMQConfig.MAIL_QUEUE_NAME)
    public void consumerMailMsg(String message){
        logger.info("接收到的消息：" + message);
        String [] str = message.split(":");
        if (str.length <= 1) {
            logger.error("消息错误" + message);
            return;
        }
        String mail = str[0];
        String code = str[1];
        try {
            EmailUtil.sendCheckCode(mail,code);
        } catch (EmailException e) {
            logger.error("发送邮件错误错误" + message);
            e.printStackTrace();
        }
        logger.info("发送邮件成功" + "邮箱：" + mail + "验证码" + code);
    }
}
