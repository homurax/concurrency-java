package com.homurax.news.rss.data;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RSSDataCapturer extends DefaultHandler {

    private final int IN_TITLE = 1;
    private final int IN_LINK = 2;
    private final int IN_DESCRIPTION = 3;
    private final int IN_PUBDATE = 4;
    private final int IN_GUID = 5;

    private int status = 0;
    private CommonInformationItem item;
    private List<CommonInformationItem> list;
    private final SimpleDateFormat formatter;
    private StringBuffer buffer;
    private final String name;

    public RSSDataCapturer(String name) {
        formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        buffer = new StringBuffer();
        this.name = name;
    }


    public List<CommonInformationItem> load(String resource) {

        list = new ArrayList<>();
        SAXParserFactory spf = SAXParserFactory.newInstance();

        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(resource, this);
        } catch (SAXException | ParserConfigurationException | IOException se) {
            System.err.printf("%s\n", resource);
            se.printStackTrace();
        }
        return list;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String txt = new String(ch, start, length);
        buffer.append(txt.trim());
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if ((item != null) && (qName.equalsIgnoreCase("title"))) {
            item.setTitle(buffer.toString());
            status = 0;

        }
        if ((item != null) && (qName.equalsIgnoreCase("link"))) {
            item.setLink(buffer.toString());
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("description"))) {
            item.addDescription(buffer.toString());
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("pubdate"))) {
            item.setTxtDate(buffer.toString());
            try {
                item.setDate(formatter.parse(buffer.toString()));
            } catch (ParseException e) {
                item.setDate(new Date());
            }
            status = 0;
        }
        if ((item != null) && (qName.equalsIgnoreCase("guid"))) {
            item.setId(buffer.toString());
            status = 0;
        }
        if (qName.equalsIgnoreCase("item")) {
            if (item.getId() == null) {
                item.setId("" + item.getDescription().hashCode());
            }
            list.add(item);
            item = null;
        }
        buffer = new StringBuffer();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if ((item != null) && (qName.equalsIgnoreCase("title"))) {
            status = IN_TITLE;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("link"))) {
            status = IN_LINK;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("description"))) {
            status = IN_DESCRIPTION;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("pubdate"))) {
            status = IN_PUBDATE;
            return;
        }
        if ((item != null) && (qName.equalsIgnoreCase("guid"))) {
            status = IN_GUID;
            return;
        }
        if (qName.equalsIgnoreCase("item")) {
            item = new CommonInformationItem();
            item.setSource(name);
        }
    }

}
