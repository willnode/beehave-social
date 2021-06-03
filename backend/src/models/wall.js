import BaseModel from './base.js';

/**
 * @extends {BaseModel<Wall>}
 */
class WallModel extends BaseModel {
    table = 'wall';
    columns = [
        'user_id', 'title', 'content', 'keyword', 'source', 'rating', 'raters', 'viewers',
        'predicted_rating', 'predicted_raters', 'predicted_viewers', 'predicted_score',
    ];
    timestamp = true;
    /**
     *
     * @param {string} user
     * @returns {Promise<Wall>}
     */
    atId(id) {
        return super.atId(id);
    }
}

/**
 * @typedef Wall
 * @property {number} user_id
 * @property {string} title
 * @property {string} content
 * @property {string} keyword
 * @property {string} source
 * @property {number} rating
 * @property {number} raters
 * @property {number} viewers
 * @property {number} predicted_rating
 * @property {number} predicted_raters
 * @property {number} predicted_viewers
 * @property {number} predicted_score
 * @property {Date} created_at
 * @property {Date} updated_at
 */

export default WallModel;