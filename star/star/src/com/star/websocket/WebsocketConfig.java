package com.star.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), "/websocket").addInterceptors(new HandshakeInterceptor());
		registry.addHandler(systemWebSocketHandler(), "/sockjs/websocket").addInterceptors(new HandshakeInterceptor())
		.withSockJS();
	}
	
	@Bean
	public SystemWebSocketHandler systemWebSocketHandler(){
		return new SystemWebSocketHandler();
	}
	
	
}
