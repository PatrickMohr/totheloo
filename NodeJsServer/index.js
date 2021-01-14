//prerequisite: import all modules we need
//and add them to express app
let express = require('express');
let bodyParser = require('body-parser');
let mongoose = require('mongoose');
let app = express();
let apiRoutes = require("./api-routes");
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());

//connect to database
mongoose.connect('mongodb://localhost/ToTheLooDB', { useNewUrlParser: true});
var db = mongoose.connection;
if(!db)
    console.log("Error connecting db")
else
    console.log("Db connected successfully")


// Setup server port
var port = process.env.PORT || 8080;

// Send message for default URL
app.get('/', (req, res) => res.send('Hello this is the loo server! You are on the root, nothing to see here!'));

// Use Api routes in the App
app.use('/api', apiRoutes);


// Launch app to listen to specified port
app.listen(port, function () {
    console.log("You started the server on port " + port + " If you can see this everything works fine!");
});