import json

from bson.objectid import ObjectId

import pymongo
from flask import Flask, Response, request

app = Flask(__name__)

try:
    mongo = pymongo.MongoClient(host="localhost", port=27017, serverSelectionTimeoutMS=1000)
    db = mongo.company
    mongo.server_info()  # Trigger exception
except:
    print("Error connecting to the database")


@app.route("/users", methods=["GET"])
def get_users():
    try:
        data = list(db.users.find())

        for user in data:
            user["_id"] = str(user["_id"])
        return Response(response=json.dumps(data), status=200, mimetype="application/json")
    except Exception as ex:
        print(ex)
        return Response(response=json.dumps({"message": "Cannot read users"}), status=500, mimetype="application/json")


@app.route('/users', methods=["POST"])
def create_user():
    try:
        user = {
            "name": request.form['name'],
            "lastName": request.form['lastName']
        }
        db_response = db.users.insert_one(user)
        db_inserted_id = db_response.inserted_id
        return Response(
            response=json.dumps(
                {
                    "message": "user created",
                    "id": f"{db_inserted_id}"
                }),
            status=200,
            mimetype="application/json"
        )
    except Exception as ex:
        print(ex)
        return "xxxxx"


if __name__ == '__main__':
    app.run()
