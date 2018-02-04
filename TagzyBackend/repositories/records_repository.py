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

    def get_records(self, collection, query, limit):
        limit = int(limit)
        collection_name = collection

        collection = self.mongo_db.get_collection(collection_name)
        records = [r for r in collection.aggregate(query)]

        return records

    def update_record(self, record, collection, name):
        collection_name = collection
        collection = self.mongo_db.get_collection(collection_name)

        status = collection.update_one({"id_str": record['id']},
                                       {"$set": {
                                           "mining_metadata.tags.{}".format(name): record['mining_metadata']['tags'][
                                               name]}},
                                       upsert=False)

        return json.loads(dumps(status.raw_result))

    def get_stats(self, collection, name):
        collection_name = collection
        collection = self.mongo_db.get_collection(collection_name)

        tagged = collection.count({"mining_metadata.tags.{}".format(name): {"$exists": True}})
        total = collection.count({})
        values = [v for v in collection.aggregate([{'$group':
                                                        {'_id': '$mining_metadata.tags.{}'.format(name),
                                                         'count': {'$sum': 1}}
                                                    }])]

        values.insert(0, {"_id": "Tagged Count", "count": tagged})
        values.insert(0, {"_id": "Total Count", "count": total})

        values = [{"name": v['_id'], "count": v['count']} for v in values]

        return values
