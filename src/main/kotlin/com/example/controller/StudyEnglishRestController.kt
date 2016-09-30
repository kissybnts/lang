package com.example.controller

import com.example.bridge.IdiomBridge
import com.example.bridge.SentenceBridge
import com.example.bridge.TitleBridge
import com.example.bridge.WordBridge
import com.example.entity.Title
import com.example.service.IdiomService
import com.example.service.SentenceService
import com.example.service.TitleService
import com.example.service.WordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ser")
open class StudyEnglishRestController @Autowired constructor(private val titleService: TitleService, private val sentenceService: SentenceService, private val idiomService: IdiomService, private val wordService: WordService) {
	@RequestMapping(value = "/index", method = arrayOf(RequestMethod.GET))
	fun showAll(): List<Title> = titleService.selectAll()

	@RequestMapping(value = "/title", method = arrayOf(RequestMethod.GET))
	fun selectAllTitle(): List<TitleBridge> = titleService.selectAllBridge()

	@RequestMapping(value = "/title", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun storeTitle(@RequestBody title: TitleBridge): TitleBridge = titleService.storeTitle(title)

	@RequestMapping(value = "/title/{id}", method = arrayOf(RequestMethod.DELETE), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun deleteTitle(@PathVariable id: Int) = titleService.deleteTitle(id)

	@RequestMapping(value = "/title/{id}", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun updateTitle(@PathVariable id: Int, @RequestBody name: String): TitleBridge = titleService.updateTitle(id, name)

	@RequestMapping(value = "/sentence", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun storeSentence(@RequestBody sentence: SentenceBridge): SentenceBridge = sentenceService.storeSentence(sentence)

	@RequestMapping(value = "/sentence/search", method = arrayOf(RequestMethod.GET))
	fun searchWord(@RequestParam sentence: String): List<WordBridge> = wordService.selectBySentence(sentence)

	@RequestMapping(value = "/idiom", method = arrayOf(RequestMethod.POST), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
	fun storeIdiom(@RequestBody idiom: IdiomBridge): IdiomBridge = idiomService.storeIdiom(idiom)
}