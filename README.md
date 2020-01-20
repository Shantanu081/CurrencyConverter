# Introduction

The application is developed using Spring boot. The CLI script is developed using node js. First build and run the jar, once the application is up then run the CLI script to test the REST application.
Inside CLI folder I have my payload, named as "payload.json". I have used the same payload as in the problem statement, if you want to try with a different payload please put the json array inside "payload.json". The output will be saved as "output.json" inside the CLI folder itself.

I have used "https://code.google.com/archive/p/currency-converter-api/" to convert currencies.


I have used the following malformed json payload :

[{ "item": "B", "price": 9.95, "currency": "USD" },
{ "item": "A", "price": 99.95, "currency": "JPY" },
{ "price": 10.99, "currency": "EUR" },
{ "item": "C", "currency": "EUR" },
{ "item": "D", "price": 10.99 },
{ "item": "F", "price": 10.99, "currency": "INVALID" }]

in the output.json file the output was :

[{"item":"B","price":9.02,"currency":"EUR"},{"item":"A","price":0.85,"currency":"EUR"}]

And on the jvm console it printed :

Invalid Item: [item= null, price= 10.99, currency= EUR]
Invalid Item: [item= C, price= null, currency= EUR]
Invalid Item: [item= D, price= 10.99, currency= null]
Invalid Item: [item= F, price= 10.99, currency= INVALID]

## Requirements
* Java 8
* Maven
* Node JS (For the CLI)

## How to build the application
Run the following command from the root folder :
```
    mvn clean install
```

## How to run the application
Run the created jar file :
```
    java -jar target/productcatalogue-0.0.1-SNAPSHOT.jar
```
The application runs on port 8090.


## How to use the CLI script
Run the following commands from the "CLI" folder :
```
    npm init
```
Press enter for every option in the prompt. I have kept everything default.

Then run :
```
    npm install minimist
```
Finally to hit the REST endpoint run ('--json' argument takes the payload.json path and for '--currency' argument pass the target currency) :
```
    node index.js --json ./payload.json --currency EUR
```

## Postman request and response

## Request
```
POST: http://localhost:8090/currencyconvertor/convertCurrency?currency=EUR  (EUR is being passed as a request parameter)

[{ "item": "B", "price": 9.95, "currency": "USD" },
{ "item": "A", "price": 99.95, "currency": "JPY" },
{ "item": "C", "price": 10.99, "currency": "EUR" }]
```
## Response

```
[
    {
        "item": "B",
        "price": 9.02,
        "currency": "EUR"
    },
    {
        "item": "A",
        "price": 0.85,
        "currency": "EUR"
    },
    {
        "item": "C",
        "price": 10.99,
        "currency": "EUR"
    }
]
```
