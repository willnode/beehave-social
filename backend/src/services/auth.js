import UserModel from "../models/user.js"
import jwt from 'jsonwebtoken';
import config from '../../config.js';

function checkAuth(req, res, next) {
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(' ')[1]

    if (token == null) return res.status(401).json({
        status: 'error',
        message: 'Token Required',
        code: 101,
    })

    jwt.verify(token, config.jwt.secret, async (err, user) => {

        if (err) return res.status(401).json({
            status: 'error',
            message: 'Token Invalid',
            code: 101,
        });
        req.user = await new UserModel().atId(user.id);
        next()
    })
}

function checkAuthOptional(req, res, next) {
    const authHeader = req.headers['authorization']
    const token = authHeader && authHeader.split(' ')[1]

    if (token == null) return next();

    jwt.verify(token, config.jwt.secret, async (err, user) => {
        if (err) return next();
        req.user = await new UserModel().atId(user.id);
        next()
    })
}
/**
 *
 * @typedef {import(../models/user.js).User}
 */
/**
 *
 * @returns {import("../models/user.js").User}
 */
function user(req) {
    return req.user;
}

export {
    user,
    checkAuth,
    checkAuthOptional
}