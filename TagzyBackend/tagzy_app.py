from flask import Flask, request
import json

from managers.records_manager import RecordsManager

app = Flask(__name__)

records_manager = RecordsManager()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/records', methods=['GET', 'POST'])
def get_records():
    if request.method == 'POST':
        return records_manager.update_records()
    else:
        records = records_manager.get_records()
        return json.dumps(records)


@app.route("/record", methods=['POST'])
def update_record():
    record = request.get_json(force=True)
    status = records_manager.update_record(record)
    return json.dumps(status)


if __name__ == '__main__':
    app.run(host='localhost', port=8080,debug=True)
