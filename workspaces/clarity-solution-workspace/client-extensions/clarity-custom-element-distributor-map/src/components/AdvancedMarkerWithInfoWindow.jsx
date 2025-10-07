import { AdvancedMarker, Pin, useAdvancedMarkerRef, InfoWindow } from '@vis.gl/react-google-maps';


const AdvancedMarkerWithInfoWindow = (
	{
		distributor,
		hoveredMarker,
		onClick,
		onClose,
		onMouseOver,
		onMouseOut,
		promoCodeStore
	}
) => {
	const [markerRef, marker] = useAdvancedMarkerRef();

	return (
		<AdvancedMarker
			ref={markerRef}
			title={distributor.name}
			key={`advancedMarker_${distributor.id}`}
			position={distributor.position}
			onClick={() => onClick(distributor.id)}
			onMouseEnter={() => onMouseOver(distributor.id)}
			onMouseLeave={() => onMouseOut()}
		>
			{
				distributor.name == promoCodeStore ?
					<Pin background={'#4285F4'} borderColor={'#1A73E8'} glyphColor={'#FFF'} /> :
					<Pin background={'#f30808ff'} borderColor={'#1A73E8'} glyphColor={'#FFF'} />
			}
			{
				hoveredMarker == distributor.id &&
				(
					<InfoWindow
						key={`infoWindow_${distributor.id}`}
						anchor={marker}
						position={distributor.position}
						onCloseClick={() => onClose(null)}
						headerContent={(<strong>{distributor.name}</strong>)}
					>
						<div style={{ maxWidth: 300 }}>
							<div>{distributor.street}</div>
							<div>{distributor.city}, {distributor.state}, {distributor.zipCode}</div>
						</div>
					</InfoWindow>
				)
			}
		</AdvancedMarker>
	);

};

export default AdvancedMarkerWithInfoWindow;