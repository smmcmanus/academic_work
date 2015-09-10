#!/usr/bin/python
import requests
import json
 
# Set the request parameters
##url = 'https://wusmdev.service-now.com/incident.do?JSONv2&sysparm_action=getRecords'
##url = 'https://wusmdev.service-now.com/u_citnet_device.do?JSONv2&sysparm_action=getRecords'
url = 'https://wusmdev.service-now.com/u_citnet_devicetype.do?JSONv2&sysparm_action=getRecords'

user = 'CITNETSVC'
pwd = 'Onomatopoeia#13'
 
# Set proper headers
headers = {"Content-Type":"application/json"}
 
# Do the HTTP request
response = requests.get(url, auth=(user, pwd), headers=headers )
 
# Check for HTTP codes other than 200
if response.status_code != 200: 
	print('Status:', response.status_code, 'Headers:', response.headers)
	print('Error Response:',response.json())
	exit()
 
# Decode the JSON response into a dictionary and use the data
print('Cookies', response.cookies)
print('Status:',response.status_code,'Headers:',response.headers)

##print('Response:',response.json())
parsed = json.loads(response.text)
print json.dumps(parsed, indent=4, sort_keys=True)


"""
            "u_asset_number": "4903",
            "u_device_type": "3aaef9c6f52f1100bfac59f6cff53614",
            "u_management_ip": "",
            "u_manufacturer": "cac64f5389a31100090da23bf5f0e4e0",
            "u_model_number": "AIR-CAP3602I",
            "u_name": "mswc-east-0u",
            "u_owner": "1da56d3cf5e31100bfac59f6cff536ee",
            "u_parent_device": "",
            "u_received_date": "",
            "u_serial_number": "FTX1643GJDB",
            "u_status": "Active",
            "u_telecom_room": ""
"""
