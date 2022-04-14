package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.PopupWindow;

public class ReaderFP {

    private static final String SQUARE_BUBBLE = "M24 1h-24v16.981h4v5.019l7-5.019h13z";
    public static Reader myReaderFP;

    public ReaderFP () {
        try {
            // Get reader from Readers Collection
            UareUGlobal.GetReaderCollection().GetReaders();

            // everytime this object is initiated, the readers must be close
            closeReader();

            if (UareUGlobal.GetReaderCollection().size() > 0 ){
                myReaderFP = UareUGlobal.GetReaderCollection().get(0);
                myReaderFP.Open(Reader.Priority.EXCLUSIVE);
//                System.out.println("Opening the reader");
            }
            else {
//                System.out.println("The reader is not detected, so it's not open");
                myReaderFP = null;
            }
        }
        catch (UareUException e){
            e.printStackTrace();
        }
    }

    public void closeReader(){
        if (myReaderFP != null){
            try {
                // check apakah reader masih terpasang atau tidak
                if (UareUGlobal.GetReaderCollection().size() > 0){
                    myReaderFP.Close();
//                    System.out.println("Closing the reader");
                }

            } catch (UareUException ex) {
                myReaderFP = null;
//                System.out.println("The reader is removed by accident");
//                System.out.println("Then must be open");
            }
        }
        else {
//            System.out.println("Then reader is not close, cause it no open");
//            System.out.println("Then must be open");
        }
    }

    public Reader realReader (){
        return myReaderFP;
    }

    public void readerStatusByCircleColor (Circle circle_reader_status, Reader.ReaderStatus readerStatus){

        if (readerStatus == Reader.ReaderStatus.READY){
            circle_reader_status.setFill(Color.GREEN);
            circle_reader_status.setStroke(Color.GREEN);
        }
        else {
            circle_reader_status.setFill(Color.RED);
            circle_reader_status.setStroke(Color.RED);
        }

        if (readerStatus != null) {
            Tooltip.install(circle_reader_status, customTooltip(new Tooltip(readerStatus.toString())));
        } else {
            Tooltip.install(circle_reader_status, customTooltip(new Tooltip("Reader is not set")));
        }

    }


    /*
    Menggambarkan status reader dengan icon
     */
    private Tooltip customTooltip(Tooltip tooltip){
        tooltip.setStyle("-fx-font-size: 8px; -fx-shape: \"" + SQUARE_BUBBLE + "\";");
        tooltip.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_BOTTOM_LEFT);

        return tooltip;
    }

    public Reader.ReaderStatus getReaderFpStatus (){
        try {
            return myReaderFP.GetStatus().status;
        }
        catch (UareUException | NullPointerException e){
            return null;
        }
    }



}
