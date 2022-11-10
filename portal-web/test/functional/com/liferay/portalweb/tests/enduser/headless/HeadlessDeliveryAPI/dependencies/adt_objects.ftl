<div class="widget-mode-simple">
	<div class="container">
		<div class="col-md-8 mx-auto">
			<#assign objectResource = resourceLocator.locate("/c/universities", "University")/>
			<p>
				"${objectResource.getObjectEntriesPage(false, null, null, null, null, null)}"
			</p>
		</div>
	</div>
</div>