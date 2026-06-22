import React, {FC} from 'react';
import SearchTermsBase from 'sites/hocs/SearchTerms';
const SearchTerms = SearchTermsBase as React.ComponentType<any>;

interface ISearchTermsPageProps extends React.HTMLAttributes<HTMLDivElement> {
	router: {
		params: object;
	};
}

const SearchTermsPage: FC<ISearchTermsPageProps> = ({router}) => (
	<div className='sites-dashboard-root'>
		<div className='row'>
			<div className='col-xl-12'>
				<SearchTerms router={router} />
			</div>
		</div>
	</div>
);

export default SearchTermsPage;
