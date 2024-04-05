<#if (DDMStructure_recordedDate.getData())?has_content>
	<#assign
		recordedDateString = DDMStructure_recordedDate.getData()
		originalLocale = .locale
	/>

	<#setting locale = localeUtil.getDefault() />

	<#assign
		recordedDate = recordedDateString?date("MM/dd/yy")
		locale = originalLocale
	/>

	<#if (DDMStructure_narrator.getData())?has_content>
		<#assign narrator = DDMStructure_narrator.getOptionsMap()[DDMStructure_narrator.getData()] />
	</#if>

	<div class="mb-3">
		This demo video was recorded in ${recordedDate?string["MMMM yyyy"]} and is narrated by ${narrator}.
	</div>

	<#if (DDMStructure_summary.getData())?has_content>
		<h3 class='mb-3'>Demo Summary</h3>

		<div class='mb-3'>${DDMStructure_summary.getData()}</div>
	</#if>
</#if>