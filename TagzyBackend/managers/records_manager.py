from repositories.records_repository import RecordsRepository


class RecordsManager():
    def __init__(self, config_file):
        self.records_repository = RecordsRepository(config_file)

    def get_records(self, collection, query, limit):
        return self.records_repository.get_records(collection, query, limit)

    def update_record(self, collection, record, name):
        return self.records_repository.update_record(record, collection, name)

    def get_stats(self, collection, name):
        return self.records_repository.get_stats(collection, name)
