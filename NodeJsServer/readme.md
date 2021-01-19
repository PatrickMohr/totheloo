# Loo Client
Add instructions here




# Loo Api Server

## Description
This Loo Api Server is for getting, posting and updating loo data on a mongodb

## Test it locally

### Get, install and run mongoDb
To test the Loo Server you have to download and install MongoDb for your platform. Use  `https://www.mongodb.com/try/download` and choose the on premise version.
In the best case you download the mondoDb Compass as well: `https://www.mongodb.com/try/download/compass`
With the mongoDb Compass you get a GUI to have a look inside your mongoDb.
After that you can run it.

### install node.js and run the server
Go to `https://nodejs.org/de/download/` and download and install node.js for your operating system.
Test if the installation worked with `node -v` or `npm -v` in your terminal or powershell

If everything is fine navigate to the `NodeJsServer` project folder an run: `npm install`. You should see a folder named `node_modules` afterwards.
Then you can run `node index` to start the application.
You should see: You started the server on port 8080 If you can see this everything works fine!
showing up on your terminal or powershell.


### example requests

#### Path
Get request
basePath/api/loos       -get all loos from backend
#### what you have to send from frontend
you can run this requests without any body or something like that.

#### what you will get

- you will get a response with the fields: status, message, data. Inside the data field you will find an array with the loodata

Hint: You can ignore all `_id` fields its just used for ongodb
Example:

{
    "status": "success",
    "message": "Loos retrieved successfully",
    "data": [
        {
            "place": {
                "longitude": "53.540307",
                "latitude": "10.021677",
                "tag": "ItsATag",
                "navigationDescription": "Just go around the corner where it smells strange!"
            },
            "kind": {
                "description": "Pissoir",
                "icon": "ItsANIcon"
            },
            "name": "WonderToilet",
            "_id": "6000a0ed563ee544a3e92ffe",
            "rating": [
                {
                    "_id": "60045416d334e2ffb36b50d7",
                    "user": "Anon",
                    "ratingText": "It was wonderful",
                    "stars": 4,
                    "date": "2021-01-17T15:13:26.100Z"
                },
                {
                    "_id": "60045416d334e2ffb36b50d8",
                    "user": "Anon1",
                    "ratingText": "It smelled a bit!",
                    "stars": 2,
                    "date": "2021-01-17T15:13:26.100Z"
                }
            ],
            "create_date": "2021-01-14T19:52:13.201Z",
            "price": 0.5,
            "__v": 0,
            "id": 5,
            "version": 2
        }
    ]
}


#### Path
Post request
basePath/api/loos       -add a new loo to database
#### what you have to send from frontend
you have to add a request body to your request.
To check out which fields are necessary, have a look at looModel.js
If something has to get changed just ask!

Example:

{
    "name": "MegaUgglyToilet",
    "price": 0.5,
    "place": {
        "longitude": "53.540307",
        "latitude": "10.021677",
        "tag": "ItsATag",
        "navigationDescription": "Just go around the corner where it smells strange!"
    },
    "kind": {
        "description": "Pissoir",
        "icon": "ItsANIcon"
    },
    "rating": [
        {
            "user": "Anon",
            "ratingText": "It was wonderful",
            "stars": "4"
        }
    ]
}

#### what you will get
- you will get back a success message




#### Path
Patch request
basePath/api/loos/addNewRating/{:looId}       -add a new rating to an existing loo

Example:
basePath/api/loos/addNewRating/5 - adds a new rating to loo with the id 5


#### what you have to send from frontend
you have to add a request body to your PATCH request with a rating. Just put a rating JSON inside

Hint: 
Example:

{
    "user": "Anon20",
    "ratingText": "A very good one",
    "stars": "4"
}

#### what you will get
You will get a success message back