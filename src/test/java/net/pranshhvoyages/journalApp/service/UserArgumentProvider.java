package net.pranshhvoyages.journalApp.service;

import net.pranshhvoyages.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(User.builder().userName("utpal").password("utpal").build()),
                Arguments.of(User.builder().userName("keshav").password("keshav").build())
        );

    }
}
