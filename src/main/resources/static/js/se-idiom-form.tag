<se-idiom-form>
	<div class="row s12 m6 grey lighten-5 w98 z-depth-2">
		<div class="col s12">
			<h5 class="green-text text-darken-1 bolder" show="{ words.length == 0 }">Idiom</h5>
			<h5 class="green-text text-darken-1 bolder col s12 m2" show="{ words.length != 0 }">Idiom</h5>
			<h5 class="green-text text-darken-1 bolder col s12 m10" show="{ words.length != 0 }">Meanings</h5>
			<dl class="row">
				<dt class="col input-field s12 m2 margin-top0">
					<input type="text" class="thin-margin-bottom lato-input validate grey-text text-darken-3" value="{ words }" name="wordsText" onchange="{ wordsChange }">
				</dt>
				<dd class="col input-field s12 m2 margin-top0" show="{ words.length != 0 }" each="{ meaning, i in meaningsArray }">
					<input type="text" class="thin-margin-bottom serif validate grey-text text-darken-2" value="{ meaning.meaning }" onchange="{ meaningChange }" name="{ i }">
				</dd>
			</dl>
			<div class="row">
				<button class="btn waves-effect waves-light amber" name="action" show="{ words.length != 0 }"
						onclick="{ storeIdiom }">
					<i class="material-icons">send</i>
				</button>
			</div>
		</div>
	</div>

	<script>
		const self = this
		const STORE_URL = "http://localhost:8080/ser/idiom"
		this.words = ""
		this.meaningsArray = []

		this.wordsChange = function (e) {
			this.words = e.target.value
			if(this.words == ""){
				this.meaningsArray = []
			}else if(this.meaningsArray.length == 0){
				this.meaningsArray.push({ id: null, meaning: "" })
			}
		}

		this.meaningChange = function (e) {
			const meaningIndex = this.meaningsArray.indexOf(e.item.meaning)
			this.meaningsArray[meaningIndex].meaning = e.target.value
			//this.meaningsArray = this.meaningsArray.filter(this.filterMeaning)
			this.meaningsArray.push({ id: null, meaning: "" })
		}

		this.storeIdiom = function (e) {
			const storeMeanings = this.meaningsArray.filter(this.filterMeaning)

			if(storeMeanings.length == 0){
				$('input[name="0"]').focus()
				return
			}

			$.ajax({
				type: 'POST',
				url: STORE_URL,
				contentType: 'application/json',
				data: JSON.stringify({
					id: null,
					words: self.words,
					meanings: storeMeanings
				})
			}).done(function (response) {
				self.words = ""
				self.meaningsArray = []
				self.update()
				self.wordsText.focus()
			}).fail(function (response) {
				console.log(response)
			})
		}

		this.filterMeaning = function (element) {
			return element.meaning != ""
		}

	</script>
</se-idiom-form>