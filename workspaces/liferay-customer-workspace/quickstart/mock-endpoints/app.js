import express from 'express';
import bodyParser from 'body-parser';

const app = express();

app.use(express.json());
app.use(bodyParser.json());

app.post('/ready', async (req, res) => {
	res.send('READY');
});

app.listen(process.env.APP_PORT, () => {
	console.log(`App listening on ${process.env.APP_PORT}`);
});

export default app;