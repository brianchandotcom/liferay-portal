import Card from 'shared/components/Card';
import React from 'react';
import Row from '../components/Row';

const Lorem = () => (
	<div>
		<p>
			{
				'Lorem officia tempore quo amet porro. Iure vel autem deleniti excepturi dolorem laborum! Fuga quidem laboriosam dolores reiciendis maiores explicabo? Consectetur aliquam quasi quo voluptates placeat natus! Quisquam esse dolores!'
			}
		</p>
		<p>
			{
				'Elit ad vel officia nam non? Sit inventore iste reiciendis quae quae possimus, exercitationem. Sint praesentium excepturi a nisi at, saepe? Quae laboriosam aperiam minima eveniet molestiae ea architecto sequi.'
			}
		</p>
	</div>
);

class CardKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<Card>
						<Card.Header>
							<Card.Title>{'Card Title'}</Card.Title>
						</Card.Header>
						<Card.Body>
							<Lorem />
						</Card.Body>
						<Card.Footer>
							<Card.Title>{'Card Footer'}</Card.Title>
						</Card.Footer>
					</Card>
				</Row>
			</div>
		);
	}
}

export default CardKit;
