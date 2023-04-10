const {globSync} = require('glob');
const fs = require('fs');
const path = require('path');

const HEADER_REGEXP = /<!--(.*)-->/s;

async function buildSpritemap() {	
	let spritemapContent =
	'<?xml version="1.0" encoding="UTF-8"?>' +
	'<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">' +
	'<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">';

	const claySpritemapPath = require.resolve(
		'@clayui/css/lib/images/icons/icons.svg'
	);
	
	const claySpritemapContent = fs.readFileSync(claySpritemapPath, 'utf8');

	spritemapContent = claySpritemapContent
	.replace('</svg>', '')
	.replace(/\n/gm, '')
	.replace(/\t/gm, '');
	
	const iconsReplaced = [];
	
	const svgFiles = globSync('./src/**/*.svg', { ignore: 'node_modules/**' }).map(file => path.join(__dirname, file))

	for (const svgFile of svgFiles) {
		const content = fs.readFileSync(svgFile, 'utf8')

		const fileName = path.basename(svgFile, '.svg');

		// Remove existing Clay icons that duplicate our new icon names

		const existingSymbolRegex = new RegExp(
			`<symbol id="${fileName}".*?</symbol>`,
			'gm'
		);

		if (existingSymbolRegex.test(spritemapContent)) {
			spritemapContent = spritemapContent.replace(existingSymbolRegex, '');

			iconsReplaced.push(fileName);
		}


		const svgAttributesExec = /<svg\s+([^>]+)>/gm.exec(content);

		let svgAttributes = svgAttributesExec ? svgAttributesExec[1] : '';

		svgAttributes = svgAttributes
			.replace(/id=".*"?/, '')
			.replace(/xmlns="http:\/\/www\.w3\.org\/2000\/svg"/gm, ``);

		spritemapContent += content
			.replace(HEADER_REGEXP, '')
			.replace(/<svg.*?>/gm, `<symbol id="${fileName}" ${svgAttributes}>`)
			.replace(/<\/svg/gm, '</symbol')
			.replace(/\n/gm, '')
			.replace(/\t/gm, '');
	}

	spritemapContent += '</svg>';

	if (!fs.existsSync('dist')) {
		fs.mkdirSync('dist');
	}

	fs.writeFileSync('dist/spritemap.svg', spritemapContent);
}

buildSpritemap();