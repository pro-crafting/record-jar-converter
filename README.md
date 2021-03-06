# record-jar-converter
![Build Status](https://travis-ci.org/Postremus/record-jar-converter.svg?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pro-crafting.tools/record-jar-converter/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pro-crafting.tools/record-jar-converter)
[![](https://images.microbadger.com/badges/image/postremus/record-jar-converter-web.svg)](https://microbadger.com/images/postremus/record-jar-converter-web "Get your own image badge on microbadger.com")
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Quarkus based microservice for converting record jar formatted text to json.
Currently, record-jar-draft-02 is supported, but not completely implemented. See the Label [draft02-compliance](https://github.com/pro-crafting/record-jar-converter/issues?q=is%3Aissue+is%3Aopen+label%3Adraft02-compliance) for missing bits.

## Installation
Use the official docker image:
postremus/record-jar-converter
Tags for this image correspond to the maven versions, e.g. the 1.0.0 labeled docker image contains the 1.0.0 maven artifact.

Run it via:
````
docker run -p 8080:8080 postremus/record-jar-converter:1.0.0
````

## Usage:
The rest api is documented using openapi at [REST api](https://record-jar-converter.pro-crafting.com/swagger-ui/).
The newest version of the REST api is always hosted at [record-jar-converter.pro-crafting.com](record-jar-converter.pro-crafting.com).

The following example is based upon "The Art of Unix Programming", and shows an call to the api, together with the result:
````
root@tm:~# curl -X POST --header 'Content-Type: text/plain' --header 'Accept: application/json' -d 'Planet: Mercury \
>  Orbital-Radius: 57,910,000 km \
>  Diameter: 4,880 km \
>  Mass: 3.30e23 kg' 'https://record-jar-converter.pro-crafting.com/v1/record/jar/text?encoding=UTF-8'
[{"Planet":"Mercury Orbital-Radius: 57,910,000 km Diameter: 4,880 km Mass: 3.30e23 kg"}]
````

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/Postremus/record-jar-converter/tags). 

## Authors

* **Martin Panzer** - *Initial work* - [Postremus](https://github.com/Postremus)

See also the list of [contributors](https://github.com/Postremus/record-jar-converter/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details