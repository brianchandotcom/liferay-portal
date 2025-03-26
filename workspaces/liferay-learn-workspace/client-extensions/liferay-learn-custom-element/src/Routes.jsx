/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Suspense} from 'react';

const lazyRoutes = {
	'lms-course-list': React.lazy(() =>	import('./components/Course/CoursesList')),
	'lms-certification-list': React.lazy(() => import('./components/Certification/CertificationList')),
	'lms-landingpage-view': React.lazy(() => import('./components/LandingPageView')),
	'lms-learningpath-list': React.lazy(() => import('./components/LearningPath/LearningPathList')
	),
};

export default function Routes({path, properties}) {
	const Route = lazyRoutes[path];

	if (!Route) {
		return <h1>Page not found</h1>;
	}

	return (
		<Suspense fallback={<div>Loading...</div>}>
		  <Route properties={properties} />
		</Suspense>
	  );
}