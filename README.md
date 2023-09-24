# drones dispatch

This is a project to apply for the rol of java developer.
The objective of this application is to provide a REST API
that allows to manage a drone fleet used to deliver medications.

For this purpose the application y comprised of a set of 
endpoints that allow the necessary functionalities.

**----------ENDPOINTS DESCRIPTION-------------**

**DRONES**

*GET*

api/v2/drones - Get all drones

api/v2/drones/{id} - Get drone by id

api/v2/drones/{id}/battery-level - Get battery level for a given drone

api/v2/drones/{id}/loaded-meds - Get meds loaded for a drone

api/v2/drones/idle - Get a list of available drones to load


*POST*

api/v2/drones/create - Register a new drone

*DELETE*

api/v2/drones/{id}/remove - Delete a drone

*PUT*

api/v2/drones/{id}/change-state/?state={state} - Change state for a given drone

possible drone states

(IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING)


**MEDICATIONS**

*GET*

api/v2/medications - Get all medications

api/v2/medications/{id} - Get medication by id

*POST*

api/v2/medications/create - add a new medication

*DELETE*

api/v2/medications/{id}/remove - Delete a medication


**PAYLOADS**
*GET*

api/v2/payloads - get all payloads

*POST*

api/v2/payloads/create - create a new payload

To test these endpoints please refer to this postman
collection, where all the parameters are set

https://drive.google.com/file/d/1LMwfgza3WVK0NihhFghYlPeQxnOnkm5U/view?usp=sharing



