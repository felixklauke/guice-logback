# Logback Custom Injection for Google Guice

Ever tried dependency injection while you still want class specific logging? If you
don't want to break the sense of dependency injection you shouldn't use a static
factory in each class. Google Guice even supports logger injection but sadly only
for the default java.util.Logger. Be serious: Who really uses this in production
on enterprise level? We wan't custom injection of our own logging framework.
In this case we will use logback as our logging framework.

# Usage
- Install [Maven](http://maven.apache.org/download.cgi)
- Clone this repo
- Install: ```mvn clean install```

**Maven dependencies**

```xml
<dependency>
    <groupId>de.felix-klauke</groupId>
    <artifactId>google-guice-logback</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>compile</scope>
</dependency>
```

# Example