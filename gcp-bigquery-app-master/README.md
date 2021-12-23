# Invoke GCP BIG Query services from Application deployed in CCP

This is a working sample for building Java/J2EE   for building  spring Boot app  by consuming Google CLOUD Pub Sub Services through Open Service Broker API.

# pre-requisite/Dependency for Kubernets deployment

 To run this application successfully in in CCP Kubenets,  following action items should be completed before deploy the app as a pre-requisite

* on-premises environment, GCP Service Broker shoud be registered.
* on-premises, Service Instances and Service Binding should be done and it should be in ready state.
* Appliction Docker container should be pushed to Docker Hub or Google Container Registry.

# How to Build this application?
```bash
* Local Build  :  mvn clean package
* Docker Build :  docker build -t  hybrid-gcp-ccp-bigquery-app
```
# How to Deploy this application
```bash
* Standlone - native approach: java -jar target/hybrid-gcp-ccp-bigquery-app-0.1.0.jar
* Kubernets - hybrid through service broker: kubectl apply -f hybrid-bigquery-app-using-gcp-broker.yaml
```

# How to test this application
```bash
curl -d -H "Content-Type: application/json" -X GET http://localhost:8080/read-data-big-query/
```
Response
```bash
{"customerID":"ABC-1891","State_":"NY","gender":"M"," SeniorCitizen":0,"Partner":true,"Dependents":true}
```





# License
 [MIT](/LICENSE)
