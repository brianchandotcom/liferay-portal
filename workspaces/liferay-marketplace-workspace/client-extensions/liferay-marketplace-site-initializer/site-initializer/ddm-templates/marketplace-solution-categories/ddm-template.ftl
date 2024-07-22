<style>
	.solution-category-container {
		font-size: 1rem;
		line-height: 1.5;
	}

	.solution-category-container .solution-category {
		background-color: #ebeef2;
		border-radius: 2px;
	}
</style>

<#assign VOCABULARY_NAME = "marketplace solution category" />

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#assign categories = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"/categories") />

<#if categories?has_content>
	<#assign categoriesItems = categories.items />
<#else>
	<#assign categoriesItems = [] />
</#if>

<div class="color-neutral-3 d-flex flex-wrap font-size-paragraph-small solution-category-container">
	<#list categoriesItems as category>
		<#if category.vocabulary == VOCABULARY_NAME>
			<div class="bg-neutral-8 border-radius-small mb-1 mr-1 px-1 solution-category">
				${category.name}
			</div>
		</#if>
	</#list>
</div>