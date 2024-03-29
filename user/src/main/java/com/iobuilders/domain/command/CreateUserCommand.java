package com.iobuilders.domain.command;

import com.iobuilders.domain.bus.command.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateUserCommand implements Command {
    private final String id;
    private final String userName;
    private final String password;
    private final String name;
    private final String surname;
}
