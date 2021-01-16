# phone-services

Phone Service stores the phone contacts and also get phone contact details using name. It supports basic CURD operations.
Please follow the below steps once you clone the repository.

step 1:  mvn clean install \n
step 2: java -jar target\phone-service-0.0.1-SNAPSHOT.jar \n

#CURL Scripts:
================================================================================================
#POST CURL Script

POST /v1/phone HTTP/1.1
Host: localhost:9080
Content-Type: application/json
Cache-Control: no-cache
Postman-Token: 9da82d7e-d5d8-2383-9bc8-6c71eb5aa9f3

{
	"name":"siva",
	"mobileNumber":"1312321321",
	"workMobileNumber":"1312321321",
	"emailId" :"test@test.com"
	
}
==================================================================================================

#GET CURL Script
GET /v1/phone/siva HTTP/1.1
Host: localhost:9080
Cache-Control: no-cache




