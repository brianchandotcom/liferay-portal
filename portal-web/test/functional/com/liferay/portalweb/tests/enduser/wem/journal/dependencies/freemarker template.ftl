<ul>
<#list [
'reserved-article-asset-tag-names',
'reserved-article-author-comments',
'reserved-article-author-email-address',
'reserved-article-author-id',
'reserved-article-author-job-title',
'reserved-article-author-name',
'reserved-article-create-date',
'reserved-article-description',
'reserved-article-display-date',
'reserved-article-id',
'reserved-article-modified-date',
'reserved-article-small-image-url',
'reserved-article-title',
'reserved-article-url-title',
'reserved-article-version'] as theVar>
<li>
	  <#if (.vars[theVar])??>
		<@clay["icon"] symbol="check" />
		<#else>
		<@clay["icon"] symbol="exclamation-full" />
		</#if>&nbsp; ${theVar}
</#list>
</ul>