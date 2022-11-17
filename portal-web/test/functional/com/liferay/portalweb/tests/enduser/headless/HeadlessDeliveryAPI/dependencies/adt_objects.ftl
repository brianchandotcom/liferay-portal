<div class="widget-mode-simple">
<div class="container">
<div class="col-md-8 mx-auto">
			<#assign
				universityEntries = restClient.get("/c/universities").items
			/>
			<#if universityEntries?has_content>
				<#list universityEntries as universityEntry>
					<p>
						${universityEntry.name}
					</p>
				</#list>
			</#if>
		</div>
	</div>
</div>