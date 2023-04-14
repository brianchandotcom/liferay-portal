<#include "${templatesPath}/SVG">

<div class="col-12 m-0 more-resources row">
	<#if entries?has_content>
		<#list entries as navigationEntry>
			<#assign
				customFields = navigationEntry.getExpandoAttributes()!{}
				navItemIconId = customFields["SVG Sprite Map ID"]!""
			/>

			<div class="card-container col-12 col-sm-6 col-xl-3 d-flex justify-content-center my-1 p-2 p-xl-4">
				<a class="align-items-center d-flex flex-column home-card" href="${navigationEntry.getURL()}">
					<#if navItemIconId?has_content>
						<svg class="icon">
							<use xlink:href="#${navItemIconId}"></use>
						</svg>
					</#if>

					<h6 class="${navItemIconId?has_content?then("pt-3", "")} title">
						${navigationEntry.getName()}
					</h6>
				</a>
			</div>
		</#list>
	</#if>
</div>