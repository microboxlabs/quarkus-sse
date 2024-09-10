# quarkus-sse

Quarkus-SSE is a high-performance server using Quarkus, designed for real-time event streaming with Server-Sent Events (SSE). Its reactive architecture delivers fast, scalable solutions for cloud-native apps, microservices, and real-time notifications, optimizing development and resource use.

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://github.com/microboxlabs/quarkus-sse/workflows/CI/badge.svg)](https://github.com/microboxlabs/quarkus-sse/actions)

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [API Reference](#api-reference)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)

## Features

## Installation

To install Quarkus-SSE, follow these steps:

```bash
git clone https://github.com/microboxlabs/quarkus-sse.git
cd quarkus-sse
./mvnw install
```

## Usage


## Configuration

## API Reference

### Health and Liveness Endpoints

This Quarkus application includes the smallrye-health extension, which automatically exposes the following health-related endpoints:

- `/q/health/live`: Indicates if the application is up and running.
- `/q/health/ready`: Shows if the application is ready to serve requests.
- `/q/health/started`: Confirms if the application has started.
- `/q/health`: Aggregates all health check procedures in the application.

## Contributing

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```
You can then execute your native executable with: `./target/quarkus-sse-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements
