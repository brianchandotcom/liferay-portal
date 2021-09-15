<#assign records = ddlDisplayTemplateHelper.getRecords(reserved_record_set_id) />

<#if records?has_content>
	<#list records as cur_record>
		<div class="ddl-record">${ddlDisplayTemplateHelper.renderRecordFieldValue(cur_record.getDDMFormFieldValues("Name")?first, locale)}</div>
	</#list>
</#if>