<#assign
	assetEntryId = ObjectEntry_objectEntryId.getData()

	response = restClient.get("/c/lessons/${assetEntryId}?nestedFields=module,course&nestedFieldsDepth=2&fields=r_lesson_c_module.r_module_c_course.title")

	pageTitle = response.r_lesson_c_module.r_module_c_course.title + " - " +.data_model["ObjectRelationship#C_Module#lesson_title"].getData() + " - " + ObjectField_title.getData()
/>

<#if pageTitle?has_content>
	<#assign void = portalUtil.setPageTitle(pageTitle, request) />
</#if>