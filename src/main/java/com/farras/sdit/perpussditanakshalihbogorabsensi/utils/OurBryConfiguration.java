package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class OurBryConfiguration {
    protected Element rootElement;
    public OurBryConfiguration(){
//        File file = new File("src/main/java/com/farras/sdit/perpussditanakshalihbogorabsensi/configurations/config.xml");
        File file = new File("config.xml");

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            document.getDocumentElement().normalize();
            this.rootElement = document.getDocumentElement();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


    public String getFetchAllDataStudentFP(){
        String baseUrl = rootElement.getElementsByTagName("base-url").item(0).getTextContent().trim();
        String jsonUrl = rootElement.getElementsByTagName("get-all-data").item(0).getTextContent().trim();
        return baseUrl+jsonUrl;
    }
    public String getTokenOurBry (){
        return rootElement.getElementsByTagName("token").item(0).getTextContent().trim();
    }
}
