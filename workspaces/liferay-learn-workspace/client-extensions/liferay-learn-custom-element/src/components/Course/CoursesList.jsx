/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../index.scss';

import ClayLayout from '@clayui/layout';
import React from 'react';
import {useEffect, useState} from 'react';

import {getCoursesAndFirstLessons} from '../../services/course';
import SectionCard from '../Common/SectionCard';

const CoursesList = () => {
	const [courses, setCourses] = useState(null);

	useEffect(() => {
		async () => {
			const response = await getCoursesAndFirstLessons();

			setCourses(
				response
					.filter(
						(course) => course.lesson && course.r_module_c_course
					)
					.sort(
						(a, b) =>
							a.r_module_c_course.position -
							b.r_module_c_course.position
					)
					.slice(0, 3)
			);
		};

	}, []);

	return (
		<ClayLayout.ContainerFluid view>
			<ClayLayout.Row justify="start">
				{courses && !!courses.length && (
					<>
						{courses.map((course, index) => {
							return (
								<SectionCard
									description={
										!course.r_module_c_course.description
											? ''
											: course.r_module_c_course
													.description
									}
									expertise={
										course.r_module_c_course.level.name
									}
									index={index}
									key={course.r_module_c_course.id}
									link={`/l/${course.lesson[0].id}`}
									personas={course.r_module_c_course.audience}
									title={
										!course.r_module_c_course.title
											? ''
											: course.r_module_c_course.title
									}
								/>
							);
						})}
					</>
				)}
			</ClayLayout.Row>
		</ClayLayout.ContainerFluid>
	);
};

export default CoursesList;
