## A simple service to provide transfers between bank accounts

* I chose to use H2 as a embeded database.
* I used springboot framework.
* I also used lombok to make clean code.
* I used a @Synchronized in the transfer method to provide to simultaneous request a safety thread environment. In an enviroment with lots of requests and a high latency because the syncronized method, maybe an improvment to a service that run the transfers in background could be considered.

## Download dependencies
Just execute the command in the root.

    mvn clean install

## How to start
Just execute the command in the root. Use 8080 port to access.

    mvn spring-boot:run

## End-points

### Account

#### Creating a bank account

The example creates a new bank account and returns the created account id

    POST /account
    {
        "name": "John"
        "balance": "10.5"
    }

Response:

    {
        "id": 1
    }
    
#### Retrieving a bank account

The example retrieve a bank account by id

    GET /account/{accountId}
    
Response:

    {
        "id": 1,
        "name": "John"
        "balance": "10.5"
    }
    
#### Generating a transfer between accounts

The example generate a transfer between two accounts, the balance in the origin account need to be equal or higher than the transaction value

    POST /transfer
    {
        "origin": "1"
        "destiny": "2"
        "value": "7.5"
    }
    
Response:

    {
        "id": 1
    }
    
#### Retrieving a transfer between account

The example retrieve the register of a transfer between two accounts

    GET /transfer/{accountId}
    
Response:

    {
        "id": 1,
        "origin": "1"
        "destiny": "2"
        "value": "7.5"
    }