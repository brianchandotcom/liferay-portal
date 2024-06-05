<#assign
	taxonomyCategoryBriefs = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${.vars['reserved-article-id'].data}").taxonomyCategoryBriefs
/>

<#list taxonomyCategoryBriefs as taxonomyCategoryBrief>
	<#if taxonomyCategoryBrief.taxonomyCategoryName == 'Recipe'>

		<#assign recipeCategoryId = taxonomyCategoryBrief.taxonomyCategoryId />
	</#if>
</#list>

<div class="learn-recipe-breadcrumbs">
	<div>
		<div class="align-items-baseline d-flex justify-content-between mb-3">
			<ul
			aria-label="breadcrumb navigation"
			class="learn-recipe-breadcrumb"
			role="navigation"
			>
				<li>
					<a href="/"><@clay["icon"] symbol="home-full" /></a>
				</li>
				<li>
					<a href='/search?resource-type=${recipeCategoryId}'>Recipes</a>
				</li>
				<li>
					${.vars["reserved-article-title"].data}
				</li>
			</ul>
		</div>
	</div>
</div>