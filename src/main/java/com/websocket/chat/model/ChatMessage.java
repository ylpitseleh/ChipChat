package com.websocket.chat.model;

import lombok.Getter;
import lombok.Setter;
/*
채팅 메시지를 주고받기 위한 DTO를 하나 만듭니다.
상황에 따라 채팅방 입장, 채팅방에 메시지 보내기 두가지 상황이 있으므로 ENTER(채팅방 입장), TALK(대화하기)을  enum으로 선언합니다.
나머지 멤버 필드는 채팅방 구별 id, 메시지를 보낸 사람, 메시지로 구성합니다.
 */
@Getter
@Setter
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}