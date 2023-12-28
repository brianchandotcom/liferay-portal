<style>
	.section-card {
		box-shadow: none !important;
	}

	.section-card:hover {
		background-color: #EDF3FE !important;
		border-bottom: 1px solid #0053F0 !important;
		border-color: #0053F0 !important;
		box-shadow: none !important;
		margin-bottom: 0px !important;
		transform: none !important;
	}
</style>

<div class="landing-page" id="landingPage">
	<div class="section-card" v-for="card in cards">
		<div class="autofit-row autofit-row-center">
			<div class="autofit-col" v-if="card.icon">
				<svg class="icon">
					<use :xlink:href="card.icon"></use>
				</svg>
			</div>

			<div class="autofit-col autofit-col-expand">
				<a :href="removeRelativeURLHTMLExtensions(card.sectionURL)">
					<h4 class="title">{{ card.sectionName }}</h4>
				</a>

				<div class="subsection-wrapper" v-if="card.subsections">
					<ul class="subsection" v-show="card.subsections.length <= 3 || card.showAll">
						<li v-for="subsection in card.subsections">
							<a :href="removeRelativeURLHTMLExtensions(subsection.url)">{{ subsection.name}}</a>
						</li>
					</ul>

					<ul class="subsection" v-show="card.subsections.length > 3 && !card.showAll">
						<li v-for="(subsection, index) in card.subsections" v-show="index < 3">
							<a :href="removeRelativeURLHTMLExtensions(subsection.url)">{{ subsection.name}}</a>
						</li>
						<li>
							<button class="btn btn-monospaced show-more" role="button" type="button" v-on:click="card.showAll = true">
								<svg aria-label="button more show" class="lexicon-icon" role="img">
									<use xlink:href="#ellipsis-h" />
								</svg>
							</button>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function removeRelativeURLHTMLExtensions(url) {
		if (url) {
			return url.replace(/(^(?!http).+).html$/, "$1");
		}

		return url;
	}
</script>