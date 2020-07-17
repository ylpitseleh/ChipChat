package com.websocket.chat.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;
/*
채팅방을 구현하기 위해 DTO를 하나 만듭니다.
채팅방은 입장한 클라이언트들의 정보를 가지고 있어야 하므로 WebsocketSession 정보 리스트를 멤버 필드로 갖습니다.
나머지 멤버 필드로 채팅방 id, 채팅방 이름을 추가합니다. 채팅방에서는 입장, 대화하기의 기능이 있으므로 handleAction을 통해 분기 처리합니다.
입장 시에는 채팅룸의 session정보에 클라이언트의 session리스트에 추가해 놓았다가 채팅룸에 메시지가 도착할 경우 채팅룸의 모든 session에 메시지를 발송하면 채팅이 완성됩니다.
 */
/*
pub/sub방식을 이용하면 구독자 관리가 알아서 되므로 웹소켓 세션 관리가 필요 없어집니다.
또한 발송의 구현도 알아서 해결되므로 일일이 클라이언트에게 메시지를 발송하는 구현이 필요 없어집니다.
 따라서 채팅방 DTO는 다음과 같이 간소화됩니다.
 */
/*
Redis에 저장되는 객체들은 Serialize가능해야 하므로 Serializable을 참조하도록 선언하고 serialVersionUID를 세팅해 줍니다.
 */
@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}