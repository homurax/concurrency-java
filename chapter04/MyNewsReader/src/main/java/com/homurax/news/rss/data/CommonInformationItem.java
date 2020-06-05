package com.homurax.news.rss.data;

import lombok.Data;

import java.io.StringWriter;
import java.util.Date;

@Data
public class CommonInformationItem {

    private String id;

    private String title;

    private String txtDate;

    private Date date;

    private String link;

    private StringBuffer description;

    private String source;

    public CommonInformationItem() {
        this.description = new StringBuffer();
    }



    public void addDescription(String txt) {
        description.append(txt);
    }

    public String toXML() {

        StringWriter writer = new StringWriter();

        writer.append("<item>\n");
        writer.append("<ide>\n");
        writer.append(id);
        writer.append("\n</id>\n");
        writer.append("\n<title>\n");
        writer.append(title);
        writer.append("\n</title>\n");
        writer.append("\n<date>\n");
        writer.append(txtDate);
        writer.append("\n</date>\n");
        writer.append("\n<link>\n");
        writer.append(link);
        writer.append("\n</link>\n");
        writer.append("\n<description>\n");
        writer.append(description);
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
