import knex from 'knex';
import config from '../../config.js';

var singleton = knex({
    client: 'mysql',
    connection: config.database.main,
});


/**
 *
 * @param {Date} date
 */
 function timestamp(date) {
    date = date || new Date();
    return date.toISOString().slice(0, 19).replace('T', ' ');
}

function toIsoString(date) {
    return typeof date == 'string' ? date.replace(' ', 'T') + 'Z' : null;
}

/**
 *
 * @param {Transaction} trx
 */
 async function lastInsertId(trx) {
    return (await trx.select(trx.raw('LAST_INSERT_ID() as c')).first()).c;
}

export default function () {
    return singleton;
}

export {
    timestamp,
    toIsoString,
    lastInsertId,
}