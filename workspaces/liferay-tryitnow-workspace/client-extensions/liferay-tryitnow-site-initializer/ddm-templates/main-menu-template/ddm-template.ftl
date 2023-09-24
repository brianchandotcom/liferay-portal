<style>
    .menu-container {
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
			  padding-top: 10px;
    }

    .menu-container .menu-item a {
        text-decoration: none;
    }

    .menu-container .menu-item:not(:first-child) {
        margin-left: 40px;
    }

    .menu-container .menu-item.selected a {
        border-bottom: 2px solid var(--color-brand-primary);
    }

    .menu-container .hvr-overline-from-center:before {
        background-color: var(--color-brand-primary);
        height: 2px;
				display: unset;
    	  vertical-align: middle;
    }
		
		.menu-container .selected a{
			display : inline-block;
		}
</style>

<#if entries?has_content>
    <div class="menu-container">
        <#list entries as navigationEntry>
            <div class="menu-item text-link-md font-weight-bold ${navigationEntry.isSelected()?then('selected','')}">
                <a class="text-neutral-7 ${navigationEntry.isSelected()?then('','hvr-overline-from-center')}"
                    href="${navigationEntry.getURL()}">${navigationEntry.getName()}</a>
            </div>
        </#list>
    </div>
</#if>