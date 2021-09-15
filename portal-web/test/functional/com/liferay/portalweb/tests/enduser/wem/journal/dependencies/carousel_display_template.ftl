<#if contents.getSiblings()?has_content>
	<#assign VOID = freeMarkerPortletPreferences.setValue("view", "carouselListView") />

	<#--
	* Using Bootstrap 3 carousel HTML markup
	* ${.vars["reserved-article-id"].data} is the way you fetch this information in Freemarker. In Velocity it is just ($reserved-article-id.data)
	-->

	<section class="main-carousel-wrapper">
		<div class="carousel slide" data-ride="carousel" id="main-carousel-${.vars["reserved-article-id"].data}">
			<ol class="carousel-indicators hidden-sm hidden-xs">
				<#list contents.getSiblings() as cur_contents>
					<li class="${(cur_contents?counter == 1)?then('active', '')}" data-slide-to="${(cur_contents?counter == 1)?then(0, (cur_contents?counter - 1))}" data-target="main-carousel-${.vars["reserved-article-id"].data}"></li>
				</#list>
			</ol>

			<div class="carousel-inner" role="listbox">
				<#list contents.getSiblings() as cur_contents>
					<div class="${(cur_contents?counter == 1)?then('active', '')} item">
						<#assign article = cur_contents.getData()?eval />

						<#-- Here is our taglib call -->

						<@liferay_asset["asset-display"]
							className=article.className
							classPK=getterUtil.getLong(article.classPK, 0)
							template="full_content"
						/>
					</div>
				</#list>
			</div>

			<a class="carousel-control left" data-slide="prev" href="#main-carousel-${.vars["reserved-article-id"].data}" role="button">
				<span aria-hidden="true" class="glyphicon glyphicon-chevron-left"></span>

				<span class="sr-only">Previous</span>
			</a>

			<a class="carousel-control right" data-slide="next" href="#main-carousel-${.vars["reserved-article-id"].data}" role="button">
				<span aria-hidden="true" class="glyphicon glyphicon-chevron-right"></span>

				<span class="sr-only">Next</span>
			</a>
		</div>
	</section>

	<#assign VOID = freeMarkerPortletPreferences.reset() />
</#if>