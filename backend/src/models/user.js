import BaseModel from './base.js';

class UserModel extends BaseModel {
    table = 'user';
    columns = ['name', 'email', 'password'];
    /**
     *
     * @param {string} user
     * @returns {Promise<User>}
     */
    async atUsername(user) {
        return await this.conn().where({
            email: user
        }).first();
    }
    /**
     *
     * @param {string} user
     * @returns {Promise<User>}
     */
    atId(id) {
        return super.atId(id);
    }
}

/**
 * @typedef User
 * @property {number} id
 * @property {string} name
 * @property {string} email
 * @property {string} password
 */

export default UserModel;