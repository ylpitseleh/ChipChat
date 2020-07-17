package com.websocket.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;/*
2) handler를 이용하여 Websocket을 활성화하기 위한 Config 파일을 작성합니다.
@EnableWebSocket을 선언하여 Websocket을 활성화합니다.
Websocket에 접속하기 위한 endpoint는 /ws/chat으로 설정하고 도메인이 다른 서버에서도 접속 가능하도록 CORS : setAllowedOrigins(“*”)를 설정을 추가해 줍니다.
이제 클라이언트가 ws://localhost:8080/ws/chat으로 커넥션을 연결하고 메시지 통신을 할 수 있는 기본적인 준비가 끝났습니다.
 */
/*
Stomp를 사용하기 위해 @EnableWebSocketMessageBroker을 선언하고 WebSocketMessageBrokerConfigurer를 상속받아 configureMessageBroker를 구현합니다.
pub/sub 메시징을 구현하기 위해 메시지를 발행하는 요청의 prefix는 /pub로 시작하도록 설정하고 메시지를 구독하는 요청의 prefix는 /sub로 시작하도록 설정합니다.
그리고 stomp websocket의 연결 endpoint는 /ws-stomp로 설정합니다.
따라서 개발서버의 접속 주소는 다음과 같이 됩니다.
ws://localhost:8080/ws-stomp
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
                .withSockJS();
    }
}