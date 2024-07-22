<script>
	var A = new AUI();

	function toggleCollapse() {
		var content = document.getElementById('collapsibleContent');
		if (window.innerWidth < 768) {
			if (content.style.display === "block") {
			content.style.display = "none";
	  		} else {
				content.style.display = "block";
	  		}
		}
	}

	function checkScreenSize() {
		var content = document.getElementById('collapsibleContent');
		if (window.innerWidth >= 768) {
			content.style.display = "block";
		} else {
	  	content.style.display = "none";
		}
	}

	document.addEventListener('DOMContentLoaded', function() {
		checkScreenSize();
	});

	window.addEventListener('resize', function() {
		checkScreenSize();
	});
</script>

<#assign
	displayDate = .vars["reserved-article-display-date"].getData()?date("EEE, dd MMM yyyy HH:mm:ss Z")
	formattedDate = displayDate?string["LLLL dd, YYYY"]
	journalArticleId = .vars["reserved-article-id"].getData()?number
	journalArticlePK = .vars["reserved-article-resource-prim-key"].getData()?number
/>

<div class="learn-recipe-container">
	<div class="header-navigation d-flex justify-content-between px-3 mb-3">
		<div class="learn-recipe-breadcrumbs">
			<div>
				<div class="align-items-baseline d-flex justify-content-between">
					<ul
					aria-label="breadcrumb navigation"
					class="learn-recipe-breadcrumb"
					role="navigation"
					>
						<li>
							<a href="/"><@clay["icon"] symbol="home-full" /></a>
						</li>
						<li>
							<a href='/search'>Recipes</a>
						</li>
						<li>
							${.vars["reserved-article-title"].data}
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="component-button text-break recipe-feedback">
			<a class="btn btn-nm btn-link page-editor__editable" data-lfr-editable-id="link" data-lfr-editable-type="link" href="https://liferay.dev/c/portal/login?redirect=https://liferay.dev/ask/questions/liferay-learn-feedback/new" id="fragment-txnc-link" rel="" target="" data-tooltip-floating="true">Submit Feedback</a>
		</div>
	</div>

	<div class="container recipe-main">
		<div class="row">
			<div id="left-panel" class="col-md-9 recipe-main">
			  	<div class="disclaimer">
				  	<div class="container-fluid">
					   	<div class="row">
							<div class="col recipe-dialect"><@liferay_ui["message"] key="Recipe"/></div>
						<div class="col text-right"><@liferay_ui["message"] key="published"/> ${formattedDate}</div>
					</div>
				</div>

				<div class="article-title">
					<h1>${.vars["reserved-article-title"].data}</h1>
				</div>
			<div>
			<p class="component-text text-paragraph mb-0 text-break">
				While we make every effort to ensure this Recipe is accurate, it may not always reflect the most recent updates or
				official guidelines. We appreciate your understanding and encourage you to reach out with any feedback or concerns.
			</p>
		</div>
	</div>

	<article>
		<a id="introduction" />

		<h3><@liferay_ui["message"] key="Introduction"/></h3>

		<div>
			<#if (introduction.getData())??>
				${introduction.getData()}
			</#if>
		</div>

		<a id="prerequisites" />

		<h3><@liferay_ui["message"] key="Prerequisites"/></h3>
		<#if Prerequisite.getSiblings()?? && Prerequisite.getSiblings()?has_content>
			<ul>
				<#list Prerequisite.getSiblings() as current_Prerequisite>
					<#if (current_Prerequisite.getData())??>
						<div>
							<li>${current_Prerequisite.getData()}</li>
						</div>
					</#if>
				</#list>
			</ul>
		<#else>
		<div>
			None
		</div>
		</#if>
			<a id="steps" />

			<h3><@liferay_ui["message"] key="Steps"/></h3>
			<#if Steps.getSiblings()?has_content>
				<ol>
					<#list Steps.getSiblings() as current_Step>
						<li>${current_Step.Step.StepInstruction.getData()}</li>

						<#if current_Step.Step.AdditionalNotes.getSiblings()?has_content>
	  						<#list current_Step.Step.AdditionalNotes.getSiblings() as current_Note>
								<#if current_Note?? && current_Note.NoteText.getData()?has_content>
									<div class="adm-block adm-${current_Note.NoteType.getData()}">
										<div class="adm-heading">
											<svg class="adm-icon">
												<use xlink:href="#adm-note"></use>
											</svg>

											<span>
												<@liferay_ui["message"] key="${current_Note.NoteType.getData()}" />
											</span>
										</div>

										<div class="adm-body">
											${current_Note.NoteText.getData()}
										</div>
									</div>
								</#if>
							</#list>
						</#if>

						<#if current_Step.Step.Resources.Image.getData()?has_content>
							<div class="mb-3">
								<img src="${current_Step.Step.Resources.Image.getData()}" class="rounded img-fluid" width="75%" height="75%" />
							</div>
						</#if>
					</#list>
				</ol>
			</#if>

			<a id="conclusion" />
				<h3><@liferay_ui["message"] key="Conclusion"/></h3>
				<#if (Conclusion.getData())??>
					${Conclusion.getData()}
				</#if>
			<a id="tips" />

			<h3><@liferay_ui["message"] key="Tips"/></h3>
				<#if Tip.getSiblings()?has_content>
					<#list Tip.getSiblings() as current_Tip>
						<#if (current_Tip.getData())??>
							<div class="adm-block adm-tip">
								<div class="adm-heading">
								 	<svg class="adm-icon">
										<use xlink:href="#adm-tip"></use>
									</svg>

									<span>tip</span>
								</div>

								<div class="adm-body">
									<p>${current_Tip.getData()}</p>
								</div>
							</div>
						</#if>
					</#list>
				</#if>
	</article>
