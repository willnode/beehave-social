import cors from 'cors';
import express from 'express';
import minimist from 'minimist';
import auth from './controllers/auth.js';

const args = minimist(process.argv.slice(2));
const app = express();
const port = args.port || 3000;

app.use(cors());
app.use(express.json());
app.use('/auth', auth());
app.use('/public', express.static('public'));

app.use(
    /**
     * @param {Error} err
     */
    function (err, req, res, next) {
        var resp = {
            status: 'error',
            message: err.message,
            code: err.code || 999,
            stack: err.stack ? err.stack.split('\n') : null,
        };
        writeLog(err.code ? 'ERROR' : 'EXCEPTION', resp)
        res.status(err.code ? 403 : 500).json(resp);
    }
)

app.listen(port, () => console.log(`Beehave-Server listening on ${port}!`));
