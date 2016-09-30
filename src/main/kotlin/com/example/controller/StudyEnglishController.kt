package com.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/se")
class StudyEnglishController {
	@RequestMapping(value = "/titles")
	fun titles() = "study_english/title"

	@RequestMapping(value = "/sentences")
	fun sentences() = "study_english/sentence"
}