</div>

<div id="right-panel" class="col-md-3 recipe-sidemenu">
	<div class="container-fluid">
		<div class="row sidemenu-header">
			<div class="col recipe-dialect">
				<@liferay_ui["message"] key="Recipe" />
			</div>

			<div class="col reading-time text-right">
				<#if (timeToFinish.getData())??>
					<span> ${timeToFinish.getData()} Minutes </span>
				</#if>
			</div>
		</div>
	</div>

	<div class="page-nav-menu">
		<div class="component-button text-break">
			<a href="#introduction" class="btn btn-nm btn-link">
				<@liferay_ui["message"] key="Introduction" />
			</a>
		</div>

		<div class="component-button text-break">
			<a href="#prerequisites" class="btn btn-nm btn-link">
				<@liferay_ui["message"] key="Prerequisites" />
			</a>
		</div>

		<div class="component-button text-break">
			<a href="#steps" class="btn btn-nm btn-link">
				<@liferay_ui["message"] key="Steps" />
			</a>
		</div>

		<div class="component-button text-break">
			<a href="#conclusion" class="btn btn-nm btn-link">
				<@liferay_ui["message"] key="Conclusion" />
			</a>
		</div>

		<div class="component-button text-break">
			<a href="#tips" class="btn btn-nm btn-link">
				<@liferay_ui["message"] key="Tips" />
			</a>
		</div>
	</div>

	<div class="page-nav-menu">
		<div class="asset-categories mb-3">
			<@liferay_asset["asset-categories-summary"]
				className="com.liferay.journal.model.JournalArticle"
				classPK=journalArticlePK
				displayStyle="simple-category"
			/>
		</div>

		<div class="asset-tags mb-3">
			<@liferay_asset["asset-tags-summary"]
				className="com.liferay.journal.model.JournalArticle"
				classPK=journalArticlePK
			/>
		</div>

		<div class="d-flex flex-row align-items-center">
			<div><@liferay_ui["message"] key="Was this article helpful?"/></div>
			<@liferay_ratings["ratings"]
				className="com.liferay.journal.model.JournalArticle"
				classPK=journalArticlePK
				type="thumbs"
			/>
		</div>
	</div>
</div>