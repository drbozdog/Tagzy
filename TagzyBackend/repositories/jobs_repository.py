class JobsRepository():
    def __init__(self):
        self.jobs = [
            {
                "id": 1,
                "name": "Ridgid Professionals",
                "type": "twitter_user",
                "query": [
                    {"$match": {"$and":
                        [
                            {"mining_metadata.tags.Ridgid Professionals": {"$exists": False}},
                            {"mining_metadata.predictions_probabilities.Ridgid Professionals.Blogger": {"$gt": 0.18}}
                        ]
                    }}
                    , {'$sample': {'size': 80}}
                    , {"$project": {
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
                        "mining_metadata.predictions_probabilities.Ridgid Professionals": True
                    }}
                ],
                "stats_query": {},
                "collection": "thetrades_twitter_users",
                "tags": ["Professional", "Shop", "Manufacturer", "Blogger", "Jobs", "None"]
            },
            {
                "id": 2,
                "name": "Ridgid Professionals Tweets",
                "type": "twitter_post",
                "query": [
                    {"$match": {"$and":
                        [
                            {"mining_metadata.tags.Ridgid Professionals Tweets": {"$exists": False}},
                            {"mining-metadata.sources.thetrades-twitter-posts.query": "professionals"},
                            {"retweeted_status": {"$exists": False}},
                            {"quoted_status": {"$exists": False}},
                        ]
                    }}
                    , {'$sample': {'size': 80}}
                    , {"$project": {
                        "_id": False,
                        "id": True,
                        "entities": True,
                        "text": True,
                        "user.screen_name": True,
                        "extended_tweet.full_text": True,
                        "extended_tweet.entities": True,
                        "mining_metadata.predictions_probabilities.Ridgid Professionals Tweets": True
                    }}
                ],
                "stats_query": {
                    "$and": [
                        {"mining-metadata.sources.thetrades-twitter-posts.query": "professionals"},
                        {"retweeted_status": {"$exists": False}},
                        {"quoted_status": {"$exists": False}},
                    ]
                },
                "collection": "thetrades_twitter_posts",
                "tags": ["Advertising", "Tips", "Career", "Product", "OnSite", "None"]
            },
            {
                "id": 3,
                "name": "RP Mold Tweet",
                "type": "twitter_post",
                "query": [
                    {"$match": {"$and": [
                        {
                            "mining-metadata.sources.thetrades-twitter-posts.query": "track=mold,%23mold,%23moldremediation,%23moldremoval"
                        },
                        {"mining_metadata.tags.RP Mold Tweet": {"$exists": False}},
                        {"retweeted_status": {"$exists": False}},
                        {"quoted_status": {"$exists": False}},
                    ]
                    }}
                    , {'$sample': {'size': 80}}
                    , {"$project": {
                        "_id": False,
                        "id": True,
                        "entities": True,
                        "text": True,
                        "user.screen_name": True,
                        "extended_tweet.full_text": True,
                        "extended_tweet.entities": True,
                        "mining_metadata.predictions_probabilities.RP Mold Tweet": True
                    }}
                ],
                "stats_query": {
                    "$and": [
                        {
                            "mining-metadata.sources.thetrades-twitter-posts.query": "track=mold,%23mold,%23moldremediation,%23moldremoval"
                        },
                        {"retweeted_status": {"$exists": False}},
                        {"quoted_status": {"$exists": False}},
                    ]
                },
                "collection": "thetrades_twitter_posts",
                "tags": ["Mold", "None", "None", "None", "None", "None"]
            },
            {
                "id": 4,
                "name": "Festival Tweets",
                "type": "twitter_post",
                "query": [
                    {"$match": {"$and": [
                        {"$or": [
                            {"created_at_month":{"$gte":10}},
                            {"created_at_month":{"$lte":5}}
                        ]
                        },
                        {"mining_metadata.tags.Festival Tweet": {"$exists": False}},
                        {"retweeted_status": {"$exists": False}},
                        {"quoted_status": {"$exists": False}},
                    ]
                    }}
                    , {'$sample': {'size': 80}}
                    , {"$project": {
                        "_id": False,
                        "id": True,
                        "entities": True,
                        "text": True,
                        "user.screen_name": True,
                        "extended_tweet.full_text": True,
                        "extended_tweet.entities": True,
                        "created_at_month":True,
                        "created_at":True
                    }}
                ],
                "stats_query": {
                    "$and": [
                        {"$or": [
                            {"created_at_month": {"$gte": 10}},
                            {"created_at_month": {"$lte": 5}}
                        ]
                        },
                        {"retweeted_status": {"$exists": False}},
                        {"quoted_status": {"$exists": False}},
                    ]
                },
                "collection": "onstage_twitter_historical",
                "tags": ["Announcement", "Tickets", "None", "None", "None", "None"]
            }
        ]

        pass

    def get_jobs(self):
        jobs = map(lambda j: dict(j), self.jobs)
        return jobs

    def get_job_by_id(self, id):
        return dict(filter(lambda v: v['id'] == int(id), self.jobs)[0])
