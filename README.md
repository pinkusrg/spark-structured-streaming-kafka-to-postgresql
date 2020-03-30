# spark-structured-streaming-with-kafka-and-postgresql

A basic template of integrating spark strucutured streaming with kafka and postgresql
## Table of contents  
1. [Getting Started](#Getting-Started)  
2. [Running](#Running)  
3. [Example](#Example-and-Results)  
  
## Getting Started  
#### Minimum requirements  
To run this example you will need  **Java 1.8+, Scala 2.11.12, SBT 1.2.8, spark 2.4.0**.   

## Running 

Before running make sure kafka and postgresql is running in your local
Or you can start fresh by following the below steps :
First go inside the postgres shell:
```
sudo -u postgres psql
```
Then, create a user and a database:

```
CREATE USER tom WITH ENCRYPTED PASSWORD 'password';
CREATE DATABASE pendulum WITH OWNER tom
```

A user named tom is created with password as password itself and a database named pendulum.

## Example and Results

lets produce some sample messages in the kafka topic say : bands
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic bands
>>{"id":4,"name":"The Beatles","hometown":"Liverpool","year":1960}
>>{"id":3,"name":"Metallica","hometown":"Los Angeles","year":1981}
>>{"id":0,"name":"Led Zeppelin","hometown":"London","year":1968}
```
Now, after running your application you should be able to see the data in your table accordingly, in our case it was the bands table:
<br>
![Output result](https://springflee.files.wordpress.com/2020/03/bands_sample.png?w=810)