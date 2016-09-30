package com.example.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "titles")
data class Title(@Id @GeneratedValue
				 var id: Int? = null,
				 @Column
				 var name: String = "",
				 @Column @JsonIgnore
				 var updatedAt: LocalDateTime = LocalDateTime.now(),
				 @Column @JsonIgnore
				 var createdAt: LocalDateTime = LocalDateTime.now(),
				 @OneToMany(mappedBy = "title") @JsonIgnore
				 var sentences: List<Sentence> = listOf())

@Entity
@Table(name = "sentences")
data class Sentence(@Id @GeneratedValue
					var id: Int? = null,
					@Column
					var body: String = "",
					@Column
					var translation: String = "",
					@Column @JsonIgnore
					var updatedAt: LocalDateTime = LocalDateTime.now(),
					@Column @JsonIgnore
					var createdAt: LocalDateTime = LocalDateTime.now(),
					@ManyToOne @JoinColumn(name = "title_id")
					var title: Title = Title(),
					@ManyToMany @JoinTable(name = "sentence_word", joinColumns = arrayOf(JoinColumn(name = "sentence_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "word_id")))
					var words: List<Word> = listOf())

@Entity
@Table(name = "words")
data class Word(@Id @GeneratedValue
				var id: Int? = null,
				@Column(unique = true)
				var word: String = "",
				@Column @JsonIgnore
				var updatedAt: LocalDateTime = LocalDateTime.now(),
				@Column @JsonIgnore
				var createdAt: LocalDateTime = LocalDateTime.now(),
				@ManyToMany(mappedBy = "words") @JsonIgnore
				var sentences: List<Sentence> = listOf(),
				@ManyToMany @JoinTable(name = "word_meaning", joinColumns = arrayOf(JoinColumn(name = "word_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "meaning_id")))
				var meanings: List<Meaning> = listOf())

@Entity
@Table(name = "idioms")
data class Idiom(@Id @GeneratedValue
				 var id: Int? = null,
				 @Column
				 var words: String = "",
				 @Column @JsonIgnore
				 var updatedAt: LocalDateTime = LocalDateTime.now(),
				 @Column @JsonIgnore
				 var createdAt: LocalDateTime = LocalDateTime.now(),
				 @ManyToMany @JoinTable(name = "idiom_meaning", joinColumns = arrayOf(JoinColumn(name = "idiom_id")), inverseJoinColumns = arrayOf(JoinColumn(name = "meaning_id")))
				 var meanings: List<Meaning> = listOf())

@Entity
@Table(name = "meanings")
data class Meaning(@Id @GeneratedValue
				   var id: Int? = null,
				   @Column
				   var meaning: String = "",
				   @Column @JsonIgnore
				   var updatedAt: LocalDateTime = LocalDateTime.now(),
				   @Column @JsonIgnore
				   var createdAt: LocalDateTime = LocalDateTime.now(),
				   @ManyToMany(mappedBy = "meanings") @JsonIgnore
				   var words: List<Word> = listOf(),
				   @ManyToMany(mappedBy = "meanings") @JsonIgnore
				   var idioms: List<Idiom> = listOf())