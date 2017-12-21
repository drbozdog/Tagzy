from pymongo import MongoClient
from bson.json_util import dumps
from bson.json_util import loads
import json


class RecordsRepository():
    def __init__(self, config_file):
        with open(config_file) as f:
            config = json.load(f)

        self.mongo = MongoClient(config['mongo_ip'], port=config['mongo_port'])
        self.mongo_db = self.mongo.get_database('local')

    def get_records(self, limit):
        limit = int(limit)
        collection_name = 'thetrades_twitter_users'

        projection = {
            "_id": False,
            "id": True,
            "description": True,
            "name": True,
            "profile_background_image_url": True,
            "profile_banner_url": True,
            "profile_image_url": True,
            "screen_name": True,
            "verified": True,
            "created_at": True,
            "url": True,
            "mining_metadata": True
        }

        collection = self.mongo_db.get_collection(collection_name)
        records = [r for r in collection.aggregate([{"$match": {"mining_metadata.tags": {"$exists": False}}}
                                                       , {'$sample': {'size': limit}}
                                                       , {"$project": projection}])]

        return records

    def update_record(self, record):
        collection_name = 'test_thetrades_twitter_users'
        collection = self.mongo_db.get_collection(collection_name)

        status = collection.update_one({"id_str": record['id']}, {"$set": record}, upsert=False)

        return json.loads(dumps(status.raw_result))
