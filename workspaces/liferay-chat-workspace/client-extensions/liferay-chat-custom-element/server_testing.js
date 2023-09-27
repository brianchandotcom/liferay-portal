let express = require('express')
let path = require('path')
let serveStatic = require('serve-static')
let app = express()
let portNumber = 3300;
app.use(serveStatic(path.join(`${__dirname}/componentLibrary`)))
app.listen(portNumber)
console.clear();
console.log('Component Library has been published to the below urls');
console.log('JavaScript URL');
console.log(`http://localhost:${portNumber}/components.js`);
console.log('CSS URL');
console.log(`http://localhost:${portNumber}/components.css`);
