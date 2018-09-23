# Java/Scala Test

Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts.

Explicit requirements:
1. keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is invoked by another internal system/service) 
1. use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the requirement #1 
1. the datastore should run in-memory for the sake of this test 
1. the final result should be executable as a standalone program (should not require a pre-installed container/server) 
1. demonstrate with tests that the API works as expected

Implicit requirements:
1. the code produced by you is expected to be of high quality. 
1. there are no detailed requirements, use common sense.
Please put your work on github or bitbucket.

Please put your work on github or bitbucket.

# Test application
build project:
```
mvn clean package -DskipTests
```

# Run application
```
cd target
```
then:
```
java -jar money-transfer-app-1.0-SNAPSHOT-shaded.jar
```
check running application at localhost:8080, where you can see the information page

# Test application
adds first user:
```
curl -k --header "Content-Type: application/json" --request POST --data '{"firstName":"Vasiliy", "middleName": "Vasilievich", "lastName": "Vasiliev"}' http://localhost:8080/api/customers
```
output:
```
{
  "id" : 1,
  "creationDate" : 1537723463856,
  "modificationDate" : 1537723463856,
  "status" : "ACTIVE",
  "firstName" : "Vasiliy",
  "middleName" : "Vasilievich",
  "lastName" : "Vasiliev",
  "fullName" : "Vasiliy Vasilievich Vasiliev",
  "birthDate" : 1537723463857
}
```
adds second user:
```
curl -k --header "Content-Type: application/json" --request POST --data '{"firstName":"Petr", "middleName": "Petrovich", "lastName": "Petrov"}' http://localhost:8080/api/customers
```
output:
```
{
  "id" : 2,
  "creationDate" : 1537723465846,
  "modificationDate" : 1537723465846,
  "status" : "ACTIVE",
  "firstName" : "Petr",
  "middleName" : "Petrovich",
  "lastName" : "Petrov",
  "fullName" : "Petr Petrovich Petrov",
  "birthDate" : 1537723465846
}
```
adds account to first user:
```
curl -k --header "Content-Type: application/json" --request POST --data '{"customerId": "1"}' http://localhost:8080/api/accounts
```
output:
```
{
  "id" : 1,
  "creationDate" : 1537723466064,
  "modificationDate" : 1537723466064,
  "status" : "ACTIVE",
  "owner" : {
    "id" : 1,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "firstName" : "Vasiliy",
    "middleName" : "Vasilievich",
    "lastName" : "Vasiliev",
    "fullName" : "Vasiliy Vasilievich Vasiliev",
    "birthDate" : 1537650000000
  },
  "balance" : 10,
  "reserved" : null
}
```
adds account to second user:
```
curl -k --header "Content-Type: application/json" --request POST --data '{"customerId": "2"}' http://localhost:8080/api/accounts
```
sample output:
```
{
  "id" : 2,
  "creationDate" : 1537723466390,
  "modificationDate" : 1537723466390,
  "status" : "ACTIVE",
  "owner" : {
    "id" : 2,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "firstName" : "Petr",
    "middleName" : "Petrovich",
    "lastName" : "Petrov",
    "fullName" : "Petr Petrovich Petrov",
    "birthDate" : 1537650000000
  },
  "balance" : 10,
  "reserved" : null
}
```
executes transfer creation:
 ```
curl -k --header "Content-Type: application/json" --request POST --data '{"operation": "create_transfer", "donorAccount": "1", "recipientAccount": "2", "amount": "1"}' http://localhost:8080/api/transactions
```
sample output:
```
[ {
  "id" : 1,
  "creationDate" : 1537723466785,
  "modificationDate" : 1537723466785,
  "transactionId" : "b0776049-88d3-47b2-bd26-e5a8d95384b8",
  "state" : "PENDING",
  "type" : "OUTCOME",
  "amount" : 1,
  "account" : {
    "id" : 1,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 1,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Vasiliy",
      "middleName" : "Vasilievich",
      "lastName" : "Vasiliev",
      "fullName" : "Vasiliy Vasilievich Vasiliev",
      "birthDate" : 1537650000000
    },
    "balance" : 9.00,
    "reserved" : 1
  }
}, {
  "id" : 2,
  "creationDate" : 1537723466785,
  "modificationDate" : 1537723466785,
  "transactionId" : "4573992a-9ba1-4d0b-bb71-a80f39c63b24",
  "state" : "NEW",
  "type" : "INCOME",
  "amount" : 1,
  "account" : {
    "id" : 2,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 2,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Petr",
      "middleName" : "Petrovich",
      "lastName" : "Petrov",
      "fullName" : "Petr Petrovich Petrov",
      "birthDate" : 1537650000000
    },
    "balance" : 10.00,
    "reserved" : null
  }
} ]
```
executes acceptation of outcome operation(notice that you should specify correct operationUUID):
```
curl -k --header "Content-Type: application/json" --request POST --data '{"operation": "accept_outcome", "operationUUID": "b0776049-88d3-47b2-bd26-e5a8d95384b8"}' http://localhost:8080/api/transactions
```
sample output:
```[ {
  "id" : 1,
  "creationDate" : 1537650000000,
  "modificationDate" : 1537723505057,
  "transactionId" : "b0776049-88d3-47b2-bd26-e5a8d95384b8",
  "state" : "ACCEPTED",
  "type" : "OUTCOME",
  "amount" : 1.00,
  "account" : {
    "id" : 1,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537723505057,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 1,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Vasiliy",
      "middleName" : "Vasilievich",
      "lastName" : "Vasiliev",
      "fullName" : "Vasiliy Vasilievich Vasiliev",
      "birthDate" : 1537650000000
    },
    "balance" : 9.00,
    "reserved" : 0.00
  }
}, {
  "id" : 2,
  "creationDate" : 1537650000000,
  "modificationDate" : 1537723505300,
  "transactionId" : "b8973eb9-0ae5-44cd-bedc-202a5e26116f",
  "state" : "PENDING",
  "type" : "INCOME",
  "amount" : 1.00,
  "account" : {
    "id" : 2,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 2,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Petr",
      "middleName" : "Petrovich",
      "lastName" : "Petrov",
      "fullName" : "Petr Petrovich Petrov",
      "birthDate" : 1537650000000
    },
    "balance" : 10.00,
    "reserved" : null
  }
} ]
```
executes acceptation of income operation(notice that you should specify correct operationUUID):
```
curl -k --header "Content-Type: application/json" --request POST --data '{"operation": "accept_income", "operationUUID": "4573992a-9ba1-4d0b-bb71-a80f39c63b24"}' http://localhost:8080/api/transactions
```
sample output:
```
[ {
  "id" : 1,
  "creationDate" : 1537650000000,
  "modificationDate" : 1537723568113,
  "transactionId" : "b0776049-88d3-47b2-bd26-e5a8d95384b8",
  "state" : "COMPLETED",
  "type" : "OUTCOME",
  "amount" : 1.00,
  "account" : {
    "id" : 1,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537650000000,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 1,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Vasiliy",
      "middleName" : "Vasilievich",
      "lastName" : "Vasiliev",
      "fullName" : "Vasiliy Vasilievich Vasiliev",
      "birthDate" : 1537650000000
    },
    "balance" : 9.00,
    "reserved" : 0.00
  }
}, {
  "id" : 2,
  "creationDate" : 1537650000000,
  "modificationDate" : 1537723568113,
  "transactionId" : "4573992a-9ba1-4d0b-bb71-a80f39c63b24",
  "state" : "COMPLETED",
  "type" : "INCOME",
  "amount" : 1.00,
  "account" : {
    "id" : 2,
    "creationDate" : 1537650000000,
    "modificationDate" : 1537723568113,
    "status" : "ACTIVE",
    "owner" : {
      "id" : 2,
      "creationDate" : 1537650000000,
      "modificationDate" : 1537650000000,
      "status" : "ACTIVE",
      "firstName" : "Petr",
      "middleName" : "Petrovich",
      "lastName" : "Petrov",
      "fullName" : "Petr Petrovich Petrov",
      "birthDate" : 1537650000000
    },
    "balance" : 11.00,
    "reserved" : null
  }
} ]
```

