import CardTabs from 'shared/components/CardTabs';
import React from 'react';

export default class CardTabsKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<CardTabs
					activeTabId='bar'
					tabs={[
						{
							secondaryInfo: 'Foo secondary info',
							tabId: 'foo',
							title: 'Foo Tab'
						},
						{
							secondaryInfo: 'Bar secondary info',
							tabId: 'bar',
							title: 'Bar Tab'
						}
					]}
				/>
			</div>
		);
	}
}
