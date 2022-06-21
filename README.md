# Trip Simulator

Trip simulator, as part of the Oracle SCM - IoT division creates instances in the server instance and simulates a given number of trips with random sources, destinations and stops for a real UX. 

## Getting started

### Dependencies

1. HTTPClient (v4.5.13)
2. HTTPCore (v4.4.15)
3. Commons-logging (v1.2)
4. Jackson-Core (v2.13.3)
5. Jackson-databind (v0.4.24)
6. Jackson-annotations (v2.13.3)
7. Annotations (v20.1.0)
8. Testng (v7.6.0)
9. Jcommander (v1.48)
10. Slf4j-api (v1.7.36)
11. Slf4j-simple (v1.7.36)

All dependencies can be installed manually from [maven repository](https://mvnrepository.com/) or using the following xml file for 

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient -->
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpclient</artifactId>
        <version>4.5.13</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.apache.httpcomponents/httpcore -->
    <dependency>
        <groupId>org.apache.httpcomponents</groupId>
        <artifactId>httpcore</artifactId>
        <version>4.4.15</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/commons-logging/commons-logging -->
    <dependency>
        <groupId>commons-logging</groupId>
        <artifactId>commons-logging</artifactId>
        <version>1.2</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.13.3</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.3</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.13.3</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.jetbrains/annotations -->
    <dependency>
        <groupId>org.jetbrains</groupId>
        <artifactId>annotations</artifactId>
        <version>20.1.0</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.testng/testng -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.6.0</version>
        <scope>test</scope>
    </dependency>

    <!-- https://mvnrepository.com/artifact/com.beust/jcommander -->
    <dependency>
        <groupId>com.beust</groupId>
        <artifactId>jcommander</artifactId>
        <version>1.48</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.36</version>
    </dependency>

    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-simple -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.36</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Data simulation.models

### 1. Trip

### 2. Vehicles

### 3. Vehicle type

### 4. Users

### 5. Connectors

### 6. Device

### 6. Organizations

## API Clients

1. IoT server instance

API calls are made to the configured server instance to setup and start the trip simulations with the given number of instances

2. HERE Maps

API Calls are made to the [routing](https://developer.here.com/documentation/routing-api/dev_guide/index.html) and the [reverse geolocation](https://developer.here.com/documentation/geocoder/dev_guide/topics/resource-reverse-geocode.html) sections of the HERE maps API

## TODO

1. 
