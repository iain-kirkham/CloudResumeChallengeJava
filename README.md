# Cloud Resume Challenge - Backend Java
https://lift.sonatype.com/api/badge/github.com/iain-kirkham/CloudResumeChallengeJava

![Cloud Resume Challenge Diagram](https://res.cloudinary.com/db7mbeieo/image/upload/v1683923642/Cloud-Resume-Challenge-Diagram_sr4sxp.webp)

## Description

This repo contains my [Cloud Resume Challenge](https://cloudresumechallenge.dev/docs/the-challenge/aws/) backend, this includes an AWS lambda function written in Java 11 and AWS CloudFormation template for all cloud resources.
It uses the Java 11 Lambda runtime, and DynamoDB API to read the number of visitors from the cloud resume DynamoDB table, then updates the table with the new visitor value, once updated the current visitor value is then returned through a JSON response. To ensure that the application works correctly before being deployed to AWS within the SAM build step it uses the written JUnit tests,
You may also find my infrastructure within the template.yaml which contains most of the infrastructure for the challenge in CloudFormation.

## Website

https://iainkirkham.dev

## Blog

https://iainkirkham.dev/blog/cloud-resume-challenge/

## Technologies used
- [AWS Java SDK V2](https://github.com/aws/aws-sdk-java-v2)
- [JUnit Testing](https://junit.org/junit4/)
- [Apache Maven](https://maven.apache.org/what-is-maven.html)
- [AWS SAM](https://aws.amazon.com/serverless/sam/)
- [AWS CloudFormation](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/Welcome.html)


## Frontend Repository

https://github.com/iain-kirkham/iain-kirkham-next