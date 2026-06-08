/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {FC} from 'react';
import SearchTermsBase from '~/sites/hocs/SearchTerms';
const SearchTerms = SearchTermsBase as React.ComponentType<any>;

interface ISearchTermsPageProps extends React.HTMLAttributes<HTMLDivElement> {
	router: {
		params: object;
	};
}

const SearchTermsPage: FC<ISearchTermsPageProps> = ({router}) => (
	<div className="sites-dashboard-root">
		<div className="row">
			<div className="col-xl-12">
				<SearchTerms router={router} />
			</div>
		</div>
	</div>
);

export default SearchTermsPage;
