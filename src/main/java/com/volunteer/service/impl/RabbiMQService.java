package com.volunteer.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author VernHe
 * @date 2021年12月30日 13:19
 */
@Service
public class RabbiMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试发送消息，如果发送成功，则表示配置没有问题
     */
    public void testSendMsg() {

        rabbitTemplate.convertAndSend("rabbitmq_direct", "", "这是一条用java发的消息");
    }
}
