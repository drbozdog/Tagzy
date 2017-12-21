from repositories.records_repository import RecordsRepository


class RecordsManager():
    def __init__(self, config_file):
        self.records_repository = RecordsRepository(config_file)

    def get_records(self, limit):
        return self.records_repository.get_records(limit)

    def update_record(self, record):
        return self.records_repository.update_record(record)
