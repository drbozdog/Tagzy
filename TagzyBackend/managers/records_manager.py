from repositories.records_repository import RecordsRepository


class RecordsManager():
    def __init__(self):
        self.records_repository = RecordsRepository()

    def get_records(self):
        return self.records_repository.get_records()

    def update_record(self, record):
        return self.records_repository.update_record(record)
