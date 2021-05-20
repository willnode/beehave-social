import db, {
    timestamp
} from '../services/db.js';
import _ from 'lodash';

class BaseModel {
    primaryKey = 'id';
    table = '';
    columns = [];
    timestamp = false;
    constructor() {}
    conn() {
        return db()(this.table);
    }
    async atId(id) {
        return this.expand(await this.conn().where({
            [this.primaryKey]: id
        }).first());
    }
    async save(row, trx) {
        var con = this.conn();
        row = this.shrink(row);
        var id = row[this.primaryKey];
        row = _.pick(row, this.columns);
        if (_.isEmpty(row)) return;
        if (trx) {
            con = con.transacting(trx);
        }
        if (id !== undefined) {
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
    expand(row) {
        if (row === null)
            return null;
        else if (Array.isArray(row))
            return row.map(x => this.expand(x));
        else if (typeof row === 'object') {
            if (this.timestamp) {
                row.created_at = row.created_at && new Date(row.created_at);
                row.created_at = row.updated_at && new Date(row.updated_at);
            }
        }
        return row;
    }
    shrink(row) {
        if (row === null)
            return null;
        else if (Array.isArray(row))
            return row.map(x => this.shrink(x));
        else if (typeof row === 'object') {
            if (this.timestamp) {
                row.created_at = row.created_at && timestamp(row.created_at);
                row.created_at = row.updated_at && timestamp(row.updated_at);
            }
        }
        return row;
    }
    populate() {
        return {};
    }
}

export default BaseModel;