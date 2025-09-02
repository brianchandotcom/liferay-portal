<#assign
	assetEntryId = ObjectEntry_objectEntryId.getData()

	response = restClient.get("/c/quizes/${assetEntryId}?nestedFields=module,course&nestedFieldsDepth=2&fields=r_quiz_c_module.r_module_c_course.title")

	pageTitle = response.r_quiz_c_module.r_module_c_course.title + " - " +.data_model["ObjectRelationship#C_Module#quiz_title"].getData() + " - " + ObjectField_title.getData()
/>

<#if pageTitle?has_content>
	<#assign void = portalUtil.setPageTitle(pageTitle, request) />
</#if>