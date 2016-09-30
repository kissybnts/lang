package com.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class MainController {
	@RequestMapping(method = arrayOf(RequestMethod.GET))
	fun index(model: ModelAndView): ModelAndView {
		return model.apply { viewName = "/index" }
	}
}