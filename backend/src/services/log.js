import fs from 'fs';

/**
 * @type {fs.WriteStream}
 */
var stream;
var streamDate;

if (!fs.existsSync('./tmp')){
    fs.mkdirSync('./tmp');
}
if (!fs.existsSync('./tmp/log')){
    fs.mkdirSync('./tmp/log');
}

function ensureLogStream() {
    var newStreamDate = new Date().toISOString().slice(0, 10);
    if (newStreamDate != streamDate) {
        if (stream) {
            stream.close();
        }
        stream = fs.createWriteStream('./tmp/log/'+ (streamDate = newStreamDate) + ".txt", {
            flags: 'a'
        });
    }
}

/**
 *
 * @param {string} channel
 * @param {any} message
 */
function writeLog(channel, message) {
    ensureLogStream();
    if (typeof message !== 'string') {
        message = JSON.stringify(message);
    }
    return new Promise((resolve, reject) => {
        stream.write(`[ ${new Date().toISOString()}: ${channel} ] - ${message}\n`, (e) => {
            resolve();
        });
    })
}

export {
    writeLog
}