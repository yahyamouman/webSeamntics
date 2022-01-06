# webSemantics

To run you need to have a running instance of the Fuseki jena server and create a database called /territoire

![image](https://user-images.githubusercontent.com/56233662/148463000-59d6f208-ca9f-4e0e-a0f5-b217e4103483.png)

- Browse ontology permits us to navigate the triplestore in a linked tree manner as follows
![image](https://user-images.githubusercontent.com/56233662/148463080-8033996a-0689-49eb-887f-315ac6cd091e.png)

- Upload any RDF type data to Triplestore with jena fuseki
![image](https://user-images.githubusercontent.com/56233662/148463248-d646e1c5-c2d3-464d-8704-d7f2ee032a3c.png)

- Upload csv sensor measurements to automatically add them to triple store as well as web scrapped complementary outside measurements.


- SPARQL Query to select all rooms with different temperature than outside (10 C in difference) :
```
PREFIX sosa: <http://www.w3.org/ns/sosa/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
PREFIX bot: <https://w3id.org/bot#>
PREFIX seas: <https://w3id.org/seas/>

SELECT ?room ?time
WHERE {

  ?room a bot:Space.
  ?room bot:containsElement ?a.
  ?a a sosa:Sensor.  
  ?a sosa:observes ?p.
  ?p a seas:TemperatureProperty.
  ?o sosa:hasResult ?r.
  ?o sosa:resultTime ?time.
  ?m a sosa:observation.
  ?m sosa:observedProperty <https://territoire.emse.fr/kg/emse/fayol/#outside-temperature>.
  ?m sosa:hasResult ?rOutside.
  ?m sosa:resultTime ?time.
  FILTER(?r -?rOutside>10 || ?r -?rOutside<-10)
}
```

Script to download territoire rdf files
```
wget -r -np -k https://territoire.emse.fr/kg/
find . -type f -regex '.html.' -exec rm {} \;
```
