# Clarity Solution Custom Element Distributors Map OSM With Slots

> **Pedagogical purpose of this module**
>
> - Integrate a third-party library (OpenStreetMap, via Leaflet / react-leaflet) into a Liferay client extension.
> - Bundle the client extension with Vite.
> - Test it standalone, locally, via `yarn dev`.
> - Use the shadow DOM to allow dynamically injecting parameters and HTML into the custom element through native HTML5 slots (very useful for integrating CMS content).
> - Handle CSS injection into the JavaScript, since a `jsImportMapsEntry` client extension only handles JS, not CSS.

Liferay custom element (`clarity-solution-custom-element-distributors-map-osm-with-slots`) that renders an interactive map (via [Leaflet](https://leafletjs.com/) / [react-leaflet](https://react-leaflet.js.org/)) inside a shadow DOM, with markers defined declaratively in HTML through `<map-item>` children and native `<slot>` projection.

## Usage

```html
	<clarity-solution-custom-element-distributors-map-osm-with-slots>
		<map-item id="1"
			latitude="30.402968" longitude="-97.720991"
			name="Warby Parker" slot="item-0"
			tier-key="gold" tier-name="Gold"
		>
			<h5>Warby Parker</h5>
			<div>11700 Domain Blvd Suite 114</div>
			<div>Austin, TX, 78758</div>
		</map-item>
		<map-item id="2"
			latitude="32.945113" longitude="-97.121117"
			name="ClearView Optics" slot="item-1"
			tier-key="bronze" tier-name="Bronze"
		>
			<h5>ClearView Optics</h5>
			<div>1980 East State Hwy 114</div>
			<div>Southlake, TX, 76092</div>
		</map-item>
		<map-item id="3"
			latitude="33.217463" longitude="-96.660282"
			name="Stanton Optical" slot="item-2"
			tier-key="bronze" tier-name="Bronze"
		>
			<h5>Stanton Optical</h5>
			<div>3620 W University Dr #400</div>
			<div>McKinney, TX, 75071</div>
		</map-item>
		<map-item id="4"
			latitude="32.848092" longitude="-96.770822"
			name="Roka" slot="item-3"
			tier-key="silver" tier-name="Silver"
		>
			<h5>Roka</h5>
			<div>5646 Milton St. Suite 540</div>
			<div>Dallas, TX, 75206</div>
		</map-item>
	</clarity-solution-custom-element-distributors-map-osm-with-slots>
```

Each `<map-item>` child becomes a marker:

- `latitude` / `longitude` position the marker on the map.
- `slot` (defaults to `item-<index>` if omitted) is used to project the element's inner HTML into the marker's tooltip via a matching `<slot name="...">` inside the shadow DOM ([src/components/Map.jsx](src/components/Map.jsx)).
- Any markup inside `<map-item>` (e.g. `h2`, `p`) is rendered as-is in the tooltip shown on hover.

## How it works

- [src/main.jsx](src/main.jsx) attaches an open shadow root once per element instance, reads the `<map-item>` direct children from the light DOM, and passes them as data to the React app mounted inside the shadow root. A `MutationObserver` renders the map again whenever `<map-item>` children are added or removed, so items moved into the element after it was defined still show up.
- The map (a `<slot>` per item, empty until a marker is hovered) is scoped to the same shadow root, so the browser natively projects each `<map-item>`'s content into the corresponding tooltip — no HTML cloning or manual serialization involved.
- The map bounds automatically fit all provided items ([src/components/CalculateBounds.jsx](src/components/CalculateBounds.jsx)).

## Client extension

The build is packaged as a Liferay client extension of type `jsImportMapsEntry` ([client-extension.yaml](client-extension.yaml)). This type simply registers the built bundle as a bare specifier (`clarity-solution-custom-element-distributors-map-osm-with-slots`) in Liferay's JS import map, making the custom element available on any page without deploying it as a widget or portlet.

Using `jsImportMapsEntry` here (instead of e.g. a Custom Element / widget client extension) is deliberate: it leaves full control of the element's light-DOM markup to whoever places it on the page. Since the component reads its `<map-item>` children and projects them through native `<slot>`s (see "How it works" above), the author can inject *exactly* the markup they want for each point of interest — arbitrary HTML per tooltip — directly from a Liferay fragment, page template, or content page, rather than being constrained by a fixed widget configuration.

> **Note:** the `<map-item>` children don't have to be hand-written. Since a fragment's rendered HTML can loop over a Liferay collection (Asset List / Collection selector in Fragment configuration, or a `for`/mapped repeat in the fragment's Freemarker/React code), the list of points of interest can be generated dynamically — e.g. one `<map-item>` per item in a collection of locations/stores, with `latitude`, `longitude` and the tooltip markup populated from that collection's fields — instead of listing them statically as in the example above.

## Fragments

Each folder under [fragments](fragments) holds the code of a fragment: copy its `fragment.html` into the fragment's HTML and its `fragment.js` into its JavaScript. The JavaScript imports the bare specifier, which makes Liferay load the bundle through the import map and define the custom element.

- [distributors-map-osm-with-slots](fragments/distributors-map-osm-with-slots) lists the points of interest statically, as in the example above.
- [distributors-map-osm-with-slots-collection](fragments/distributors-map-osm-with-slots-collection) is a placeholder for a Collection Display. Drop the Collection Display into the fragment's drop zone and make each of its items render a `<map-item>`. A Collection Display nests its items several levels deep, while slots only project the element's direct children, so the fragment's JavaScript moves every `<map-item>` it finds in the drop zone to the top level of the custom element and hides what is left of the Collection Display. It leaves the drop zone alone in edit mode, where the page editor owns its DOM, so the map is only filled when the page is viewed. Items added afterwards, for example by a "Load More" pagination, are not moved.
- [distributors-map-osm-with-slots-item](fragments/distributors-map-osm-with-slots-item) renders one `<map-item>` per entry of an object. Place it in the item of a Collection Display, inside the previous fragment, then drop into its own drop zone whatever the tooltip should show, mapping those fragments to the entry's fields. Its `itemSelector` configuration field (see [configuration.json](fragments/distributors-map-osm-with-slots-item/configuration.json)) exposes the current collection item to FreeMarker as `itemObject`, and the "Field Names" configuration names the object fields that fill the `latitude`, `longitude`, `name` and `tier-key` attributes, which default to those of a U3A2 Distributor Location. In edit mode, [fragment.css](fragments/distributors-map-osm-with-slots-item/fragment.css) displays each item as a framed block labeled with its name and coordinates, so the drop zone is easy to reach. A basic component mapped to a field, such as a Heading, does not render a `<map-item>`, so the map would stay empty without this fragment.

The Collection Display only lists the entries the visitor may view, so Guest needs the VIEW permission on the distributor locations for the map to show them to anonymous visitors.

## Development

```bash
yarn            # install dependencies
yarn dev        # Vite dev server (index.html)
yarn build      # production build → build/vite
```

`yarn dev` above serves the standalone [index.html](index.html) demo page directly, no running Liferay portal needed. To instead run the custom element inside a real Liferay instance while keeping hot reload, deploy the client extension, start `yarn dev`, and let [client-extension.dev.yaml](client-extension.dev.yaml) proxy `clarity-solution-custom-element-distributors-map-osm-with-slots`'s URLs to the local Vite dev server (`http://localhost:5173`).