/**
 * SPDX-FileCopyrightText: (c) 2006 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Simple dev server that serves static files and proxies ALL non-static
 * requests to a Liferay instance, avoiding CORS issues during local
 * development. This includes login pages so cookies are set correctly.
 *
 * Usage:
 *   node dev-server.mjs                          # defaults to http://localhost:8080
 *   node dev-server.mjs http://your-liferay:8080
 *   LIFERAY_URL=http://your-liferay:8080 node dev-server.mjs
 *
 * Steps:
 *   1. Start your Liferay instance
 *   2. Run: npm run dev
 *   3. Open http://localhost:3000/c/portal/login and log in
 *   4. Navigate to http://localhost:3000 to test the widget
 */

import {createReadStream, existsSync, statSync} from 'fs';
import {createServer, request as httpRequest} from 'http';
import {extname, join, resolve} from 'path';
import {URL} from 'url';

const LIFERAY_URL =
	process.argv[2] || process.env.LIFERAY_URL || 'http://localhost:8080';

const PORT = Number(process.env.PORT) || 3000;

const ROOT = resolve(import.meta.dirname || '.');

const MIME_TYPES = {
	'.css': 'text/css',
	'.html': 'text/html',
	'.js': 'application/javascript',
	'.json': 'application/json',
	'.map': 'application/json',
	'.png': 'image/png',
	'.svg': 'image/svg+xml',
};

// Static file paths served from the local filesystem

const STATIC_PATHS = new Set(['/', '/test.html']);

function isStaticFile(pathname) {
	if (STATIC_PATHS.has(pathname)) {
		return true;
	}

	// Serve dist/ files locally

	if (pathname.startsWith('/build/static/')) {
		const filePath = join(ROOT, pathname);

		return existsSync(filePath) && statSync(filePath).isFile();
	}

	return false;
}

const server = createServer((req, res) => {
	const url = new URL(req.url, `http://localhost:${PORT}`);

	// Serve static files from local filesystem

	if (isStaticFile(url.pathname)) {
		const filePath = join(
			ROOT,
			url.pathname === '/' ? 'test.html' : url.pathname
		);

		if (!existsSync(filePath) || !statSync(filePath).isFile()) {
			res.writeHead(404);
			res.end('Not found');

			return;
		}

		const ext = extname(filePath);
		const contentType = MIME_TYPES[ext] || 'application/octet-stream';

		res.writeHead(200, {'Content-Type': contentType});
		createReadStream(filePath).pipe(res);

		return;
	}

	// Proxy everything else to Liferay

	const target = new URL(url.pathname + url.search, LIFERAY_URL);

	const proxyReq = httpRequest(
		target,
		{
			headers: {
				...req.headers,
				host: new URL(LIFERAY_URL).host,
			},
			method: req.method,
		},
		(proxyRes) => {
			res.writeHead(proxyRes.statusCode, proxyRes.headers);
			proxyRes.pipe(res);
		}
	);

	proxyReq.on('error', (error) => {
		console.error(`[proxy] ${error.message}`);
		res.writeHead(502);
		res.end('Bad Gateway: could not reach ' + LIFERAY_URL);
	});

	req.pipe(proxyReq);
});

server.listen(PORT, () => {

	/* eslint-disable no-console */
	console.log();
	console.log(`  Dev server:   http://localhost:${PORT}`);
	console.log(`  Proxying to:  ${LIFERAY_URL}`);
	console.log();

	/* eslint-enable no-console */
});
