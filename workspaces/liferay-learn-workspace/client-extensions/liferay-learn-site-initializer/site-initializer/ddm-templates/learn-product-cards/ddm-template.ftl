<style>
	.card-container {
		height: 150px;
		min-width: auto !important;
	}

	.home-card {
		background-color: var(--color-brand-primary-lighten-6, #FBFCFE) !important;
		border-color: var(--color-brand-primary-lighten-5, #E7EFFF) !important;
		border-radius: 10px !important;
		border-width:1px !important;
		min-width: 100%;
	}

	.home-card:hover {
		background-color: var(--color-action-primary-hover-10, #EDF3FE) !important;
		border-color: var(--color-brand-primary-darken-1, #0053F0) !important;
		box-shadow: none !important;
		transform: none !important;
	}

	.product-cards {
		min-width: 100%;
	}

	.product-icon{
		height: 50px !important;
		width: 50px !important;
	}

	.responsive-text {
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
	}

	.subtitle {
		color: var(--color-neutral-8, #54555F);
		font-size: small;
	}

	.title {
		color: var(--color-neutral-10, #282934);
	}
</style>

<div class="d-flex justify-content-center m-0 product-cards row">
	<#if entries?has_content>
		<#list entries as navigationEntry>
			<#assign
				customFields = navigationEntry.getExpandoAttributes()!{}
				navItemDescription = customFields["Description"]!""
				navItemIcon = customFields["Icon URL"]!""
			/>

			<div class="card-container col-12 col-md-4 col-sm-6 d-flex justify-content-center p-2">
				<a class="d-flex home-card p-3" href="${navigationEntry.getURL()}">
					<#if navItemIcon?has_content>
						<img
							alt="${navigationEntry.getName()} icon"
							class="icon mr-3 product-icon"
							src="${navItemIcon}"
						/>
					</#if>

					<div>
						<h6 class="responsive-text title">
							${navigationEntry.getName()}
						</h6>

						<p class="pt-2 responsive-text subtitle">
							${navItemDescription}
						</p>
					</div>
				</a>
			</div>
		</#list>
	</#if>
</div>