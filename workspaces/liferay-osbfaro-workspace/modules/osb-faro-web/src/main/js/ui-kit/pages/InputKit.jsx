import ClayButton from '@clayui/button';
import Input from 'shared/components/Input';
import Item from '../components/Item';
import React from 'react';
import Row from '../components/Row';

class InputKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<Input placeholder='hello' />
					</Item>
				</Row>

				<Row>
					{Input.SIZES.map((size, index) => (
						<Item key={index}>
							<Input placeholder={size} size={size} />
						</Item>
					))}
				</Row>

				<Row>
					<Item>
						<Input.Group>
							<Input.GroupItem position='prepend' shrink>
								<Input.Text>{'$'}</Input.Text>
							</Input.GroupItem>

							<Input.GroupItem position='append'>
								<Input placeholder='placeholder' />
							</Input.GroupItem>
						</Input.Group>
					</Item>

					<Item>
						<Input.Group>
							<Input.GroupItem position='prepend'>
								<Input placeholder='placeholder' />
							</Input.GroupItem>

							<Input.GroupItem position='append' shrink>
								<Input.Text>{'%'}</Input.Text>
							</Input.GroupItem>
						</Input.Group>
					</Item>
				</Row>

				<Row>
					<Item>
						<Input.Group>
							<Input.Button
								displayType='secondary'
								position='prepend'
							>
								{'Click'}
							</Input.Button>

							<Input.GroupItem position='append'>
								<Input placeholder='placeholder' />
							</Input.GroupItem>
						</Input.Group>
					</Item>

					<Item>
						<Input.Group>
							<Input.GroupItem position='prepend'>
								<Input placeholder='placeholder' />
							</Input.GroupItem>

							<Input.Button
								displayType='secondary'
								position='append'
							>
								{'Click'}
							</Input.Button>
						</Input.Group>
					</Item>
				</Row>

				<Row>
					<Item>
						<Input.Group>
							<Input.GroupItem>
								<Input
									inset='before'
									placeholder='placeholder'
								/>

								<Input.Inset position='before'>
									{'To:'}
								</Input.Inset>
							</Input.GroupItem>
						</Input.Group>
					</Item>

					<Item>
						<Input.Group>
							<Input.GroupItem>
								<Input
									inset='after'
									placeholder='placeholder'
								/>

								<Input.Inset position='after'>
									<ClayButton
										className='button-root'
										displayType='secondary'
									>
										{'Click'}
									</ClayButton>
								</Input.Inset>
							</Input.GroupItem>
						</Input.Group>
					</Item>
				</Row>
			</div>
		);
	}
}

export default InputKit;
