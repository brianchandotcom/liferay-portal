<div class="language-selector-container ml-2">
	<button class="d-flex language language-selector language-selector-${template_id} px-2 py-2 utility-nav-link" data-toggle="liferay-dropdown" id="language-selector-id" tabindex="${template_id}">
		<div class="language-selector-globe-icon">
			<@clay["icon"] symbol="globe-lines" />
		</div>

		<div class="language-selector-text text-nowrap">
			<#assign current_locale = locale />
			<#if current_locale?has_content>
				<#assign current_locale = locale[0..1]
					+ " (" + locale[3..4]
					+ ")" />
			</#if>
			${current_locale?upper_case}
		</div>

		<span class="language-caret-icon">
			<@clay["icon"] symbol="caret-bottom-l" />
		</span>
	</button>

	<div aria-labelledby="language-selector-id" class="dropdown-menu dropdown-menu-content">
		<div class="language-dropdown-list-container">
			<div class="language-dropdown-list">
				<ul>
					<#if entries?has_content>
						<#list entries as curLanguage>
							<li class="${(curLanguage.isSelected())?then('selected', '')} language-nav-item">
								<#assign curLanguageLabel = curLanguage.longDisplayName?capitalize />
								<#if curLanguage.shortDisplayName="en" | curLanguage.shortDisplayName="pt">
									<#assign
										curLanguageLocale = curLanguage.getLocale()
										curLanguageLabel = curLanguageLabel + " (" + curLanguageLocale.getDisplayCountry(curLanguageLocale) + ")" />
								</#if>
								<@clay["link"]
									cssClass="language-entry-long-text"
									href=curLanguage.getURL()
									label=curLanguageLabel
									lang=curLanguage.getW3cLanguageId()
									localizeLabel=false
								/>
							</li>
						</#list>
					</#if>
				</ul>
			</div>
		</div>
	</div>
</div>