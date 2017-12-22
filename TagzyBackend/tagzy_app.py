from flask import Flask, request
import json
import os

from managers.records_manager import RecordsManager

app = Flask(__name__)

records_manager = RecordsManager('/Users/drbozdog/Tagzy/TagzyBackend/config.json')


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/records/<limit>', methods=['GET'])
def get_records(limit):
    records = records_manager.get_records(limit)
    return json.dumps(records)


@app.route("/record", methods=['POST'])
def update_record():
    record = request.get_json(force=True)
    status = records_manager.update_record(record)
    return json.dumps(status)


@app.route('/stats', methods=['GET'])
def get_stats():
    stats = records_manager.get_stats()
    return json.dumps(stats)


if __name__ == '__main__':
    app.run(host='localhost', port=8888, debug=True)
