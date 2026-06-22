import autobind from 'autobind-decorator';
import React from 'react';
import Row from '../components/Row';
import SearchInput from 'shared/components/SearchInput';

class SearchInputKit extends React.Component {
	@autobind
	handleSubmit(val) {
		alert(val);
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<SearchInput onSubmit={this.handleSubmit} />
				</Row>
			</div>
		);
	}
}

export default SearchInputKit;
