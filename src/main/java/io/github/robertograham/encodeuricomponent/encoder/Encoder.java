package io.github.robertograham.encodeuricomponent.encoder;

import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class Encoder {

    private static final Set<Character> URI_UNESCAPED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()".chars()
            .mapToObj(i -> (char) i)
            .collect(Collectors.toSet());

    private Encoder() {

    }

    public static String encodeUriComponent(String input) throws URISyntaxException {
        int inputLength = input.length();

        int i = -1;

        StringBuilder result = new StringBuilder();

        while (++i < inputLength) {
            char charA = input.charAt(i);

            if (URI_UNESCAPED.contains(charA))
                result.append(charA);
            else {
                char charToEncode;

                if (charA >= 0xDC00 && charA <= 0xDFFF)
                    throw new URISyntaxException(input, "char >= 0xDC00 && char <= 0xDFFF", i);

                if (charA < 0xD800 || charA > 0xDBFF)
                    charToEncode = charA;
                else {
                    if (++i == inputLength)
                        throw new URISyntaxException(input, "index == input.length()", i);

                    char charB = input.charAt(i);

                    if (charB < 0xDC00 || charB > 0xDFFF)
                        throw new URISyntaxException(input, "char < 0xDC00 || char > 0xDFFF", i);

                    charToEncode = (char) ((charA - 0xD800) * 0x400 + charB - 0xDC00 + 0x10000);
                }

                for (byte octet : String.valueOf(charToEncode).getBytes(StandardCharsets.UTF_8)) {
                    int o = octet & 0xFF;

                    result.append("%");

                    if (o < 0x10)
                        result.append("0");

                    result.append(Integer.toString(o, 16).toUpperCase());
                }
            }
        }

        return result.toString();
    }
}
