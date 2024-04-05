/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikErrors, FormikHelpers, setNestedObjectValues} from 'formik';
import {useEffect, useState} from 'react';

type FormikHelperType<T> = {
	errors: FormikErrors<T>;
	setTouched: FormikHelpers<T>['setTouched'];
};

const useSetTouchedOnForms = <T>(
	condition: (isButtonClicked: boolean) => boolean,
	formikHelpers: FormikHelperType<T>
) => {
	const [isButtonClicked, setIsButtonClicked] = useState(false);

	const {errors, setTouched} = formikHelpers;
	useEffect(() => {
		if (condition(isButtonClicked) || isButtonClicked) {
			setTouched(setNestedObjectValues(errors, true));
		}
	}, [condition, isButtonClicked, errors, setTouched]);

	return {isButtonClicked, setIsButtonClicked};
};

export default useSetTouchedOnForms;
