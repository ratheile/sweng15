# Author: Raffael Theiler
# Purpose: Install data from the textfile to the mongodb
import pymongo
from pymongo import MongoClient

## connects by default to db at localhost:27017
connection = MongoClient()  


print(connection.database_names())
