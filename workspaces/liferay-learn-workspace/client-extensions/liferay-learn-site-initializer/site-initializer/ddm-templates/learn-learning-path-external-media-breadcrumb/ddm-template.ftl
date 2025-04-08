<script type="module">
	document.addEventListener("DOMContentLoaded", function() {
		const title = document.querySelector(".title");

		title.innerHTML = document.getElementsByTagName('title')[0].textContent;
	});
</script>

<#if (ObjectEntry_objectEntryId.getData())??>
	<#assign
		learningPathId = (themeDisplay.getURLCurrent())?matches("(?<=learning-path=)[^&]*")[0]
		learningPathName = restClient.get("/c/learningpaths/${learningPathId}?fields=name").name
	/>

	<div class="breadcrumb breadcrumb-lp">
		<a class="breadcrumb-home" href="/education-lms/index">
			<@liferay_ui["message"] key="education" />&nbsp/
		</a>&nbsp
		<a href="/education-lms/learning-paths">
			<@liferay_ui["message"] key="learning-path" />&nbsp/
		</a>&nbsp
		<a href="/l/${learningPathId}">${learningPathName}&nbsp/</a>&nbsp
		<span class="breadcrumb-text-truncate title">
			<#if (ObjectField_name.getData())??>
				${ObjectField_name.getData()}
			</#if>
		</span>
	</div>
</#if>

<style>
	.breadcrumb-container {
		position: relative;

		.breadcrumb {
			align-items: baseline;
		}

		.breadcrumb-home {
			align-items: baseline;
			display: flex;

			&::before {
				background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' class='lexicon-icon lexicon-icon-home-full' role='presentation' viewBox='0 0 512 512' fill='%23999AA3'%3E%3Cpath class='lexicon-icon-outline' d='M233.6,22.4c12.5-12.5,32.3-12.5,44.8,0l182.9,182.8c12,12,18.7,28.3,18.7,45.3V512H320V384c0-35.3-28.7-64-64-64s-64,28.7-64,64v128H32V250.5c0-17,6.7-33.3,18.7-45.3L233.6,22.4z'%3E%3C/path%3E%3C/svg%3E");
				background-size: cover;
				color: var(--color-neutral-7, #6C6C75);
				content: '';
				display: inline-block;
				height: 1rem;
				margin-right: 0.25rem;
				vertical-align: middle;
				width: 1rem;
			}
		}

		.breadcrumb-lp {
			color: var(--color-neutral-7, #6C6C75);
			font-family: var(--font-family-sans-serif, Source Sans 3);
			span {
				font-weight: var(--display4-weight, 600);
			}
		}

		.submit-button {
			align-items: center;
			border: none !important;
			display: flex;

			&::after {
				background-image: url("data:image/svg+xml,%3Csvg width='16' height='16' viewBox='0 0 16 16' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cmask id='mask0_2251_1582' class='mask-type-alpha' maskUnits='userSpaceOnUse' x='0' y='0' width='16' height='17'%3E%3Cpath d='M14 2.00383H15C15.5498 2.00383 15.9971 2.45384 16 3.00383V10.0038C16 10.5569 15.5527 11.0038 15 11.0038H13V5.96629C13 4.88194 12.1221 4.00383 11.0371 4.00383H4V3.00383C4 2.45067 4.44727 2.00383 5 2.00383H10.5938L12.2939 0.297593C13.0156 -0.386795 14.0098 0.219468 14 1.00383V2.00383Z' fill='%236B6C7E'/%3E%3Cpath d='M3.52539 8.00383H8.52539C9.22461 8.00383 9.125 9.00383 8.52539 9.00383H3.52539C2.8252 9.00383 2.8252 8.00383 3.52539 8.00383Z' fill='%236B6C7E'/%3E%3Cpath d='M6.52539 10.0038H3.52539C2.8252 10.0038 2.8252 11.0038 3.52539 11.0038H6.52539C7.125 11.0038 7.22461 10.0038 6.52539 10.0038Z' fill='%236B6C7E'/%3E%3Cpath fill-rule='evenodd' clip-rule='evenodd' d='M11 5.00383H1C0.447266 5.00383 0 5.45073 0 6.00383V13.0038C0 13.5569 0.447266 14.0038 1 14.0038H2V15.0038C2.0127 15.9476 3.13086 16.3069 3.70605 15.7101L5.40625 14.0038H11C11.5527 14.0038 12 13.5569 12 13.0038V6.00383C12 5.45073 11.5498 5.00383 11 5.00383ZM11 13.0038H1V6.00383H11V13.0038Z' fill='%236B6C7E'/%3E%3C/mask%3E%3Cg mask='url(%23mask0_2251_1582)'%3E%3Crect width='16' height='16' fill='%230B5FFF'/%3E%3C/g%3E%3C/svg%3E");
				background-size: cover;
				content: '';
				display: inline-block;
				height: 1rem;
				margin-bottom: 0.25rem;
				margin-right: 0.25rem;
				vertical-align: middle;
				width: 1rem;
			}
		}
	}
</style>