# iMedia24 Gmbh - Backend Coding Challenge.

This is a coding challenge for creating a Restful API to manage Product.

the technology being used is Spring boot 3, Kotlin and Gradle also Docker for containerization.

## Installation

for Gradle, it is not needed as we are going to use the wrapper which is already included in the repository.

1- Get the project to you local machine by pulling from this repository.

```bash
git pull https://github.com/ismailer7/imedia24-backend-challenge.git
```

2- Switch to directory ./imedia24-backend-challenge

```bash
cd ./imedia24-backend-challenge
```

3- Checkout develop branch.
```bash
git checkout develop
```

4- Build the project
```bash
./gradlew build
```

5- After the build is finish a jar is created inside ./build/libs/imedia24-backend-challenge.jar
so let's create an image for this app for local environment.

```bash
docker build --build-arg ENVIRONMENT=local -t imedia24-backend-challenge .
```

6- let tag the image.
```bash
docker tag imedia24-backend-challenge imedia24-backend-challenge:1.0.1
```

7- Run the image
```bash
docker container run -dp 8080:8080 -t imedia24-backend-challenge:1.0.1
```

8- Finally the app should be up and running at
```bash
http://localhost:8080/api/v1/
```

9- checkout the api at 
```bash
http://localhost:8080/api/v1/
```