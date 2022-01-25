package ru.pcs.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pcs.model.User;
import ru.pcs.model.UserMap;
import ru.pcs.repository.UserRepository;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitServiceImpl implements RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public UserRepository userRepository;

    private final static String REGISTER_PDF = "files.pdf";
    private final static String SING_PDF = "files.png";

    @Override
    public long putMessage(String type, UserMap userMap) {
        switch (type) {
            case "files.pdf":
                type = REGISTER_PDF;
                break;
            default:
                type = SING_PDF;
                break;
        }

        User user = User.builder().status(User.Status.WAIT).build();
        userRepository.save(user);
        userMap.setId(user.getId());

        rabbitTemplate.send("files_topic_exchange", type, new Message(userMap.toString().getBytes(StandardCharsets.UTF_8)));
        return userMap.getId();
    }

    @Override
    public User.Status getStatus(long id) {
        return userRepository.getById(id).getStatus();
    }
}
