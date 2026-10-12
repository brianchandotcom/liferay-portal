/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import L from 'leaflet';
import {useState} from 'react';
import {Marker, Tooltip, useMap} from 'react-leaflet';

const ICON = new L.Icon({
	iconAnchor: [16, 32],
	iconSize: [32, 32],
	iconUrl: 'https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png',
});

// Leaflet clips its panes to the map container, so the tooltip opens toward
// the side of the marker that has the most room left in the map

const OFFSETS = {
	bottom: [0, 0],
	left: [-12, -16],
	right: [12, -16],
	top: [0, -32],
};

const getDirection = (map, position) => {
	const {x, y} = map.latLngToContainerPoint(position);
	const size = map.getSize();

	if (x < size.x / 4) {
		return 'right';
	}

	if (x > (size.x * 3) / 4) {
		return 'left';
	}

	return y < size.y / 2 ? 'bottom' : 'top';
};

const MapItemMarker = ({item}) => {
	const map = useMap();

	const [direction, setDirection] = useState(null);

	return (
		<Marker
			eventHandlers={{
				mouseout: () => setDirection(null),
				mouseover: () =>
					setDirection(getDirection(map, item.position)),
			}}
			icon={ICON}
			position={item.position}
		>
			{direction && (
				<Tooltip
					direction={direction}
					offset={OFFSETS[direction]}
					opacity={1}
					permanent
				>
					<div style={{whiteSpace: 'normal'}}>
						<slot name={item.slot}></slot>
					</div>
				</Tooltip>
			)}
		</Marker>
	);
};

export default MapItemMarker;
