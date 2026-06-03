import Nav from 'shared/components/Nav';
import NavBar from 'shared/components/NavBar';
import React from 'react';
import Row from '../components/Row';
import SearchInput from 'shared/components/SearchInput';

class NavBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<NavBar>
						<NavBar.Brand>{'Brand'}</NavBar.Brand>
					</NavBar>
				</Row>

				<Row>
					<NavBar display='light'>
						<NavBar.Brand>{'display: light'}</NavBar.Brand>
					</NavBar>
				</Row>

				<Row>
					<NavBar display='dark'>
						<NavBar.Brand>{'display: dark'}</NavBar.Brand>
					</NavBar>
				</Row>

				<Row>
					<NavBar display='dark'>
						<NavBar.Brand>{'Nav:'}</NavBar.Brand>

						<Nav>
							<Nav.Item active href='#' key='Home'>
								{'Home'}
							</Nav.Item>

							<Nav.Item active={false} href='#' key='Secondary'>
								{'Secondary'}
							</Nav.Item>

							<Nav.Item active={false} href='#' key='Child'>
								{'Child'}
							</Nav.Item>
						</Nav>
					</NavBar>
				</Row>

				<Row>
					<NavBar>
						<NavBar.Brand>{'Search:'}</NavBar.Brand>

						<SearchInput />
					</NavBar>
				</Row>
			</div>
		);
	}
}

export default NavBarKit;
