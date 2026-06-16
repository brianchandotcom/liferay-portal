import Pagination from 'shared/components/Pagination';
import React from 'react';
import Row from '../components/Row';

class PaginationKit extends React.Component {
	state = {
		page: 1
	};

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Pagination
						href={window.location.pathname}
						page={1}
						total={10}
					/>
				</Row>

				<Row>
					<Pagination
						href={window.location.pathname}
						page={28}
						total={30}
					/>
				</Row>

				<Row>
					<Pagination
						href={window.location.pathname}
						page={10}
						total={100}
					/>
				</Row>
			</div>
		);
	}
}

export default PaginationKit;
