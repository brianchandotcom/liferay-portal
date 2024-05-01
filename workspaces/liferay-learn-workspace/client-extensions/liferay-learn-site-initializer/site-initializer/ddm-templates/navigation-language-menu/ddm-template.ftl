<ul>
	<#if entries?has_content>
		<#list entries as entry>
			<#if !entry.isDisabled()>
					<li class="language-nav-item">
						<@clay["link"]
							cssClass="liferay-nav-item ${(entry.isSelected())?then('selected', '')}"
							href=entry.getURL()
							label=entry.longDisplayName?cap_first
							lang=entry.getW3cLanguageId()
							rel="nofollow"
						/>
					</li>
			</#if>
		</#list>
	</#if>
</ul>