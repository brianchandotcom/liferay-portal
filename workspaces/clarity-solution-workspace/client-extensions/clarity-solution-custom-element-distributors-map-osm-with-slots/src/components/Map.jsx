/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import L from 'leaflet';
import {useState} from 'react';
import {MapContainer, Marker, TileLayer, Tooltip} from 'react-leaflet';

import CalculateBounds from './CalculateBounds.jsx';

const ICON = new L.Icon({
	iconAnchor: [16, 32],
	iconSize: [32, 32],
	iconUrl: 'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',
});

const PARIS = [48.8566, 2.3522];

const Map = ({items}) => {
	const [hoveredMarker, setHoveredMarker] = useState(null);

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
					<Marker
						eventHandlers={{
							mouseout: () => setHoveredMarker(null),
							mouseover: () => setHoveredMarker(item.id),
						}}
						icon={ICON}
						key={item.id}
						position={item.position}
					>
						{hoveredMarker === item.id && (
							<Tooltip
								direction="top"
								offset={[0, -8]}
								opacity={1}
								permanent
							>
								<div style={{whiteSpace: 'normal'}}>
									<slot name={item.slot}></slot>
								</div>
							</Tooltip>
						)}
					</Marker>
				))}
			</MapContainer>
		</div>
	);
};

export default Map;
