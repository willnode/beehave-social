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

export default function () {
    var router = express.Router();
    router.post('/login', async (req, res, next) => {
        try {
            const {
                username,
                password,
                // pin,
            } = req.body;
            if (!username || !password) {
                throw new HttpError('Email atau sandi dibutuhkan', 201)
            }
            var model = new UserModel();
            var row = await model.atUsername(username);
            if (row && row.role == 'user' && bcryptjs.compareSync(password, row.password)) {
                const token = jwt.sign({
                    id: row.id
                }, config.jwt.secret, {
                    expiresIn: config.jwt.expiresIn
                });
                return res.json({
                    status: 'success',
                    token: token,
                })
            } else {
                throw new HttpError('Email atau sandi salah', 102)
            }
        } catch (error) {
            next(error)
        }
    });

    router.post('/register', async (req, res, next) => {
        try {
            let {
                name,
                email,
                password,
            } = req.body;

            name = (name || '').trim();
            email = (email || '').trim();
            password = (password || '').trim();

            if (!name || !email || !password) {
                throw new HttpError('Nama, Email, sandi dibutuhkan', 201)
            } else if (password.length < 8) {
                throw new HttpError('Password kurang kuat', 201);
            }

            var err;
            await db().transaction(async function (trx) {
                try {
                    await new UserModel().save({
                        name,
                        phone,
                        nik,
                        password: bcryptjs.hashSync(password),
                        role: 'user',
                    }, trx);
                    await trx.commit();
                } catch (error) {
                    await trx.rollback();
                    err = error;
                }
            })
            if (err)
                throw new HttpError('NIK/HP sudah terdaftar', 201)
            let id = (await new UserModel().atUsername(nik)).id;
            const token = jwt.sign({
                id
            }, config.jwt.secret, {
                expiresIn: config.jwt.expiresIn
            });
            return res.json({
                status: 'success',
                token: token,
            });
        } catch (error) {
            next(error)
        }
    });

    router.post('/pin', checkAuth, async (req, res, next) => {
        try {
            const {
                password,
                pin,
            } = req.body;
            var u = user(req);
            if (bcryptjs.compareSync(password, u.password)) {
                await new UserModel().save({
                    id: u.id,
                    pin: pin
                });
                return res.json({
                    status: 'success',
                });
            } else {
                throw new HttpError('Password salah', 103);
            }
        } catch (error) {
            next(error)
        }
    });

    router.get('/info', checkAuth, async (req, res, next) => {
        try {
            var u = user(req);
            return res.json({
                status: 'success',
                id: u.id,
                name: u.name,
                email: u.email,
            });
        } catch (error) {
            next(error)
        }
    });

    router.post('/info', checkAuth, async (req, res, next) => {
        try {
            return res.json({
                status: 'success',
            });
        } catch (error) {
            next(error)
        }
    });

    return router;
}
