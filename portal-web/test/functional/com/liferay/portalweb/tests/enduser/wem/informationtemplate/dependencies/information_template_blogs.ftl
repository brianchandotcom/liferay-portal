<#if (title.getData())??>
	${title.getData()}
</#if>

<#if (coverImage.getData())?? && coverImage.getData() != "">
	<img src="${coverImage.getData()}" />
</#if>

<#if (description.getData())??>
	${description.getData()}
</#if>

<#assign displayDate_Data = getterUtil.getString(displayDate.getData()) />

<#if validator.isNotNull(displayDate_Data)>
	<#assign displayDate_DateObj = dateUtil.parseDate("yyyy-MM-dd", displayDate_Data, locale) />

	${dateUtil.getDate(displayDate_DateObj, "dd MMM yyyy - HH:mm:ss", locale)}
</#if>