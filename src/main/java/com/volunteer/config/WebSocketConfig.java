package com.volunteer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author: 梁峰源
 * @date: 2022年2月1日18:37:21
 * Description: WebSocket配置，用来和爱心雨伞终端通信
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");//这是一个主题，是用来给客户端订阅使用的
        config.setApplicationDestinationPrefixes("/app");//目的地点的前缀，这是客户端发往送信息往服务器的地址
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();//终端名称，也即服务器端名称
    }

}

