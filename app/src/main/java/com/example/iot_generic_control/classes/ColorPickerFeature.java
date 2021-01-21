package com.example.iot_generic_control.classes;

public class ColorPickerFeature extends BaseFeature {
    String color_system;
    String separator;
    String prefix;
    String suffix;

    public String getColor_system() {return color_system;}

    public void setColor_system(String color_system) {this.color_system = color_system;}

    public String getSeparator() {return separator;}

    public void setSeparator(String separator) {this.separator = separator;}

    public String getPrefix() {return prefix;}

    public void setPrefix(String prefix) {this.prefix = prefix;}

    public String getSuffix() {return suffix;}

    public void setSuffix(String suffix) {this.suffix = suffix;}

    public ColorPickerFeature(String name, String topic, int id, int device_id, String type, String color_system, String separator, String prefix, String suffix) {
        super(name, topic, id, device_id, type);
        this.color_system = color_system;
        this.separator = separator;
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
