<div class="align-items-center d-flex flex-row justify-content-center learn-course-expertise-container">
	<div>
		<p class="component-text learn-course-expertise-text text-paragraph mb-0 text-break text-truncate">
			<#if (selectExpertise.getData())??>
				${selectExpertise.getData()?cap_first}
			</#if>
		</p>
	</div>
</div>

<script>
	const learnCourseExpertiseContainer = document.getElementsByClassName('learn-course-expertise-container')[0];

	learnCourseExpertiseContainer.classList.add("card-tag__expertise-" + ${selectExpertise.getData()});
</script>