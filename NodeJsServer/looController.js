/*
The LooController is for low level database operations:
    - create new loo
    - update existing loo
    - get all loos
    - delete loos
*/

Loo = require('./looModel');


// Handle index actions
exports.index = function (req, res) {
    Loo.get(function (err, loos) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        res.json({
            status: "success",
            message: "Loos retrieved successfully",
            data: loos
        });
    });
};




// Handle create loo actions
exports.new = function (req, res) {
    console.log(req.body)
    var loo = new Loo();
    loo.name = req.body.name ? req.body.name : loo.name;
    loo.price = req.body.price;
    loo.place = req.body.place;
    loo.kind = req.body.kind;
    loo.rating = req.body.rating;
// save the loo and check for errors
loo.save(function (err) {
         if (err)
             res.json(err);
res.json({
            message: 'New loo created!',
            data: loo
        });
    });
};



// Handle view loo info
exports.view = function (req, res) {
    Loo.findById(req.params.loo, function (err, loo) {
        if (err)
            res.send(err);
        res.json({
            message: 'Loo details loading..',
            data: loo
        });
    });
};



// Handle update loo
exports.update = function (req, res) {
Loo.findById(req.params.loo_id, function (err, loo) {
        //send error if error occur
        if (err)
            res.send(err);

            loo.name = req.body.name ? req.body.name : loo.name;
            loo.price = req.body.price;
            loo.place = req.body.place;
            loo.kind = req.body.kind;
            loo.rating = req.body.rating;

// save the loo and check for errors
loo.save(function (err) {
            if (err)
                res.json(err);
            res.json({
                message: 'loo updated',
                data: loo
            });
        });
    });
};



// Handle delete loo
exports.delete = function (req, res) {
    Loo.remove({
        _id: req.params.loo_id
    }, function (err, loo) {
        if (err)
            res.send(err);
res.json({
            status: "success",
            message: 'loo deleted'
        });
    });
};