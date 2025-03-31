export const getCoursesAndFirstLessons = async () => {
	const data = await request({
		url: `${
			config.moduleEndpoint
		}scopes/${getCurrentSiteId()}?filter=position eq 0&nestedFields=course,lesson&pageSize=-1`,
		method: 'get',
	});

	return data.items;
};