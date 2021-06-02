import BaseModel from './base.js';

class WallFeedModel extends BaseModel {
    table = 'wallfeed';
    columns = [
        'user_id', 'wall_id', 'retention', 'rating'
    ];
    /**
     *
     * @param {number} user
     * @param {number} wall
     * @returns {Promise<WallFeed>}
     */
    async atWallAndUser(user, wall) {
        return await this.conn().where({
            user_id: user,
            wall_id: wall
        }).first();
    }
    /**
     *
     * @param {string} user
     * @returns {Promise<WallFeed>}
     */
    atId(id) {
        return super.atId(id);
    }
}

/**
 * @typedef WallFeed
 * @property {number} user_id
 * @property {number} wall_id
 * @property {number?} retention
 * @property {number?} rating
 */

export default WallFeedModel;