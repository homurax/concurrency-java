package com.homurax.chapter04.reader.rss.data;

import lombok.Data;

import java.io.StringWriter;
import java.util.Date;

@Data
public class CommonInformationItem {

    private String title;

    private String txtDate;

    private Date date;

    private String link;

    private StringBuffer description;

    private String id;

    private String source;

    public CommonInformationItem() {
        this.description = new StringBuffer();
    }

    public void addDescription(String txt) {
        this.description.append(txt);
    }

    public String toXML() {

        StringWriter writer = new StringWriter();

        writer.append("<item>\n");
        writer.append("<id>\n");
        writer.append(this.id);
        writer.append("\n</id>\n");
        writer.append("\n<title>\n");
        writer.append(this.title);
        writer.append("\n</title>\n");
        writer.append("\n<date>\n");
        writer.append(this.txtDate);
        writer.append("\n</date>\n");
        writer.append("\n<link>\n");
        writer.append(this.link);
        writer.append("\n</link>\n");
        writer.append("\n<description>\n");
        writer.append(this.description);
        writer.append("\n</description>\n");
        writer.append("\n</item>\n");

        return writer.toString();
    }


    public String getFileName() {

        StringWriter writer = new StringWriter();
        writer.append(source);
        writer.append("_");
        writer.append(String.valueOf(description.hashCode()));
        writer.append(".xml");

        return writer.toString();
    }
}
