Loo = require('./looModel');

exports.createNewLoo = function(req, res) {
    var loo = new Loo();
    loo.id = req.body.id;
    loo.version = req.body.version;
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
}

exports.getAllLoos = function(req, res) {
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
}


exports.fetchAllNewVersions = function(req, res) {
    Loo.get(function (err, loos) {
        if (err) {
            res.json({
                status: "error",
                message: err,
            });
        }
        idVersionPairs = req.body
        loos= loos.filter(function(oneLoo) {
            for (var idVersionPair of idVersionPairs) {
              if (oneLoo.id === undefined || oneLoo.id != idVersionPair.id || oneLoo.version <= idVersionPair.version) {
                return false;
            }
            }
            return true;
          });
          res.json({
            message: 'All new versions of loos successfully fetched!',
            data: loos
        });
 
    });
}

exports.addNewRatingToLoo = function(req, res) {
    Loo.find({id: req.params.loo_id}, function (err, loo) {
        if (err)
            res.send(err);

        loo[0].rating.push(req.body.newRating)
        loo[0].version = loo[0].version + 1
        Loo.update(
            {id: req.params.loo_id},
            {"rating": loo[0].rating,
             "version": loo[0].version
            }, () => {
            if (err)
                res.json(err);
            res.json({
                message: 'rating added to loo!',
                data: loo
            });
        }
        )

    })
}