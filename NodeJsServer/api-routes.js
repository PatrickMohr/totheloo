// Initialize express router
let router = require('express').Router();
// Set default API response
router.get('/', function (req, res) {
    res.json({
        status: 'Api is working',
        message: 'Welcome to the Loo Server. Find the best public loo data!!',
    });
});
// Import loo controller
var looController = require('./looController');
// loo routes
router.route('/loos')
    .get(looController.getAll)
    .post(looController.new);
router.route('/loos/fetchAllNewVersions')
    .get(looController.fetchAllNewVersions)
router.route('/loos/addNewRating/:loo_id')
    .patch(looController.addRatingToLoo)
// Export API routes
module.exports = router;