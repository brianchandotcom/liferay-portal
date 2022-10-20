<#assign blogPostingResource = resourceLocator.locate("/headless-delivery/v1.0", "BlogPosting")siteBlogPostingsPage = blogPostingResource.getSiteBlogPostingsPage(groupId, null, null, blogPostingResource.toFilter("creatorName eq Test"), null, blogPostingResource.toSorts("headline:desc"))blogPostings = siteBlogPostingsPage.getItems() />

<#list blogPostings as blogPosting>
	<h1>${blogPosting.headline}</h1>
</#list>