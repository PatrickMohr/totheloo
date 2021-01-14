/*
The looModel is mainly needed to add a structure (schema) to the database
A Loo can have the following attributes:
    - name
    - price
    - place
    - kind
    - rating
    - create_date added imlizit via default value
*/



var mongoose = require('mongoose');
// Setup schema
var looSchema = mongoose.Schema({
    name: {
        type: String,
        required: false,
        default: "Public Toilet"
    },
    price: {
        type: Number,
        required: true
    },
    place: {
        longitude: {
            type: String,
            required: true
        },
        latitude: {
            type: String,
            required: true
        },
        tag: {
            type: String,
            required: false
        },
        navigationDescription: {
            type: String,
            required: false,
        }
    },
    kind: {
        description: {
            type: String,
            required: false
        },
        icon: {
            type: String,
            required: false
        }
    },
    rating: [ 
        {
        date: {
            type: Date,
            default: Date.now
        },
        user: {
            type: String,
            required: false
        },
        ratingText: {
            type: String,
            rquired: true
        },
        stars: {
            type: Number,
            required: true
        }
        }
    ],
    create_date: {
        type: Date,
        default: Date.now
    }
});
// Export Loo model
var Loo = module.exports = mongoose.model('loo', looSchema);
module.exports.get = function (callback, limit) {
    Loo.find(callback).limit(limit);
}