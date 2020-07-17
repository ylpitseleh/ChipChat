package com.websocket.chat.pubsub;

import com.websocket.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
/*
Redis에서는 공통 주제(Topic)에 대하여 구독자(subscriber)에게 메시지를 발행(publish)할 수 있는 기능이 있습니다.
통칭하여 pub/sub라고 하며 채팅방에서는 redis의 topic을 채팅방이라고 가정하고, pub/sub는 대화를 하거나/대화를 보는 행위라고 생각하면 됩니다.
Spring에서는 redis topic에 대하여 구독 및 발행을 처리할 수 있도록 다음과 같이 방법을 제공하고 있습니다.

채팅방에 입장하여 메시지를 작성하면 해당 메시지를 Redis Topic에 발행하는 기능의 서비스입니다.
이 서비스를 통해 메시지를 발행하면 대기하고 있던 redis 구독 서비스가 메시지를 처리합니다.
 */
@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}