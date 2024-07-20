# E-Commerce

This application consists of the following microservices:

### API Gateway:

- Serves as the entry point for all requests.
- Manages authentication, token transformation and can also serve rate-limiting.
- All URLs must route through API Gateway, which is running on port 8080.

### Auth Service:

- Handles user registration, login, and token validation.
- Implemented JWT for Authentication and Authorization.
- Running on port 8081.

### Order Service:

- Manages order processing.
- Running on port 8082.

### Product Service:

- Manages product information, utilizing optimistic locking for updates.
- Running on port 8083.

### Commons:

- Contains common files shared across other services.

## How To Build:

Use `.\gradlew clean build` to build the project.

## Dockerize Application

use `docker-compose up` to dockerize whole application, you can also use `.\run.sh` to build and dockerize.

## Running Locally

- Run each service individually or use `docker-compose up` to start all services.

## Creating a Cluster:

- The Terraform `main.tf` script can be used to apply network changes using below cmds-

```
terraform init
terrform plan
terraform apply
```

## High Availability & AutoScaling

I configured GCP to deploy services to Cloud Run in a multi-node cluster for higher availability. 
The minimum and maximum instances for Cloud Run are set to ensure optimal performance and cost efficiency. 
During periods of high traffic, Cloud Run's auto-scaling feature will be triggered to handle the increased load.


## CI/CD:

- The `build_and_deploy.yml` file is available for building and deploying services to Google Cloud Run.

## Using Atlantis for Network Changes:

- Use Atlantis to modify minimum or maximum instances or other network attributes, preferably after a PR is approved.
    - Run `atlantis plan` to review the changes. If they look good, apply them with `atlantis apply`.
    - If the changes are not acceptable, release the lock using `atlantis unlock`.

## H2 Console

I am using an H2 in-memory database in this project. 
Please note that data will be flushed upon restart. 
However, I have included a `data.sql` file to populate initial data, which can be modified as needed.

The H2 console is excluded from security and can be accessed using the following URLs:

```
http://localhost:8080/auth/h2-console
http://localhost:8080/order/h2-console
http://localhost:8080/product/h2-console
```

## Swagger- Api Documentation

Similarly, Swagger is also excluded from security and can be accessed using the following URLs:

```
http://localhost:8080/auth/swagger-ui/index.html
http://localhost:8080/order/swagger-ui/index.html
http://localhost:8080/product/swagger-ui/index.html
```

## Postman Collection

Postman collections for APIs and the environment are available in the `postman folder` for quick testing. 
I have tested all APIs locally and with Docker, and they are working as expected. (✅)
