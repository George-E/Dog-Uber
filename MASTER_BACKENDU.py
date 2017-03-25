import json
import mysql
from flask import Flask, abort, request
import mysql.connector

# SQL stuff ======================

DB_NAME = 'poober'

config = {
  'user': 'sec_user',
  'password': '___________',
  'host': 'localhost',
  'database': DB_NAME,
  'raise_on_warnings': True,
}

addpoo_format = ("INSERT INTO poop "
               "(time, latitude, longitude, description, price, picture) "
               "VALUES (%s, %s, %s, %s, %s, %s)")

deletepoo_format = ("DELETE FROM poop WHERE time = %s")

addimg_format = ("UPDATE poop SET picture = %s WHERE time = %s")

master_format = ("SELECT time, latitude, longitude, description, price, picture FROM poop")

def execute_on_cnx(query, data=0):
	cnx = mysql.connector.connect(**config)
	cursor = cnx.cursor()
	if not data:
		cursor.execute(query)
	else: 
		cursor.execute(query, data) 
	cnx.commit()
	cursor.close()
	cnx.close()

# =================================

app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'this is trash'

@app.route('/addpoo', methods=['POST'])
def addpoo():
	if not request.get_json():
		abort(400)
	poo_string = json.dumps(request.get_json())
	poo_obj = json.loads(poo_string)
	poo_data = (poo_obj['time'], poo_obj['latitude'], poo_obj['longitude'], poo_obj['description'], poo_obj['price'], poo_obj['picture'])
	
	execute_on_cnx(addpoo_format, poo_data)

	return poo_string

@app.route('/deletepoo', methods=['POST'])
def deletepoo():
	if not request.get_json():
		abort(400)
	poo_string = json.dumps(request.get_json())
	poo_obj = json.loads(poo_string)
	poo_data = deletepoo_format % poo_obj['time']
	
	execute_on_cnx(poo_data)

	return poo_string

@app.route('/addpic', methods=['POST'])
def changepicture():
	if not request.get_json():
		abort(400)
	poo_string = json.dumps(request.get_json())
	poo_obj = json.loads(poo_string)
	poo_data = (poo_obj['picture'], poo_obj['time'])

	execute_on_cnx(addimg_format, poo_data)

	return poo_string


@app.route('/poomaster', methods=['GET'])
def poomaster():

	json_data = {}
	count = 0
	cnx = mysql.connector.connect(**config)
	cursor = cnx.cursor()
	cursor.execute(master_format)

	for (time, latitude, longitude, description, price, picture) in cursor:
	 	row_dict = {}
	 	row_dict['time'] = time
	 	row_dict['latitude'] = latitude
	 	row_dict['longitude'] = longitude
	 	row_dict['description'] = description
	 	row_dict['price'] = price
	 	row_dict['picture'] = picture
	 	json_data[str(count)] = json.dumps(row_dict)
		count += 1

	return json.dumps(json_data)
