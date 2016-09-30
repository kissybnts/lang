DROP TABLE IF EXISTS titles;
DROP TABLE IF EXISTS sentences;
DROP TABLE IF EXISTS words;
DROP TABLE IF EXISTS idioms;
DROP TABLE IF EXISTS meanings;
DROP TABLE IF EXISTS sentence_word;
DROP TABLE IF EXISTS word_meaning;
DROP TABLE IF EXISTS idiom_meaning;

CREATE TABLE IF NOT EXISTS titles
(
	id         INT PRIMARY KEY AUTO_INCREMENT,
	name       VARCHAR(255) UNIQUE NOT NULL,
	created_at DATETIME            NOT NULL,
	updated_at DATETIME            NOT NULL
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sentences
(
	id          INT PRIMARY KEY AUTO_INCREMENT,
	title_id    INT      NOT NULL,
	body        TEXT,
	translation TEXT,
	created_at  DATETIME NOT NULL,
	updated_at  DATETIME NOT NULL,
	FOREIGN KEY (title_id) REFERENCES titles (id)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS words
(
	id         INT PRIMARY KEY AUTO_INCREMENT,
	word       VARCHAR(255) UNIQUE NOT NULL,
	created_at DATETIME            NOT NULL,
	updated_at DATETIME            NOT NULL
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS idioms
(
	id         INT PRIMARY KEY AUTO_INCREMENT,
	words      VARCHAR(255) UNIQUE NOT NULL,
	created_at DATETIME            NOT NULL,
	updated_at DATETIME            NOT NULL
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS meanings
(
	id         INT PRIMARY KEY AUTO_INCREMENT,
	meaning    VARCHAR(255) NOT NULL UNIQUE,
	created_at DATETIME     NOT NULL,
	updated_at DATETIME     NOT NULL
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS sentence_word
(
	sentence_id INT NOT NULL,
	word_id     INT NOT NULL,
	PRIMARY KEY (sentence_id, word_id),
	FOREIGN KEY (sentence_id) REFERENCES sentences (id),
	FOREIGN KEY (word_id) REFERENCES words (id)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS word_meaning
(
	word_id    INT NOT NULL,
	meaning_id INT NOT NULL,
	PRIMARY KEY (word_id, meaning_id),
	FOREIGN KEY (word_id) REFERENCES words (id),
	FOREIGN KEY (meaning_id) REFERENCES meanings (id)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS idiom_meaning
(
	idiom_id   INT NOT NULL,
	meaning_id INT NOT NULL,
	PRIMARY KEY (idiom_id, meaning_id),
	FOREIGN KEY (idiom_id) REFERENCES idioms (id),
	FOREIGN KEY (meaning_id) REFERENCES meanings (id)
)
	ENGINE = InnoDB
	DEFAULT CHARSET = utf8;

INSERT INTO titles (name, created_at, updated_at) VALUES ('TOEIC Vocabulary', sysdate(), sysdate());
