package com.star.websocket;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.star.statics.Cons;

public class SystemWebSocketHandler implements WebSocketHandler {
	
	private static Logger logger;
	
	private static ArrayList<WebSocketSession> users;
	
	static {
		if (users == null){
			users = new ArrayList<WebSocketSession>();
		}
		logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	}
	
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1)
			throws Exception {
		logger.debug("connect to the websocket close......");
		users.remove(session);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		users.add(session);
		session.sendMessage(new TextMessage(users.size() + ""));
		System.out.println("connect to the websocket success......");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> wsm)
			throws Exception {
		TextMessage returnMessage = new TextMessage(wsm.getPayload()
				+ " received at server");
		System.out.println(wsm.getPayload().toString().split("@")[0]);
//		session.sendMessage(returnMessage);
		sendMessageToUsers(returnMessage);
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable arg1)
			throws Exception {
		if(session.isOpen()){
            session.close();
        }
		users.remove(session);
		System.out.println("connect to the websocket error......");
	}

	@Override
	public boolean supportsPartialMessages() {
		return true;
	}
	/**
	 * 给所有在线用户发送消息
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message){
		for (WebSocketSession user : users) {
			try {
				System.out.println(user.getAttributes().get(Cons.WEBSOCKET_USERNAME));
				if (user.isOpen()){
					user.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessageToUser(String userName,TextMessage message){
		for (WebSocketSession user : users){
			if (user.getAttributes().get(Cons.WEBSOCKET_USERNAME).equals(userName)) {
				try {
					if (user.isOpen()){
						user.sendMessage(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
