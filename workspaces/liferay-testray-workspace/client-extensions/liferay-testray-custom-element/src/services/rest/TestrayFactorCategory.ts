/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {
	APIResponse,
	TestrayFactor,
	TestrayFactorCategory,
	TestrayFactorOption,
} from './types';

type FactorCategory = typeof yupSchema.factorCategory.__outputType;
class TestrayFactorCategoryRest extends Rest<
	FactorCategory,
	TestrayFactorCategory,
	'factorCategoryToOptions'
> {
	constructor() {
		super({
			adapter: ({id, name}: FactorCategory) => ({
				id,
				name,
			}),
			uri: 'factorcategories',
		});
	}

	public async getFactorCategoryItems(factorItems: TestrayFactor[]) {
		const factorCategoryItems: Array<TestrayFactorOption[]> = [];

		for (const factorItem of factorItems) {
			const response = await this.getFactorCategoryOptions(
				factorItem?.factorCategory?.id as number
			);

			if (response?.items) {
				factorCategoryItems.push(response.items);
			}
		}

		return factorCategoryItems;
	}

	public async getFactorCategoryOptions(
		factorCategoryId: number
	): Promise<APIResponse<TestrayFactorOption> | undefined> {
		return this.fetcher(
			`/${this.uri}/${factorCategoryId}/factorCategoryToOptions?fields=id,name&pageSize=1000`
		);
	}

	protected async validate(factorCategory: FactorCategory, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder.eq('name', factorCategory.name).build();

		const response = await this.fetcher<APIResponse<TestrayFactorCategory>>(
			`/factorcategories?filter=${filter}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'category')
			);
		}
	}

	protected async beforeCreate(
		factorCategory: FactorCategory
	): Promise<void> {
		await this.validate(factorCategory);
	}

	protected async beforeUpdate(
		id: number,
		factorCategory: FactorCategory
	): Promise<void> {
		await this.validate(factorCategory, id);
	}
}

export const testrayFactorCategoryRest = new TestrayFactorCategoryRest();
