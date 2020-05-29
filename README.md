## Requirements
- Maven
- Apache Tomcat
>_* A Dockerfile with Tomcat 9.0 is provided_

## Configuration

- Make sure the variables are correctly set in `Env.kt`
```kotlin
const val FROM = "sender@provider.com"
const val TO = "recipient@provider.com"
```
- Create an **SNS topic**
- Create a **Configuration Set** on Amazon SES, direct it to the SNS Topic and make sure the configuration set name is correct on `Env.kt`
```kotlin
const val CONFIGURATION_SET = "default"
```
- After the service is running, subscribe the SNS topic to your endpoint, or to a proxy endpoint
> I've used https://webhook.site/ and then reproduced the request locally using Postman

## Usage
- Build the WAR with `mvn clean package`
- If using Dockerfile:
```sh
docker build . -t aws-sns-sample
docker run -it -p 8080:8080 aws-sns-sample
```
- Verify the service is running:
```
GET request to http://localhost:8080/api/ping
```
- Send an email without special characters:
```
POST http://localhost:8080/api/send-without-special
```
- Send an email with special characters:
```
POST http://localhost:8080/api/send
```
- Send the SNS notification request to:
```
POST http://localhost:8080/api/handle
```
