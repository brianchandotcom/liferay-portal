/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	validateEmail,
	validateEmailArr,
	validateEmailDomain,
	validateEmailDomainArr,
} from '../email-validators';

describe('email-validators', () => {
	describe('validateEmailDomain', () => {
		it.each`
			domain                  | isValid
			${'liferay.com'}        | ${true}
			${'test@liferay.com'}   | ${false}
			${'111.222.333.444'}    | ${false}
			${'[123.123.123.123]'}  | ${false}
			${'<test@liferay.com>'} | ${false}
			${'{test}@liferay.com'} | ${false}
			${'{test@liferay.com}'} | ${false}
			${'(test@liferay.com)'} | ${false}
			${'[test@liferay.com]'} | ${false}
		`('should return $domain as $isValid', ({domain, isValid}) => {
			expect(validateEmailDomain(domain)).toEqual(isValid);
		});
	});

	describe('validateEmailDomainArr', () => {
		it('returns an empty string if there are valid email domains', () => {
			expect(
				validateEmailDomainArr(['liferay.com.br', 'liferay.com'])
			).toBeFalsy();
		});

		it('returns error message when is not validated email domain', () => {
			expect(
				validateEmailDomainArr(['test@liferay.com', 'liferay.com'])
			).toEqual('Please enter the domain in this format: domain.com');
		});
	});

	describe('validateEmail', () => {
		it.each`
			email                    | isValid
			${'test@liferay.com'}    | ${true}
			${'test@liferay.com.br'} | ${true}
			${'liferay.com'}         | ${false}
			${'111.222.333.444'}     | ${false}
			${'[123.123.123.123]'}   | ${false}
		`('should return $email as $isValid', ({email, isValid}) => {
			expect(validateEmail(email)).toEqual(isValid);
		});
	});

	describe('validateEmailArr', () => {
		it('returns an empty string if there are valid emails', () => {
			expect(
				validateEmailArr(['test@liferay.com.br', 'test@liferay.com'])
			).resolves.toBeFalsy();
		});

		it('returns an error message when an email is not valid', () => {
			validateEmailArr(['test@liferay.com', 'liferay.com']).catch(
				(error) => {
					expect(error).toEqual(
						'Please enter the email in this format: sample@email.com'
					);
				}
			);
		});
	});
});
