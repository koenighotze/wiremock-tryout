# Wiremock Examples

This repository explores the features of [Wiremock](http://wiremock.org).

It uses [Spring-Boot](http://projects.spring.io/spring-boot/) for exposing and consuming a simple REST service.

## The server

A simple REST endpoint is implemented in `org.koenighotze.wiremocktryout.service.SampleController`.
It exposes `Sample`s at [http://localhost:8080/sample/]. Otherwise nothing special is happening of note.
The server is started by running `org.koenighotze.wiremocktryout.service.SampleApplication`.

## The client

Also a simple REST client (org.koenighotze.wiremocktryout.client.SampleControllerClient) is implemented.
The client can be run by starting `org.koenighotze.wiremocktryout.client.SampleControllerClientApplication`.
It tries to consume [http://localhost:8080/sample/] via GET.

The client is the actual target for the tests using Wiremock.

## Wiremock


### Scenarios

* Stubbing
** Handling 5xx codes
** Handling 4xx codes
 
* Faults
** Request Timeouts
** Socket Timeout
** Bad response
 




## Bucket list

- [ ] Try out standalone mode
- [ ] Try record and playback
- [ ] Using it in unittests