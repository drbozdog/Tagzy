from repositories.jobs_repository import JobsRepository


class JobsManager:
    def __init__(self):
        self.jobs_repository = JobsRepository()

    def get_jobs(self):
        jobs = self.jobs_repository.get_jobs()
        for job in jobs:
            del job['query']
        return jobs

    def get_job_by_id(self, id):
        return self.jobs_repository.get_job_by_id(id)
