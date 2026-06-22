import ListGroup from 'shared/components/list-group';
import React from 'react';
import Row from '../components/Row';

class ListGroupKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<ListGroup>
						<ListGroup.Item flex>
							<ListGroup.ItemField>
								{'Item 1'}
							</ListGroup.ItemField>

							<ListGroup.ItemField>
								{'ItemField'}
							</ListGroup.ItemField>

							<ListGroup.ItemField>
								{'ItemField'}
							</ListGroup.ItemField>

							<ListGroup.ItemField>
								{'ItemField'}
							</ListGroup.ItemField>
						</ListGroup.Item>

						<ListGroup.Item>
							<ListGroup.ItemField>
								{'Item 2'}
							</ListGroup.ItemField>
						</ListGroup.Item>
					</ListGroup>
				</Row>
			</div>
		);
	}
}

export default ListGroupKit;
