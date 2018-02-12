from flask import Flask, request
import json
import os

from managers.jobs_manager import JobsManager
from managers.records_manager import RecordsManager
import argparse

app = Flask(__name__)

parser = argparse.ArgumentParser()
parser.add_argument("--production", default=False)

arguments = parser.parse_args()

production = arguments.production

print 'Argument production is:{}'.format(production)
print 'Argument production type: {}'.format(type(production))

if production:
    records_manager = RecordsManager('/tagzyservices/config.json')
else:
    # records_manager = RecordsManager('/Users/drbozdog/TagzyApp/TagzyBackend/config.json')
    records_manager = RecordsManager('/Users/drbozdog/Tagzy/TagzyBackend/config.json')
jobs_manager = JobsManager()


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/<jobid>/records/<limit>', methods=['GET'])
def get_records(jobid, limit):
    job = jobs_manager.get_job_by_id(jobid)
    collection = job['collection']
    query = job['query']
    records = records_manager.get_records(collection, query, limit)
    return json.dumps(records)


@app.route("/<jobid>/record", methods=['POST'])
def update_record(jobid):
    job = jobs_manager.get_job_by_id(jobid)
    collection = job['collection']
    name = job['name']
    record = request.get_json(force=True)
    status = records_manager.update_record(collection, record, name)
    return json.dumps(status)


@app.route('/<jobid>/stats', methods=['GET'])
def get_stats(jobid):
    job = jobs_manager.get_job_by_id(jobid)
    collection = job['collection']
    job_name = job['name']
    stats_query = job['stats_query']
    stats = records_manager.get_stats(collection, stats_query, job_name)
    return json.dumps(stats)


@app.route("/jobs", methods=['GET'])
def get_jobs():
    jobs = jobs_manager.get_jobs()
    return json.dumps(jobs)


if __name__ == '__main__':

    if production:
        app.run(host='0.0.0.0', port=8888, debug=False)
    else:
        app.run(host='0.0.0.0', port=16000, debug=True)
