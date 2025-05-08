package com.wydex.posterapp.service;

import com.wydex.posterapp.controller.PosterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public byte[] generatePoster(MultipartFile photoFile) throws IOException {
        logger.info("Starting poster generation for uploaded file: {}", photoFile.getOriginalFilename());

        BufferedImage template = ImageIO.read(
                getClass().getClassLoader().getResourceAsStream("static/blossom-event.jpg")
        );

        logger.debug("Reading user-uploaded image from input stream");

        BufferedImage userImage = ImageIO.read(photoFile.getInputStream());
        int targetWidth = 457;
        int targetHeight = 517;

        logger.debug("Resizing user image to {}x{}", targetWidth, targetHeight);

        Image scaled = userImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        int cornerRadius = 60;
        logger.debug("Applying rounded corners with radius: {}", cornerRadius);
        BufferedImage roundedUserImage = makeRoundedCornerImage(scaled, targetWidth, targetHeight, cornerRadius);

        Graphics2D g = template.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 578;
        int y = 882;
        logger.debug("Placing user image at coordinates ({}, {})", x, y);
        g.drawImage(roundedUserImage, x, y, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(template, "png", baos);
        logger.info("Poster generation complete for file: {}", photoFile.getOriginalFilename());
        return baos.toByteArray();
    }

    private BufferedImage makeRoundedCornerImage(Image image, int width, int height, int arcSize) {
        logger.debug("Creating rounded image of size {}x{} with arc {}", width, height, arcSize);
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new RoundRectangle2D.Float(0, 0, width, height, arcSize, arcSize));
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        return output;
    }

}
