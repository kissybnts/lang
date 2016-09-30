<se-sentence-form>
	<div class="row s12 m6 grey lighten-5 w98 z-depth-2">
		<div class="col s12">
			<div class="row">
				<h5 class="green-text text-darken-1 bolder">Title</h5>
				<div class="input-field col s12 m3 input-field-custom">
					<select onchange="{ selectChange }" name="titleSelect">
						<option value="" disabled selected>Choose title</option>
						<option each="{ title, i in titles }" value="{ i }">{ title.name }</option>
					</select>
				</div>
			</div>
			<div class="row">
				<h5 class="green-text text-darken-1 bolder">Sentence</h5>
				<div class="input-field col s12 input-field-custom">
					<textarea class="materialize-textarea lato-input validate grey-text text-darken-3"
							  onblur="{ sentenceChange }" name="sentenceTextArea">{ sentenceText }</textarea>
				</div>
			</div>
			<div class="row" show="{ words.length != 0 }">
				<h5 class="green-text text-darken-1 bolder">Translation</h5>
				<div class="input-field col s12 input-field-custom">
					<textarea class="materialize-textarea serif validate grey-text text-darken-1"
							  onblur="{ translationChange }" name="translationTextArea">{ translationText }</textarea>
				</div>
			</div>
			<div class="row" show="{ words.length != 0 }">
				<h5 class="green-text text-darken-1 bolder col s12 m2">Words</h5>
				<h5 class="green-text text-darken-1 bolder col s12 m10">Meanings</h5>
				<dl class="row" each="{word, i in words}">
					<dt class="col input-field s12 m2 margin-top0">
						<input type="text" class="thin-margin-bottom lato-input validate grey-text text-darken-3"
							   value="{ word.word }" readonly="{ word.id != null }" onchange="{ wordChange }">
					</dt>
					<dd class="col input-field s12 m2 margin-top0" each="{ meaning, j in word.meanings }">
						<input type="text" class="thin-margin-bottom serif validate grey-text text-darken-2"
							   value="{ meaning.meaning }" onchange="{ meaningChange }" name="{ i + '-' + j }"
							   readonly="{ meaning.id != null }">
					</dd>
				</dl>
			</div>
			<div class="row">
				<button class="btn waves-effect waves-light" name="action" onclick="{ searchWord }">
					<i class="material-icons">call_made</i>
				</button>
				<button class="btn waves-effect waves-light amber" name="action" show="{ words.length != 0 }"
						onclick="{ storeSentence }">
					<i class="material-icons">send</i>
				</button>
			</div>
		</div>
	</div>
	<script>
		const SEARCH_URL = "http://localhost:8080/ser/sentence/search"
		const STORE_URL = "http://localhost:8080/ser/sentence"
		const TITLE_URL = "http://localhost:8080/ser/title"
		const self = this
		this.sentenceText = ""
		this.translationText = ""
		this.words = []
		this.titles = []
		this.title = null

		this.showSelect = function () {
			$(document).ready(function () {
				$('select').material_select();
			})
		}

		this.getTitles = function () {
			$.ajax({
				type: 'GET',
				url: TITLE_URL
			}).done(function (response) {
				self.titles = response
				self.update()
				self.showSelect()
			}).fail(function (response) {
				console.log(response)
			})
		}

		this.selectChange = function (e) {
			this.title = this.titles[parseInt(e.currentTarget.value, 10)]
		}

		this.sentenceChange = function (e) {
			this.sentenceText = e.target.value
		}

		this.wordChange = function (e) {
			console.log(e.item)
			const wordIndex = this.words.indexOf(e.item.word)
			this.words[wordIndex].word = e.target.value
		}

		this.meaningChange = function (e) {
			const indexInf = {
				word: parseInt(e.target.name.split("-")[0]),
				meaning: parseInt(e.target.name.split("-")[1])
			}
			const word = this.words[indexInf.word]

			const meaningIndex = word.meanings.indexOf(e.item.meaning)
			word.meanings[meaningIndex].meaning = e.target.value

			word.meanings = word.meanings.filter(function (element) {
				return element.meaning != ""
			})
			word.meanings.push({id: null, meaning: ""})
		}

		this.translationChange = function (e) {
			this.translationText = e.target.value
		}

		this.searchWord = function (e) {
			const sentence = this.sentenceText

			if (sentence == "") {
				return
			}

			$.ajax({
				type: 'GET',
				url: SEARCH_URL + "?sentence=" + sentence
			}).done(function (response) {
				self.words = response
				self.words.forEach(function (word) {
					word.meanings.push({id: null, meaning: ""})
				})
				self.update()
				self.translationTextArea.focus()
			}).fail(function (response) {
				console.log(response)
			})
		}

		this.storeSentence = function (e) {
			if (this.title == null){
				this.titleSelect.focus()
				return
			}

			if(this.sentenceText == ""){
				this.sentenceTextArea.focus()
				return
			}

			if (this.translationText == ""){
				this.translationTextArea.focus()
				return
			}

			const words = this.words.map(function (word) {
				word.meanings = self.filteredMeaning(word)
				return word
			}).filter(function (word) {
				return word.meanings.length != 0
			})

			const submitData = {
				id: null,
				body: self.sentenceText,
				translation: self.translationText,
				words: words,
				title: this.title
			}

			$.ajax({
				type: 'POST',
				url: STORE_URL,
				contentType: 'application/json',
				data: JSON.stringify(submitData)
			}).done(function (response) {
				self.sentenceText = ""
				self.translationText = ""
				self.words = []
				self.update()
				self.sentenceTextArea.focus()
			}).fail(function (response) {
				console.log(response)
			})
		}

		this.filteredMeaning = function (word) {
			return word.meanings.filter(function (meaning) {
				return meaning.meaning != ""
			})
		}

		this.getTitles()
	</script>
</se-sentence-form>