<style>
	.learn-search-sort {
		align-items: center;
		height: 32px;
		margin-bottom: 24px;
		padding: 8px 0px 8px 8px;
	}

	.learn-search-sort:hover {
		background-color: #EDF3FE;
		border-radius: 10px;
	}

	.learn-search-sort option {
		color: #282934;
		font-size: 16px;
	}

	.learn-search-sort .form-group-item {
		height: 32px;
		margin-bottom: 0px;
		min-height: 32px;
	}

	.learn-search-sort > .form-group-item:not(:last-child) {
		margin-right: 0px;
	}

	.learn-search-sort .form-group-item .input-select-wrapper {
		height: 32px;
		margin-bottom: 0px;
	}

	.learn-search-sort .form-group-item select {
		align-items: center;
		background-color: transparent;
		background: url('data:image/svg+xml;utf-8,<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"><path d="M7 10l5 5 5-5z"/><path fill="rgba(11, 95, 255, 1)" d="M7 10l5 5 5-5z"/></svg>') calc(100% - 2px) 50% no-repeat;
		border: none;
		color: #0B5FFF;
		font-size: 14px;
		font-weight: 600;
		height: 32px;
		padding-bottom: 0px;
		padding-top: 0px;
	}

	.learn-search-sort .form-group-item select:focus {
		background-color: transparent;
		box-shadow: none;
		color: #0B5FFF;
		height: 32px;
	}

	.learn-search-sort .text-truncate-inline {
		align-items: center;
		color:#282934;
		height: 32px;
	}

	.learn-search-sort .text-truncate-inline .text-truncate {
		font-size: 13px;
		font-weight: 600;
		line-height: 16px;
		margin-top:1px;
	}
</style>

<div class="form-group-autofit learn-search-sort">
	<div class="form-group-item form-group-item-label form-group-item-shrink">
		<label>
			<span class="text-truncate-inline">
				<span class="text-truncate">
					${languageUtil.get(locale, "Sort by:")}
				</span>
			</span>
		</label>
	</div>

	<div class="form-group-item">
		<@liferay_aui.select
			cssClass="sort-term"
			label=""
			name="sortSelection"
		>
				<#if entries?has_content>
				<#list entries as entry>
					<@liferay_aui.option
						label="${entry.getLanguageLabel()}"
						selected=entry.isSelected()
						value="${entry.getField()}"
					/>
				</#list>
			</#if>
		</@liferay_aui.select>
	</div>
</div>