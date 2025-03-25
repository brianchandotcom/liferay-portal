<ul>
	<#if entries?has_content>
		<#list entries as entry>
			<#if !entry.isDisabled()>
				<li class="language-nav-item">
					<@clay["link"]
						cssClass="liferay-nav-item ${(entry.isSelected())?then('selected', '')}"
						href=entry.getURL()
						label=entry.longDisplayName?cap_first
						lang=entry.getW3cLanguageId()
						rel="nofollow"
					/>
				</li>
			</#if>
		</#list>
	</#if>
</ul>

<style>
	.language-nav-item {
		.liferay-nav-item {
			border-radius: calc(var(--border-radius) / 2);
			color: var(--color-neutral-10, #282934);
			cursor: pointer;
			font-weight: 400 !important;
			margin: 0.25rem 0;
			outline: rgba(99, 153, 255, 0) solid 2px;
			outline-offset: 2px;
			padding: 0.75rem 0.75rem 0.75rem 3rem;
			transition: all 0.1slinear;
			width: 100%;

			&:hover, &:focus, &:focus-visible {
				background: var(--color-action-primary-hover-lighten, #EDF3FE);
				color: var(--color-action-primary-hover, #0053F0);
				outline: var(--color-brand-primary-lighten-2, #6399FF) solid 2px;
			}

			&.selected {
				color: var(--color-neutral-10, #282934) !important;
			}

			&.selected::before {
				content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIHZpZXdCb3g9IjAgMCAxNiAxNiIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZD0iTTYuMDI4NTUgMTMuNDIzN0M1Ljc2OTE4IDEzLjQyMzcgNS41MTYwNSAxMy4zMjA1IDUuMzMxNjggMTMuMTM2MkwxLjM5MTA1IDkuMTkyNDJDMC40NjkxNzkgOC4yMjY4IDEuOTU5OCA2LjkzOTMgMi43ODQ4IDcuNzk4NjdMNS45ODQ4IDExLjAwMThMMTMuMTcyMyAyLjkxMTE3QzE0LjAyMjMgMS45NTgwNSAxNS40OTQyIDMuMjcwNTUgMTQuNjQ0MiA0LjIyMDU1TDYuNzYyOTMgMTMuMDkyNEM2LjU4MTY4IDEzLjI5NTUgNi4zMjU0MyAxMy40MTQzIDYuMDU2NjggMTMuNDIzN0M2LjA0NzMgMTMuNDIzNyA2LjAzNzkzIDEzLjQyMzcgNi4wMjg1NSAxMy40MjM3WiIgZmlsbD0iIzZCNkM3RSIvPgo8L3N2Zz4K);
				left: 1rem;
				position: absolute;
				top: 18px;
			}
		}
	}
</style>