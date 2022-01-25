package ru.pcs.service;

import ru.pcs.model.User;
import ru.pcs.model.UserMap;

public interface RabbitService {
    public long putMessage(String type, UserMap user);
    public User.Status getStatus(long id);
}
