import express from 'express';
import UserModel from '../models/user.js';
import bcryptjs from 'bcryptjs';
import jwt from 'jsonwebtoken';
import HttpError from '../services/err.js';
import config from '../../config.js';
import db from '../services/db.js';
import {
    checkAuth,
    user
} from '../services/auth.js';
import _ from 'lodash';
import WallModel from '../models/wall.js';

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
    router.get('/', async (req, res, next) => {
        var {
            id
        } = req.params;
        var wm = new WallModel();
        res.json({
            status: 'success',
            data: await wm.atId(id),
        })
    });
}