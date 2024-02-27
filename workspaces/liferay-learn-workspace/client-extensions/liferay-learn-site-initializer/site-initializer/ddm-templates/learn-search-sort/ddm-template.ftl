<style>
	.form-group-autofit{
	  align-items: center;
      height: 32px;
      padding: 8px;
      margin-bottom: 0px;
	}
	
	.form-group-autofit:hover {
       background-color: #EDF3FE;
       border-radius: 10px;
    }
	
	.form-group-autofit .form-group-item {
	   height: 32px;
	}
	
	.form-group-item select{
	    border: none;
		font-weight: 600;
		font-size: 14px;
		align-items: center;
		color: #0B5FFF;
		align-items: center;
		background-color: transparent;
		height: 32px;
        background: url('data:image/svg+xml;utf-8,<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24"><path d="M7 10l5 5 5-5z"/><path fill="rgba(11, 95, 255, 1)" d="M7 10l5 5 5-5z"/></svg>') 100% 50% no-repeat; 
	}
	
	.form-group-item select:focus{
		box-shadow: none;
        color: #0B5FFF;
        background-color: transparent;
        height: 32px;
	}
	
	.text-truncate-inline{
	   height: 32px;
       align-items: center;
	   color:black;
	}
	
	.text-truncate-inline .text-truncate{
	   font-weight: 600;
       font-size: 13px;
	}
	
	.form-group-autofit > .form-group-item:not(:last-child) {
	   margin-right: 0px;
	}
	
	option {
	  color: black;
	  font-size: 16px;
	}
	
	.select-list {
      color: black;
   }   

  .select-list li {
    color: black;
   }

  .select-list li.selected {
    color: black;
  }
	
</style>

<div class="form-group-autofit">
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
            <#list entries as entry >
                <@liferay_aui.option 
                    label="${entry.getLanguageLabel()}"
                    selected=entry.isSelected()
                    value="${entry.getField()}"
                />  
            </#list>
        </#if>
    </@liferay_aui.select>
</div>