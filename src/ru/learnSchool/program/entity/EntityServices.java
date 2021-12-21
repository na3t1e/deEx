package ru.learnSchool.program.entity;


import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

@Data
public class EntityServices {
    private int id;
    private String title;
    private Double cost;
    private int duration;
    private String unit;
    private Double discount;
    private String description;
    private String path;
    private ImageIcon image;

    /**
     * функция для создания новой сущности с изображением
     * @param id
     * @param title
     * @param cost
     * @param duration
     * @param unit
     * @param discount
     * @param description
     * @param path
     */
    public EntityServices(int id, String title, Double cost, int duration, String unit, Double discount, String description, String path) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.duration = duration;
        this.unit = unit;
        this.discount = discount;
        this.description = description;
        this.path = path;
        try {
            image = new ImageIcon(
                    ImageIO.read(EntityServices.class.getClassLoader().getResource(path))
                            .getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
        }
    }

    public EntityServices(String title, Double cost, int duration, String unit, Double discount, String description, String path) {
        this(-1, title, cost, duration, unit, discount, description, path);
    }

}
