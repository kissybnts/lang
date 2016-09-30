package com.example.bridge

import com.example.entity.*

data class TitleBridge(val id: Int?, val name: String) {
	fun toTitle(): Title = Title(id, name)
	constructor(title: Title): this(title.id, title.name)
}

data class SentenceBridge(val id: Int?, val body: String, val translation: String, val title: TitleBridge, val words: List<WordBridge>){
	fun toSentence(): Sentence = Sentence(id, body, translation, title = title.toTitle(), words = words.map { it.toWord() })
	constructor(sentence: Sentence): this(sentence.id, sentence.body, sentence.translation, TitleBridge(sentence.title), sentence.words.map { WordBridge(it) })
}

data class WordBridge(val id: Int?, val word: String, val meanings: List<MeaningBridge>){
	fun toWord(): Word = Word(id, word, meanings = meanings.map { it.toMeaning() })
	constructor(word: Word): this(word.id, word.word, word.meanings.map { MeaningBridge(it) })
}

data class IdiomBridge(val id: Int?, val words: String, val meanings: List<MeaningBridge>) {
	fun toIdiom(): Idiom = Idiom(id, words, meanings = meanings.map { it.toMeaning() })
	constructor(idiom: Idiom): this(idiom.id, idiom.words, idiom.meanings.map { MeaningBridge(it) })
}


data class MeaningBridge(val id: Int?, val meaning: String) {
	fun  toMeaning(): Meaning = Meaning(id, meaning)
	constructor(meaning: Meaning): this(meaning.id, meaning.meaning)
}

