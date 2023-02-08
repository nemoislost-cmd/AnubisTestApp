from flask import Flask, request, jsonify, render_template

app = Flask(__name__)


@app.route("/device", methods=["POST"])
def device():
    device_info = request.get_json()
    

    Manufacturer = device_info['manufacturer']
    Model = device_info['model']
    verison = device_info['androidVersion']
    phoneno = device_info['phoneno']
   
   
    print("Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android verison is " +  verison + " and phone number is " + phoneno)
    result = "Manufacturer is " + Manufacturer + ", Phone Model is " +  Model +  " , Android verison is " +  verison + " and phone number is " + phoneno

    return result, 200
    
    

    # do something with the device information, for example, store it in a database


@app.route("/hello")
def hello():
    return "hello"

if __name__ == "__main__":
    app.run('0.0.0.0', debug=True)

    
