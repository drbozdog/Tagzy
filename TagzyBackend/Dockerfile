FROM tiangolo/uwsgi-nginx-flask:python2.7

RUN pip install pymongo

RUN mkdir tagzyservices

COPY . /tagzyservices

EXPOSE 8888

WORKDIR tagzyservices

CMD python /tagzyservices/tagzy_app.py --production true