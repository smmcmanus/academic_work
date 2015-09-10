#!/usr/bin/python
import requests
import json
import csv
import sys
import MySQLdb

def output():
	#create CSV file from JSON record
	name = 'output.csv'
	try:
		file = open(name, 'a') 
		file.close
	except:
		print('Error')
	with open(name,'wb') as f:
		dict_writer = csv.DictWriter(f, keys)                
		dict_writer.writerows(data)
def sql():
	db = MySQLdb.connect(host="localhost",
						 user="msns", 
						  passwd="#WashUBears#", 
						  db="NetworkCMDB_Test",
						  local_infile = 1)
	cursor = db.cursor()
	for line in open('create_' + urlish + '_table.sql'):
		cursor.execute(line)
	db.commit()
	db.close()
def access():
	#Fetch JSON file from ServiceNow
	url = 'https://wusmdev.service-now.com/u_citnet_' + urlish + '.do?JSONv2&sysparm_action=getRecords'
	user = 'CITNETSVC'
	pwd = 'Onomatopoeia#13'
 
	# Set proper headers
	headers = {"Content-Type":"application/json"}
 
	# Do the HTTP request
	response = requests.get(url, auth=(user, pwd), headers=headers )

	# Check for HTTP codes other than 200
	if response.status_code == 200:
	  try:
		predata = json.loads(response.text)
	  except ValueError:
		print("shoot")
	else: 
		print('Status:', response.status_code, 'Headers:', response.headers)
		print('Error Response:',response.json())
		exit()
		
	global data
	#Remove highest index to make JSON readable into CSV
	data = predata['records']
	
urlish = 'bldgcode'
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_bldg_code', 'u_bldg_name', 'u_short_description', 'u_wusm_campus'] 
access()
output()
sql()

urlish = 'connectiontype'
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_bill_description', 'u_charge_fte', 'u_charge_nonfte', 'u_connection_type_code', 'u_short_description']
access()
output()
sql()

urlish = 'device'	 
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_asset_number', 'u_device_type', 'u_management_ip', 'u_manufacturer', 'u_model_number', 'u_name', 'u_owner', 'u_parent_device', 'u_received_date', 'u_serial_number', 'u_status', 'u_telecom_room'] 
access()
output()
sql()

urlish = 'devicetype'
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_alpha', 'u_mscns_equip_type_code', 'u_short_description']
access()
output()
sql()

urlish = 'lines'
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_active', 'u_activeonbillchkdt', 'u_billingaccount', 'u_building', 'u_buildingcode', 'u_cableroute', 'u_comments', 'u_connectiontype', 'u_contact', 'u_date_active', 'u_date_inactive', 'u_descriptor', 'u_device', 'u_floor', 'u_linecharge', 'u_mediatype', 'u_miscinfo', 'u_otherunumber', 'u_override_linecharge', 'u_owner', 'u_port', 'u_room', 'u_unumber'] 
access()
urlish = 'linesid'
output()
sql()

urlish = 'manufacturer'	
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_manufacturer_code', 'u_name'] 
access()
output()
sql()

urlish = 'mediatype'	
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_media_type_code', 'u_short_description'] 
access()
output()
sql()

urlish = 'owner'	
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_name'] 
access()
output()
sql()

urlish = 'ports'
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_description', 'u_device_id', 'u_idle_since', 'u_interface', 'u_mac', 'u_name', 'u_neighbor', 'u_speed', 'u_status', 'u_transform_status', 'u_vlan'] 
access()
output()
sql()

urlish = 'telecom_room'	
keys = ['__status', 'sys_created_by', 'sys_created_on', 'sys_id', 'sys_mod_count', 'sys_updated_by', 'sys_updated_on', 'u_bldg_code', 'u_building', 'u_comments', 'u_floor', 'u_name', 'u_room', 'u_room_key', 'u_tr'] 
access()
output()
sql()

