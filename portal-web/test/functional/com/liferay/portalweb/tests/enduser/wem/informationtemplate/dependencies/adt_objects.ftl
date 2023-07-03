<div class="widget-mode-simple">
	<div class="container">
		<div class="col-md-8 mx-auto">
			<#assign
				universities = restClient.get("/c/universities").items
			/>

			<#if universities?has_content>
				<#list universities as university>
					<p>
						${university.name}
					</p>
				</#list>
			</#if>
		</div>
	</div>
</div>