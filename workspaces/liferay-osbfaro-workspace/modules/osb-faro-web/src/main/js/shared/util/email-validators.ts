/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toPromise} from '~/shared/components/form';

const VALIDATE_DOMAINS =
	/^([a-zA-Z0-9_]([a-zA-Z0-9_-]{0,61}[a-zA-Z0-9_])?\.){1,126}[a-zA-Z0-9][a-zA-Z0-9-]{0,61}[a-zA-Z]$/;
const VALIDATE_EMAILS =
	/^(?![<>{}[\]()])[^@\s]+@[^\s@]+\.[^\s@]+(?<![<>{}[\]()])$/;

export const validateEmailDomain = function validateEmailDomain(
	emailDomain: string
): boolean {
	return VALIDATE_DOMAINS.test(emailDomain);
};

export const validateEmailDomainArr = function validateEmailDomainArr(
	items: string[],
	inputListValue: string | string[]
): string | void {
	const emailDomains = items.concat(inputListValue || []);

	if (emailDomains.some((emailDomain) => !validateEmailDomain(emailDomain))) {
		return Liferay.Language.get(
			'please-enter-the-domain-in-this-format-domain-com'
		);
	}
};

export const validateEmail = function validateEmail(email: string): boolean {
	return VALIDATE_EMAILS.test(email);
};

export const validateEmailArr = function validateEmailArr(
	items: string[],
	inputListValue: string
): Promise<string> {
	const emails = items.concat(inputListValue || []);

	let error = '';

	if (emails.some((email) => !validateEmail(email))) {
		error = Liferay.Language.get(
			'please-enter-the-email-in-this-format-sample-email-com'
		);
	}

	return toPromise(error);
};
