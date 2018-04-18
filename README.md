# encodeuricomponent
Java implementation of ECMAScript 2015 encodeUriComponent

## Install
```xml
<dependency>
    <groupId>io.github.robertograham</groupId>
    <artifactId>encodeuricomponent</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage
The following will print `%23this%20is%20a%20test`
```java
import io.github.robertograham.encodeuricomponent.encoder.Encoder;

import java.net.URISyntaxException;

public class Example {

    public static void main(String[] args) throws URISyntaxException {
        System.out.println(Encoder.encodeUriComponent("#this is a test"));
    }
}
```

## Tests
The project is covered by 20 JUnit 5 DynamicTests. Pull requests for additional DynamicTests are welcome!
