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
                            {"mining-metadata.sources.thetrades-twitter-posts.query": "professionals"}
                        ]
                    }}
                    , {'$sample': {'size': 80}}
                    , {"$project": {
                        "_id": False,
                        "id": True,
                        "entities.urls": True,
                        "text": True,
                        "user.screen_name": True,
                        "extended_tweet.full_text": True,
                        "extended_tweet.entities.url": True,
                        "mining_metadata.predictions_probabilities.Ridgid Professionals Tweets": True
                    }}
                ],
                "collection": "thetrades_twitter_posts",
                "tags": ["Advertising", "Tips", "Career", "Product", "OnSite", "None"]
            }
        ]

        pass

    def get_jobs(self):
        jobs = map(lambda j: dict(j), self.jobs)
        return jobs

    def get_job_by_id(self, id):
        return dict(filter(lambda v: v['id'] == int(id), self.jobs)[0])
