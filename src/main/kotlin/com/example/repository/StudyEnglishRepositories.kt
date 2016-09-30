package com.example.repository

import com.example.entity.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TitleRepository : JpaRepository<Title, Int>

@Repository
interface SentenceRepository : JpaRepository<Sentence, Int>

@Repository
interface WordRepository : JpaRepository<Word, Int> {
	fun findByWord(word: String): Word?
}

@Repository
interface IdiomRepository : JpaRepository<Idiom, Int>

@Repository
interface MeaningRepository : JpaRepository<Meaning, Int> {
	fun findByMeaning(meaning: String): Meaning?
}