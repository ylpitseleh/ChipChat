package com.websocket.chat.controller;

import com.websocket.chat.model.ChatMessage;
import com.websocket.chat.pubsub.RedisPublisher;
import com.websocket.chat.repo.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
/*
채팅방의 생성 및 조회는 Rest api로 구현할 것이므로 아래와 같이 Controller를 생성하여 내용을 작성합니다.
 */
/*
@MessageMapping을 통해 Websocket으로 들어오는 메시지 발행을 처리합니다.
클라이언트에서는 prefix를 붙여서 /pub/chat/message로 발행 요청을 하면 Controller가 해당 메시지를 받아 처리합니다. 메시지가 발행되면 /sub/chat/room/{roomId}로 메시지를 send 하는 것을 볼 수 있는데 클라이언트에서는 해당 주소를(/sub/chat/room/{roomId}) 구독(subscribe)하고 있다가 메시지가 전달되면 화면에 출력하면 됩니다. 여기서 /sub/chat/room/{roomId}는 채팅룸을 구분하는 값이므로 pub/sub에서 Topic의 역할이라고 보면 됩니다.
기존의 WebSockChatHandler가 했던 역할을 대체하므로 WebSockChatHandler는 삭제합니다.
 */
/*
클라이언트가 채팅방 입장시 채팅방(topic)에서 대화가 가능하도록 리스너를 연동하는 enterChatRoom 메서드를 세팅합니다.
채팅방에 발행된 메시지는 서로 다른 서버에 공유하기 위해 redis의 Topic으로 발행합니다.
 */
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}