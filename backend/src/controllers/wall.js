import express from 'express';
import UserModel from '../models/user.js';
import bcryptjs from 'bcryptjs';
import jwt from 'jsonwebtoken';
import HttpError from '../services/err.js';
import config from '../../config.js';
import db from '../services/db.js';
import {
    checkAuth,
    checkAuthOptional,
    user
} from '../services/auth.js';
import _ from 'lodash';
import WallModel from '../models/wall.js';
import WallFeedModel from '../models/wallfeed.js';

export default function () {
    var router = express.Router();
    router.get('/', async (req, res, next) => {
        var {
            start_from
        } = req.query;
        start_from = parseInt(start_from || '0');
        var wm = new WallModel();
        var r = wm.expand(await wm.conn().join('user', 'user.id = wall.user_id').orderBy('predicted_score', 'desc').limit(25).offset(start_from).select([
            'wall.id as id',
            'IF(CHAR_LENGTH(content) > 50, CONCAT(LEFT(content, 47),"..."), content) as excerpt',
            'rating',
            'raters',
            'viewers',
            'created_at',
        ]))
        res.json({
            status: 'success',
            data: r,
        })
    });

    router.get('/:id', checkAuthOptional, async (req, res, next) => {
        var {
            id
        } = req.params;
        var u = user(req);
        var wm = new WallModel();
        var wfm = new WallFeedModel();
        var w = await wm.atId(id);
        if (!w)
            throw new HttpError('Artikel tidak ditemukan', 404);
        if (u) {
            // analytics
            var analytics = wfm.atWallAndUser(u.id, id);
            if (!analytics) {
                await wfm.save({
                    user_id: u.id,
                    wall_id: id,
                    rating: null,
                });
                w.viewers++;
                await wm.save(w);
            }
        }
        res.json({
            status: 'success',
            data: w,
        })
    });

    router.post('/:id', checkAuth, async (req, res, next) => {
        var {
            id
        } = req.params;
        var {
            action,
            rating,
        } = req.body;
        var u = user(req);
        var wm = new WallModel();
        var wfm = new WallFeedModel();
        var w = await wm.atId(id);
        if (rating < 0 || rating > 5) {
            throw new HttpError('Rating diluar batas', 401);
        }
        if (!w)
            throw new HttpError('Artikel tidak ditemukan', 404);
        if (u) {
            var analytics = await wfm.atWallAndUser(u.id, id);
            if (!analytics) {
                analytics = analytics.expand({
                    user_id: u.id,
                    wall_id: id,
                });
            }
            if (!analytics.rating) {
                if (w.rating) {
                    w.rating = (w.rating * w.raters + rating) / (w.raters + 1);
                } else {
                    w.rating = rating;
                }
                w.raters++;
            } else {
                if (w.rating) {
                    w.rating = (w.rating * w.raters - analytics.rating + rating) / (w.raters);
                } else {
                    w.rating = rating;
                }
            }
            await wm.save(w);
            analytics.rating = rating;
            await wfm.save(analytics);
        }
        res.json({
            status: 'success',
        })
    });
}