package com.example.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

@EnableWebSocketMessageBroker
@Configuration
open class WebSocketConfig : AbstractWebSocketMessageBrokerConfigurer() {

	override fun registerStompEndpoints(registry: StompEndpointRegistry?) {
		// WebSocketのエンドポイントを指定
		registry?.addEndpoint("/hello")?.withSockJS()
	}

	override fun configureMessageBroker(registry: MessageBrokerRegistry?) {
		// Controllerでハンドリングするeエンドポイントのプリフィクス
		// Topic(Pub-Sub)とQueue(P2P)を有効化 -> メッセージブローカーがハンドリングする
		registry?.setApplicationDestinationPrefixes("/app")?.enableSimpleBroker("/topic", "/queue")
	}
}