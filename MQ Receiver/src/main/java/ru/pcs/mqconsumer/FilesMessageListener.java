package ru.pcs.mqconsumer;

import com.google.gson.Gson;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pcs.model.User;
import ru.pcs.model.UserMap;
import ru.pcs.repository.UserRepository;

import java.io.FileOutputStream;


@Component
public class FilesMessageListener {

    @Autowired
    private Gson gson;

    @Autowired
    private UserRepository userRepository;

    @RabbitListener(queues = "files.pdf", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        String toString = new String(message.getBody());
        UserMap userMap = gson.fromJson(toString, UserMap.class);
        try {

            User user = userRepository.getUserById(userMap.getId());
            user.setStatus(User.Status.IN_PORCCES);
            userRepository.save(user);
            createPdf(userMap, "Pdf");
            user.setStatus(User.Status.FINISH);
            userRepository.save(user);

        } catch (Exception e) {
            User user = userRepository.getUserById(userMap.getId());
            user.setStatus(User.Status.FAILED);
            userRepository.save(user);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @RabbitListener(queues = "files.png", containerFactory = "containerFactory")
    public void onFileMessage(Message message) {
        String toString = new String(message.getBody());
        UserMap userMap = gson.fromJson(toString, UserMap.class);
        try {

            User user = userRepository.getUserById(userMap.getId());
            user.setStatus(User.Status.IN_PORCCES);
            userRepository.save(user);
            createPdf(userMap, "Png");
            user.setStatus(User.Status.FINISH);
            userRepository.save(user);

        } catch (Exception e) {
            User user = userRepository.getUserById(userMap.getId());
            user.setStatus(User.Status.FAILED);
            userRepository.save(user);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    private void createPdf(UserMap userMap, String mainText) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(userMap.getId() + ".pdf"));
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph("My Name: " + userMap.getFirstName() + "\n", font);
        Paragraph paragraph2 = new Paragraph("My LastName: " + userMap.getLastName() + "\n", font);
        Paragraph paragraph3 = new Paragraph("My LastName: " + mainText + "\n", font);
        document.add(paragraph1);
        document.add(paragraph2);
        document.add(paragraph3);
        document.close();
    }
}
