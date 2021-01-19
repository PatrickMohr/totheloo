/*
The LooController is for low level database operations:
    - create new loo
    - update existing loo
    - get all loos
    - delete loos
*/


looService = require('./looService');


// Handle index actions
exports.getAll = function (req, res) {
    looService.getAllLoos(req, res)
};


// Handle create loo actions
exports.new = function (req, res) {
    looService.createNewLoo(req, res);
};


exports.fetchAllNewVersions = function (req, res) {
    looService.fetchAllNewVersions(req, res);
}

exports.addRatingToLoo = function (req, res) {
    looService.addNewRatingToLoo(req, res);
}