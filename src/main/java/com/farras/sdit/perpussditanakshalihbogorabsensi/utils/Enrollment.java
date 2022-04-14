package com.farras.sdit.perpussditanakshalihbogorabsensi.utils;

import com.digitalpersona.uareu.*;
import com.farras.sdit.perpussditanakshalihbogorabsensi.controller.EnrollmentController;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Enrollment
        extends JPanel
        implements ActionListener
{

    public class EnrollmentThread
            extends Thread
            implements Engine.EnrollmentCallback
    {
        public static final String ACT_PROMPT   = "enrollment_prompt";
        public static final String ACT_CAPTURE  = "enrollment_capture";
        public static final String ACT_FEATURES = "enrollment_features";
        public static final String ACT_DONE     = "enrollment_done";
        public static final String ACT_CANCELED = "enrollment_canceled";

        public class EnrollmentEvent extends ActionEvent{
            private static final long serialVersionUID = 102;

            public Reader.CaptureResult capture_result;
            public Reader.Status        reader_status;
            public UareUException       exception;
            public Fmd                  enrollment_fmd;

            public EnrollmentEvent(Object source, String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex){
                super(source, ActionEvent.ACTION_PERFORMED, action);
                capture_result = cr;
                reader_status = st;
                exception = ex;
                enrollment_fmd = fmd;
            }
        }

        private final Reader   m_reader;
        private CaptureThread  m_capture;
        private ActionListener m_listener;
        private boolean m_bCancel;

        protected EnrollmentThread(Reader reader, ActionListener listener){
            m_reader = reader;
            m_listener = listener;
        }

        public Engine.PreEnrollmentFmd GetFmd(Fmd.Format format){
            Engine.PreEnrollmentFmd prefmd = null;

            while(null == prefmd && !m_bCancel){

                System.out.println("\u001B[31m"+"Dua run capture thread (Enrollment.java)"+"\u001B[0m");
                //start capture thread
                m_capture = new CaptureThread(m_reader, false, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
                m_capture.start(null);

                //prompt for finger
                SendToListener(ACT_PROMPT, null, null, null, null);


                //wait till done
                m_capture.join(0);
                System.out.println("\u001B[31m"+"Lima sent to listener (Enrollment.java)"+"\u001B[0m");
                //check result
                CaptureThread.CaptureEvent evt = m_capture.getLastCaptureEvent();
                if(null != evt.capture_result){
                    if(Reader.CaptureQuality.CANCELED == evt.capture_result.quality){
                        //capture canceled, return null
                        break;
                    }
                    else if(null != evt.capture_result.image && Reader.CaptureQuality.GOOD == evt.capture_result.quality){
                        //acquire engine
                        Engine engine = UareUGlobal.GetEngine();

                        try{
                            //extract features
                            Fmd fmd = engine.CreateFmd(evt.capture_result.image, Fmd.Format.ANSI_378_2004);

                            //return prefmd
                            prefmd = new Engine.PreEnrollmentFmd();
                            prefmd.fmd = fmd;
                            prefmd.view_index = 0;

                            //send success
                            SendToListener(ACT_FEATURES, null, null, null, null);
                        }
                        catch(UareUException e){
                            //send extraction error
                            SendToListener(ACT_FEATURES, null, null, null, e);
                        }
                    }
                    else{
                        //send quality result
                        SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                    }
                }
                else{
                    //send capture error
                    SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                }
            }

            return prefmd;
        }

        public void cancel(){
            m_bCancel = true;
            if(null != m_capture) m_capture.cancel();
        }

        private void SendToListener(String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex){
            if(null == m_listener || null == action || action.equals("")) return;

            final EnrollmentEvent evt = new EnrollmentEvent(this, action, fmd, cr, st, ex);

            //invoke listener on EDT thread
            try {
                javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        m_listener.actionPerformed(evt);
                    }
                });
            }
            catch (InvocationTargetException e) { e.printStackTrace(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

        public void run(){
            firstFmd = null;
            secondFmd = null;
            //acquire engine
            Engine engine = UareUGlobal.GetEngine();

            try{
                m_bCancel = false;
                while(!m_bCancel){
                    System.out.println("\u001B[31m"+ "Satu ......."+ "\u001B[0m");
                    //run enrollment
                    Fmd fmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, this);
                    System.out.println("\u001B[43m"+ "Unfreeze Enrollment FMD are created (Enrollmen.java)"+ "\u001B[0m");
                    //send result
                    if(null != fmd){
                        SendToListener(ACT_DONE, fmd, null, null, null);
                    }
                    else{
                        System.out.println("\u001B[43m"+ "Sent ACT-CANCELED to Listener (Enrollmen.java)"+ "\u001B[0m");
                        SendToListener(ACT_CANCELED, null, null, null, null);
                        break;
                    }
                }
            }
            catch(UareUException e){
                System.out.println("Ini karena gagal buat FMD");
                SendToListener(ACT_DONE, null, null, null, e);
            }
        }
    }





    private static final long serialVersionUID = 6;
    private static final String ACT_BACK = "back";

    private EnrollmentThread m_enrollment;
    private Reader  m_reader;

    private ImageView firstImage;
    private ImageView secondImage;
    private Label labelEnrollmentStatus;
    private ResourceBundle resourceBundle;

    private boolean m_bJustStarted;
    private final SoundNotification trueNotification = new SoundNotification(SoundNotification.SoundType.SUCCESS);
    private final SoundNotification falseNotification = new SoundNotification(SoundNotification.SoundType.FAILED);
    private final SoundNotification doneNotification = new SoundNotification(SoundNotification.SoundType.DONE);

    // to store fmd temporary
    public static Fmd firstFmd = null;
    public static Fmd secondFmd = null;
    public static Fmd[] bothFmd;

    private Enrollment(Reader reader, ImageView firstImage, ImageView secondImage, Label labelEnrollmentStatus, ResourceBundle resourceBundle){
        m_reader = reader;
        this.firstImage = firstImage;
        this.secondImage = secondImage;
        m_bJustStarted = true;
        m_enrollment = new EnrollmentThread(m_reader, this);
        this.labelEnrollmentStatus = labelEnrollmentStatus;
        this.resourceBundle = resourceBundle;
    }

    private void ChangeLabelJavafxInSwingThread (Label label, String string){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(string);
            }
        });
    }

    public void actionPerformed(ActionEvent e){
        System.out.println(this.labelEnrollmentStatus.getText());
        EnrollmentThread.EnrollmentEvent evt = (EnrollmentThread.EnrollmentEvent)e;
        System.out.println("\u001B[34m"+"Menunggu dan menerima"+"\u001B[0m");
        if(e.getActionCommand().equals(EnrollmentThread.ACT_PROMPT)){
            trueNotification.turnOn();
            if(m_bJustStarted){
                ChangeLabelJavafxInSwingThread(labelEnrollmentStatus, resourceBundle.getString("enrollment_start_capture_new_finger"));
                System.out.println("Enrollment started");
                System.out.println("::put any finger on the reader");
            }
            else{
                ChangeLabelJavafxInSwingThread(labelEnrollmentStatus, resourceBundle.getString("enrollment_same_finger"));
                System.out.println("::put the same finger on the reader");
            }
            m_bJustStarted = false;
        }
        else if(e.getActionCommand().equals(EnrollmentThread.ACT_CAPTURE)){
            System.out.println("\u001B[41m Enam Hasil diterima \\u001B[0m");
            if(null != evt.capture_result){
                System.out.println(evt.capture_result.quality);
            }
            else if(null != evt.exception){
                System.out.println("Capture"+ evt.exception);
            }
            else if(null != evt.reader_status){
                System.out.println(evt.reader_status);
            }
            m_bJustStarted = false;
        }
        else if(e.getActionCommand().equals(EnrollmentThread.ACT_FEATURES)){
            if(null == evt.exception){
                System.out.println("::fingerprint captured, features extracted\n\n");
            }
            else{
                System.out.println("Feature extraction" + evt.exception);
            }
            m_bJustStarted = false;
        }
        else if(e.getActionCommand().equals(EnrollmentThread.ACT_DONE)){
            if(null == evt.exception){
                doneNotification.turnOn();
                String str = String.format("::enrollment template created, size: %d\n\n\n", evt.enrollment_fmd.getData().length);
                System.out.println(str);


                if (firstFmd == null){
                    System.out.println("\u001B[45m"+ "First FMD is fill (Enrollment.java)"+ "\u001B[0m");
                    firstFmd = evt.enrollment_fmd;
                    firstImage.setImage(new Image(getClass().getResource("/image/fingerprint-success.png").toExternalForm()));
                    firstImage.setOpacity(1);

                }
                else if (secondFmd == null) {
                    ChangeLabelJavafxInSwingThread(labelEnrollmentStatus, resourceBundle.getString("enrollment_finish"));
                    System.out.println("\u001B[45m"+ "Second FMD is fill (Enrollment.java)"+ "\u001B[0m");
                    secondFmd = evt.enrollment_fmd;
                    System.out.println("\u001B[45m"+ "Both FMDs is fill - Close(Enrollment.java)"+ "\u001B[0m");
                    bothFmd = new Fmd[]{firstFmd, secondFmd};
                    secondImage.setImage(new Image(getClass().getResource("/image/fingerprint-success.png").toExternalForm()));
                    secondImage.setOpacity(1);

                    // Make both of them null
                    firstFmd = null;
                    secondFmd = null;
                    m_enrollment.cancel();
                }
            }
            else{
                System.out.println("Enrollment template creation " + evt.exception);
                falseNotification.turnOn();
            }
            m_bJustStarted = true;
        }
        else if(e.getActionCommand().equals(EnrollmentThread.ACT_CANCELED)){
            System.out.println("\u001B[43m"+ "Final Canceled Enrollment (Enrollmen.java)"+ "\u001B[0m");
            //canceled, destroy dialog
        }

        //cancel enrollment if any exception or bad reader status
        if(null != evt.exception){
//            evt.exception.printStackTrace();

            System.out.println ("cancel enrollment");
//                m_dlgParent.setVisible(false);
        }
        else if(null != evt.reader_status && Reader.ReaderStatus.READY != evt.reader_status.status && Reader.ReaderStatus.NEED_CALIBRATION != evt.reader_status.status){
//                m_dlgParent.setVisible(false);
            System.out.println("Gak tau apa ini");
        }
    }


    protected static Enrollment enrollmentForClose;

    public static void Run(Reader reader, ImageView image1, ImageView image2, Label labelEnrollmentStatus, ResourceBundle resourceBundle) {
        Enrollment enrollment = new Enrollment(reader, image1, image2, labelEnrollmentStatus, resourceBundle);
        enrollmentForClose = enrollment;
        enrollment.m_enrollment.start();
    }

    public static void Cancel(){
        enrollmentForClose.m_enrollment.cancel();
    }

    public static Fmd[] GetBothFmds(){
        return bothFmd;
    }
}








