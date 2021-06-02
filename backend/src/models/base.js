import db, {
    timestamp
} from '../services/db.js';
import _ from 'lodash';

/**
 * @typedef {import('knex').Knex.QueryBuilder} QueryBuilder
 */

/** @template T */
class BaseModel {
    primaryKey = 'id';
    table = '';
    columns = [];
    timestamp = false;
    timestamps = ['created_at', 'updated_at'];
    objects = [];
    constructor() {}
    conn() {
        return db()(this.table);
    }
    /**
     *
     * @param {number} id
     * @returns {Promise<T>}
     */
    async atId(id) {
        return this.expand(await this.conn().where({
            [this.primaryKey]: id
        }).first());
    }
    /**
     *
     * @param {function(conn: QueryBuilder):QueryBuilder} clause
     * @returns {Promise<Order[]>}
     */
    async with(clause) {
        return this.expandAll(await clause(this.conn()).select());
    }
    /**
     *
     * @param {T} row
     * @param {any} trx
     * @returns {Promise}
     */
    async save(row, trx) {
        var con = this.conn();
        row = this.shrink(row);
        var id = row[this.primaryKey];
        row = _.pick(row, this.columns);
        if (_.isEmpty(row)) return;
        if (trx) {
            con = con.transacting(trx);
        }
        if (id != null) {
            if (this.timestamp) {
                row.updated_at = timestamp();
            }
            return await con.where({
                [this.primaryKey]: id
            }).update(row);
        } else {
            if (this.timestamp) {
                row.created_at = timestamp();
                row.updated_at = timestamp();
            }
            return await con.insert(row);
        }
    }
    /**
     *
     * @param {object} row
     * @returns {T}
     */
    expand(row) {
        if (row == null)
            return null;
        if (this.timestamp) {
            this.timestamps.forEach(key => {
                row[key] = row[key] && new Date(row[key]);
            });
        }
        if (this.objects.length > 0) {
            this.objects.forEach(key => {
                try {
                    row[key] = row[key] && JSON.parse(row[key] || '{}');
                } catch (e) {
                    row[key] = {};
                }
            });
        }
        return _.merge(this.populate(), row);
    }
    /**
     *
     * @param {T} row
     * @returns {object}
     */
    shrink(row) {
        if (row == null)
            return null;
        else {
            if (this.timestamp) {
                this.timestamps.forEach(key => {
                    row[key] = row[key] && timestamp(row[key]);
                });
            }
            if (this.objects.length > 0) {
                this.objects.forEach(key => {
                    row[key] = row[key] && JSON.stringify(row[key]);
                })
            }
        }
        return row;
    }
    /**
     * @returns {T}
     */
    populate() {
        return {};
    }
    /**
     *
     * @param {T[]} row
     * @returns {object[]}
     */
    shrinkAll(rows) {
        return rows.map(x => this.shrink(x));
    }
    /**
     *
     * @param {object[]} rows
     * @returns {T[]}
     */
    expandAll(rows) {
        return rows.map(x => this.expand(x));
    }
}

export default BaseModel;