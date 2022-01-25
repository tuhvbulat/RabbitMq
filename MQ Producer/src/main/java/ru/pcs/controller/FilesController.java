package ru.pcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pcs.model.User;
import ru.pcs.model.UserMap;
import ru.pcs.model.UserDto;
import ru.pcs.service.RabbitService;

@RestController
public class FilesController {

    @Autowired
    private final RabbitService rabbitService;

    public FilesController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> get(Long id) {
        return ResponseEntity.ok(rabbitService.getStatus(id).toString());
    }

    @GetMapping("/file")
    public ResponseEntity<?> sendFile(@RequestBody UserDto userDto) {
        UserMap user = UserMap.builder().firstName(userDto.getFirstName()).lastName(userDto.getLastName()).build();
        long status = rabbitService.putMessage(userDto.getType(), user);
        if (status != 0) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
