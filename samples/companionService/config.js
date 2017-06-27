/**
 * @module
 * This module defines the settings that need to be configured for a new
 * environment.
 * The clientId and clientSecret are provided when you create
 * a new security profile in Login with Amazon.
 *
 * You will also need to specify
 * the redirect url under allowed settings as the return url that LWA
 * will call back to with the authorization code.  The authresponse endpoint
 * is setup in app.js, and should not be changed.
 *
 * lwaRedirectHost and lwaApiHost are setup for login with Amazon, and you should
 * not need to modify those elements.
 */
var config = {
    clientId: 'amzn1.application-oa2-client.42a35b85424e4e29aeeb1b3eb434bfc8',
    clientSecret: 'f761db595743bdf9ae5f643c2612d5d8c69378897c2ebd520765f986192b82be',
    redirectUrl: 'https://localhost:3000/authresponse',
    lwaRedirectHost: 'amazon.com',
    lwaApiHost: 'api.amazon.com',
    validateCertChain: true,
    sslKey: '../javaclient/certs/server/node.key',
    sslCert: '../javaclient/certs/server/node.crt',
    sslCaCert: '../javaclient/certs/ca/ca.crt',
    products: {
        "alexaunity": ["123456"], // Fill in with valid device values, eg: "testdevice1": ["DSN1234", "DSN5678"]
    },
};

module.exports = config;
