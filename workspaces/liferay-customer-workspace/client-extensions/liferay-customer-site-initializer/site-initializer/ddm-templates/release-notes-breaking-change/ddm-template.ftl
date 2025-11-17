<#assign
	journalArticleId = .vars["reserved-article-id"].data
	moreInfoURLs = []
	restArticle = restClient.get("/headless-delivery/v1.0/sites/${groupId}/structured-contents/by-key/${journalArticleId}?nestedFields=embeddedTaxonomyCategory")
	ticketURLs = []
	whatChanged = ""
	whoIsAffected = ""
	whatDoINeedToDo = ""
	whyWasTheChangeMade = ""
/>

<#list restArticle.contentFields as contentField>
	<#if stringUtil.equals(contentField.label, "Description")>
		<#list contentField.nestedContentFields as nestedContentField>
			<#if stringUtil.equals(nestedContentField.label, "What changed?")>
				<#assign whatChanged = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "Who is affected?")>
				<#assign whoIsAffected = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "What do I need to do?")>
				<#assign whatDoINeedToDo = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "Why was the change made?")>
				<#assign whyWasTheChangeMade = nestedContentField.contentFieldValue.data />
			</#if>
		</#list>
	<#elseif stringUtil.equals(contentField.label, "More Info")>
		<#list contentField.nestedContentFields as nestedContentField>
			<#if stringUtil.equals(nestedContentField.label, "URL Title")>
				<#assign urlTitle = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "URL")>
				<#assign url = nestedContentField.contentFieldValue.data />
			</#if>
		</#list>

		<#if validator.isNotNull(url) && validator.isNotNull(urlTitle)>
			<#assign moreInfoURLs = arrayUtil.append(moreInfoURLs, "<a href=\"" + url + "\">" + urlTitle + "</a>") />
		</#if>
	<#elseif stringUtil.equals(contentField.label, "Name")>
		<#assign name = contentField.contentFieldValue.data />
	<#elseif stringUtil.equals(contentField.label, "Ticket")>
		<#list contentField.nestedContentFields as nestedContentField>
			<#if stringUtil.equals(nestedContentField.label, "URL Title")>
				<#assign urlTitle = nestedContentField.contentFieldValue.data />
			<#elseif stringUtil.equals(nestedContentField.label, "URL")>
				<#assign url = nestedContentField.contentFieldValue.data />
			</#if>
		</#list>

		<#if validator.isNotNull(url) && validator.isNotNull(urlTitle)>
			<#assign ticketURLs = arrayUtil.append(ticketURLs, "<a href=\"" + url + "\">" + urlTitle + "</a>") />
		</#if>
	</#if>
</#list>

<@clay.panel displayTitle="${name}">
	<div class="h4">
		What Changed?
	</div>

	<p>
		${whatChanged}
	</p>

	<div class="h4">
		Who is affected?
	</div>

	<p>
		${whoIsAffected}
	</p>

	<div class="h4">
		What do I need to do?
	</div>

	<p>
		${whatDoINeedToDo}
	</p>

	<div class="h4">
		Why was the change made?
	</div>

	<p>
		${whyWasTheChangeMade}
	</p>

	<#list moreInfoURLs as moreInfoURL>
		<p class="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
			<@clay.icon symbol="link" /> ${moreInfoURL}
		</p>
	</#list>

	<#list ticketURLs as ticketURL>
		<p class="bg-brand-primary-lighten-5 cp-key-details-paragraph px-3 py-2 rounded">
			<@clay.icon symbol="check" /> ${ticketURL}
		</p>
	</#list>
</@clay.panel>