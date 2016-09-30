package com.example.service

import com.example.bridge.IdiomBridge
import com.example.bridge.SentenceBridge
import com.example.bridge.TitleBridge
import com.example.bridge.WordBridge
import com.example.entity.*
import com.example.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
open class TitleService @Autowired constructor(private val repository: TitleRepository) {
	internal fun selectAll(): List<Title> = repository.findAll()

	internal fun selectAllBridge(): List<TitleBridge> {
		return selectAll().map { TitleBridge(it.id, it.name) }
	}

	internal fun storeTitle(titleBridge: TitleBridge): TitleBridge {
		val title = titleBridge.toTitle()
		return TitleBridge(storeTitle(title))
	}

	private fun storeTitle(title: Title): Title {
		return repository.save(title)
	}

	internal fun deleteTitle(id: Int) = repository.delete(id)

	internal fun updateTitle(id: Int, newName: String): TitleBridge {
		return TitleBridge(updateTitleImp(id, newName))
	}

	private fun updateTitleImp(id: Int, newName: String): Title {
		val newTitle = repository.findOne(id)

		newTitle.apply {
			name = newName
			updatedAt = LocalDateTime.now()
		}

		return repository.save(newTitle)
	}

}

@Service
open class SentenceService @Autowired constructor(private val sentenceRepository: SentenceRepository, private val wordService: WordService) {
	internal fun selectAll(): List<Sentence> = sentenceRepository.findAll()

	internal fun storeSentence(sentenceBridge: SentenceBridge): SentenceBridge {
		val sentence = sentenceBridge.toSentence()
		return SentenceBridge(this.storeSentenceImp(sentence))
	}

	private fun storeSentenceImp(sentence: Sentence): Sentence {
		sentence.words = wordService.storeWords(sentence.words)
		return sentenceRepository.save(sentence)
	}
}

@Service
open class IdiomService @Autowired constructor(private val idiomRepository: IdiomRepository, private val meaningService: MeaningService) {
	internal fun storeIdiom(idiomBridge: IdiomBridge): IdiomBridge {
		val idiom = idiomBridge.toIdiom()
		return IdiomBridge(storeIdiomImp(idiom))
	}

	private fun storeIdiomImp(idiom: Idiom): Idiom {
		idiom.meanings = meaningService.storeMeanings(idiom.meanings)
		return idiomRepository.save(idiom)
	}
}

@Service
open class WordService @Autowired constructor(private val repository: WordRepository, private val meaningService: MeaningService) {
	internal fun selectBySentence(sentence: String): List<WordBridge> {
		val wordList = sentence.split(" ").filter { it.length > 2 }.map { it.replace(".", "").replace("?", "").replace(",", "") }
		return wordList.map { repository.findByWord(it) ?: Word(word = it) }.map { WordBridge(it) }
	}

	internal fun selectAll(): List<Word> = repository.findAll()

	internal fun storeWord(word: Word): Word {
		word.meanings = meaningService.storeMeanings(word.meanings)
		return repository.save(word)
	}

	internal fun storeWords(words: List<Word>): List<Word> {
		return words.map {
			if (it.id == null) {
				storeWord(it)
			} else {
				it.apply { meanings = meaningService.storeMeanings(meanings) }
			}
		}
	}
}

@Service
open class MeaningService @Autowired constructor(private val repository: MeaningRepository) {
	internal fun storeMeaning(meaning: Meaning): Meaning {
		println(meaning.meaning)
		return if (meaning.id != null) {
			meaning
		} else {
			repository.findByMeaning(meaning.meaning) ?: repository.save(meaning)
		}
	}

	internal fun storeMeanings(meanings: List<Meaning>): List<Meaning> {
		return meanings.map {
			storeMeaning(it)
		}
	}
}