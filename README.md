# Anubis Chat Application






Anubis Chat is a POC of a malicious chat application that is able to conduct malicious activities



## Features

- Read SMS and send to remote server
- Keylogger
- Extracting Device Info and Phone Number
- Extracting Geo Location



## Frameworks used



- [Python Flask] - Sending of data to remote server
- [Java] - Anubis Chat 
- [ngrok] - Enable sending of data through a public URL
- [Firebase] -  Authenticate users and store data related to the ap




## Installation

Anubis Chat  requires a minimum SDK of  21. This app has been tested on Pixel 4 and Pixel4a emulators.
For the sending of remote data to server, attacker machine has to be configured with ngrok.

To download and set up ngrok , visit the site here [https://ngrok.com/download]

Once ngrok has been set up, start the ngrok serer through the command
```cmd
ngrok http 5000
```
Note down the NGROK url given.

Download the zip file from GitHub
***Open the source code in an editor of our choice and amend the following***
--> Amend line 32 in KeyloggerUtility to your own ngrok url
--> Amend line 102 in MainActivity to your own ngrok url
--> Amend line 45 and 55 IN app.py to a diretory on ur host machine
After making the changes, run the app.py file
```cmd
python3 app.py
```
Recompile the app and generate an apk file.
Download the apk file and install it on the victim's phone.

Once the victim starts using the application, visit the interface of the ngrok URL to see the data and requests coming in. Alternatively, visit the directory that you have specified to see the text files stored locally with the data harvested from the victim's phone.





