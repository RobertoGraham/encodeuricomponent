package io.github.robertograham.encodeuricomponent.encoder;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.net.URISyntaxException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EncoderTest {

    private Map<String, String> inputToExpectedResult = Stream.of(
            new SimpleEntry<>("hello java", "hello%20java"),
            new SimpleEntry<>("~`!@#Â£â‚¬$()*^&Â°%Â§Â¥Â¢?.,<>'\";:/|[]{}=+_- ", "~%60!%40%23%C3%82%C2%A3%C3%A2%E2%80%9A%C2%AC%24()*%5E%26%C3%82%C2%B0%25%C3%82%C2%A7%C3%82%C2%A5%C3%82%C2%A2%3F.%2C%3C%3E'%22%3B%3A%2F%7C%5B%5D%7B%7D%3D%2B_-%20"),
            new SimpleEntry<>("#this is a test", "%23this%20is%20a%20test"),
            new SimpleEntry<>("#foo bar$", "%23foo%20bar%24"),
            new SimpleEntry<>("test with different chars like Ã§ÅŸÃ¶Ã¼ÄŸÄ°Ä±", "test%20with%20different%20chars%20like%20%C3%83%C2%A7%C3%85%C5%B8%C3%83%C2%B6%C3%83%C2%BC%C3%84%C5%B8%C3%84%C2%B0%C3%84%C2%B1"),
            new SimpleEntry<>("$ %20F%20@", "%24%20%2520F%2520%40"),
            new SimpleEntry<>(" %20F %20 +", "%20%2520F%20%2520%20%2B"),
            new SimpleEntry<>("%40F%20F%10F", "%2540F%2520F%2510F"),
            new SimpleEntry<>(" + %20 + ", "%20%2B%20%2520%20%2B%20"),
            new SimpleEntry<>("%20 %20 %20 %20F", "%2520%20%2520%20%2520%20%2520F"),
            new SimpleEntry<>("'%20F'", "'%2520F'"),
            new SimpleEntry<>("@%40@", "%40%2540%40"),
            new SimpleEntry<>(" test ", "%20test%20"),
            new SimpleEntry<>("$#%23#$", "%24%23%2523%23%24"),
            new SimpleEntry<>("   %20   ", "%20%20%20%2520%20%20%20"),
            new SimpleEntry<>("^Ã¢%5E", "%5E%C3%83%C2%A2%255E"),
            new SimpleEntry<>("({foo &%26 bar})", "(%7Bfoo%20%26%2526%20bar%7D)"),
            new SimpleEntry<>("\uD800\uDFFF", "%CF%BF")
    )
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    private Set<String> errorInputs = Stream.of(
            "\uD800",
            "\uDFFF"
    )
            .collect(Collectors.toSet());

    @TestFactory
    Stream<DynamicTest> translateInputToExpectedResultMapToDynamicTestStream() {
        return inputToExpectedResult.keySet()
                .stream()
                .map(input ->
                        DynamicTest.dynamicTest("Test encodeUriComponent " + input, () -> assertEquals(inputToExpectedResult.get(input), Encoder.encodeUriComponent(input)))
                );
    }

    @TestFactory
    Stream<DynamicTest> translateErrorInputToExpectedMessageMapToDynamicTestStream() {
        return errorInputs.stream()
                .map(errorInput ->
                        DynamicTest.dynamicTest("Test encodeUriComponent throws URISyntaxException " + errorInput, () -> assertThrows(URISyntaxException.class, () -> Encoder.encodeUriComponent(errorInput)))
                );
    }

}
