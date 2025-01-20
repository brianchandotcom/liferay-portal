<#if (DDMStructure_workshopDate.getData())?has_content>
	<#assign
		workshopDateString = DDMStructure_workshopDate.getData()
		originalLocale = .locale
	/>

	<#setting locale = localeUtil.getDefault() />

	<#assign
		workshopDate = workshopDateString?date("MM/dd/yy")
		locale = originalLocale
	/>

	<#if DDMStructure_isNextSessionScheduled?has_content>
		<#if (DDMStructure_presenter.getData())?has_content>
			<#assign presenters = DDMStructure_presenter.getOptionsMap()[DDMStructure_presenter.getData()] />
		</#if>

		<#if (DDMStructure_additionalPresenters.getData())?has_content>
			<#assign presenters = presenters + ", " + DDMStructure_additionalPresenters.getData() />
		</#if>

		<#if (DDMStructure_expirationDate.getData())?has_content>
			<#assign
				retireDateString = DDMStructure_expirationDate.getData()
				originalLocale = .locale
			/>

			<#setting locale = localeUtil.getDefault() />

			<#assign
				retireDate = retireDateString?date("MM/dd/yy")
				locale = originalLocale
			/>
		</#if>

		<#assign
			lastSession = "<div class='mb-3'>This is the recording from the " + workshopDate?string["MMMM yyyy"] + " live workshop of this module, presented by " + presenters + ".</div>"
			expiryText = "<div class='mb-3'>This recording will be available until <strong> " + retireDate?string.long + "</strong>.</div>"

			nextSessionBoolean = getterUtil.getBoolean(DDMStructure_isNextLiveSessionScheduled.getData())
		/>

		<#if nextSessionBoolean>
			<#if (DDMStructure_nextSessionUrl.getData())?has_content>
				<#assign nextSession = "<div class='mb-3'>The next live session is scheduled, click <a target='_blank' href='" + DDMStructure_nextSessionUrl.getData() + "'>here</a> to register.</div>" />
			<#else>
				<#assign nextSession = "<div class='mb-3'>The next live session is scheduled, but registration is not yet available, please check again later.</div>" />
			</#if>
		<#else>
			<#assign nextSession = "<div class='mb-3'>The next live session is not yet scheduled, please check again later.</div>" />
		</#if>

		<#assign nowDate = .now?date />

		<span>
			<span>
				<#if retireDate gt nowDate>
					${lastSession}
					${expiryText}

					<#if nextSessionBoolean>
						${nextSession}
					</#if>
				</#if>

				<#if retireDate <= nowDate>
					<div class='mb-3'>The recording from this live session is no longer available.</div>

					${nextSession}
				</#if>
			</span>
		</span>
	</#if>
</#if>