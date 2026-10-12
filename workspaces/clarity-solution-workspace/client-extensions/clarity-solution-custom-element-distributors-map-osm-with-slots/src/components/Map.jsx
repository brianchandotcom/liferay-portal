/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MapContainer, TileLayer} from 'react-leaflet';

import CalculateBounds from './CalculateBounds.jsx';
import MapItemMarker from './MapItemMarker.jsx';

const PARIS = [48.8566, 2.3522];

const Map = ({items}) => {
	const positionedItems = items
		.map((item) => ({
			...item,
			position: [Number(item.latitude), Number(item.longitude)],
		}))
		.filter(
			({position: [lat, lng]}) =>
				Number.isFinite(lat) && Number.isFinite(lng)
		);

	return (
		<div style={{height: '500px', width: '100%'}}>
			<MapContainer
				center={positionedItems[0]?.position ?? PARIS}
				style={{height: '100%', width: '100%'}}
				zoom={4}
			>
				<TileLayer
					attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
				/>

				<CalculateBounds items={items} />

				{positionedItems.map((item) => (
					<MapItemMarker item={item} key={item.id} />
				))}
			</MapContainer>
		</div>
	);
};

export default Map;
