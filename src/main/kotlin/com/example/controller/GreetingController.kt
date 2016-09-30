package com.example.controller

import com.example.model.Greeting
import com.example.model.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import java.util.concurrent.TimeUnit

@Controller
class GreetingController {
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	fun greeting(message: Message): Greeting {
		TimeUnit.MILLISECONDS.sleep(10)
		return Greeting("Hello, ${message.name}!")
	}
}