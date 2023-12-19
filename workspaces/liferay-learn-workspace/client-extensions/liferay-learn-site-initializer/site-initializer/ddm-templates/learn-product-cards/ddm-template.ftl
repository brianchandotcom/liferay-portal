<style>
	.card-container {
		height: 150px;
		min-width: auto!important;
	}

	.home-card {
		background-color:#FBFCFE!important;
		border-color:#E7EFFF!important;
		border-radius: 10px!important;
		border-width:1px!important;
		min-width: 100%;
	}

	.home-card:hover {
		background-color: #EDF3FE!important;
		border-color: #0053F0!important;
	}

	.product-cards {
		min-width: 100%;
	}

	.responsive-text {
		display: -webkit-box;
    	overflow: hidden;
    	text-overflow: ellipsis;
    	-webkit-box-orient: vertical;
    	-webkit-line-clamp: 2;
	}

	.subtitle {
		color: #54555F;
		font-size: small;
	}

	.title {
		color: #282934;
	}
</style>

<div class="m-0 product-cards row">
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
							class="icon mr-3"
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