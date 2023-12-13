<style>
	.card-container {
		height: 150px;
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

	.subtitle {
		color: #54555F;
		font-size: small;
	}

	.title {
		color: #282934;
	}
</style>

<#assign capabilityId = restClient.get("/headless-admin-taxonomy/v1.0/sites/${themeDisplay.getSiteGroupId()}/taxonomy-vocabularies/by-external-reference-code/CAPABILITY?fields=id").id />

<div class="col-12 m-0 product-cards row">
	<#list restClient.get("/headless-admin-taxonomy/v1.0/taxonomy-vocabularies/${capabilityId}/taxonomy-categories?fields=description%2Cid%2Cname%2CtaxonomyCategoryProperties").items?sort_by("name") as taxonomyCategory>
		<div class="card-container col-12 col-md-4 col-sm-6 d-flex justify-content-center p-2">
			<a class="d-flex home-card p-3" href="/search?category=${taxonomyCategory.id}">
				<#if taxonomyCategory.taxonomyCategoryProperties?has_content>
					<#list taxonomyCategory.taxonomyCategoryProperties as taxonomyCategoryProperty>
						<#if taxonomyCategoryProperty.key == 'icon'>
							<img
								alt="${taxonomyCategory.name} icon"
								class="icon mr-3"
								src="${taxonomyCategoryProperty.value}"
							/>
						</#if>
					</#list>
				</#if>

				<div>
					<h6 class="title">
						${taxonomyCategory.name}
					</h6>

					<p class="pt-2 subtitle">
						${taxonomyCategory.description}
					</p>
				</div>
			</a>
		</div>
	</#list>
</div>