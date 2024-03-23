package pipelines;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//I just imported a bunch of libaries to see if it does anything.
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.naming.CommunicationException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.vision.VisionPipeline;
import edu.wpi.first.vision.VisionThread;

import org.opencv.core.Mat;

import NetworkTables.*;
import pipelines.*;

public class AprilTagPipeline implements VisionPipeline {
    private AprilTagDetector m_Detector;
    private int reticalSize = 20;
    private Scalar outlineColor = new Scalar(0, 255, 0);
    private Scalar xColor = new Scalar(0, 0, 255);   

    /**
     * All tags currently detected by the emitting camera
     */
    public AprilTagDetection[] detectedTags;

    public AprilTagPipeline(String family) {
        AprilTagDetection[] emptyTags = {};
        this.detectedTags = emptyTags;
        this.m_Detector = new AprilTagDetector();
    }

    @Override
    public void process(Mat cameraFrame) {
        //This causes a bunch of errors and cause all code after to not run. -josh
        //AprilTagDetector detector = new AprilTagDetector();
        Mat grayMat = new Mat();
        //The libary for cvtColor seems to not be able to be found causing an error. Also all code after stops running. - josh
        Imgproc.cvtColor(cameraFrame, grayMat, Imgproc.COLOR_RGB2GRAY);

        //Currently haven't tested any of the code under for errors -josh
        //CvSource outputStream = CameraServer.putVideo("detect", 640, 480);
        // TODO! How can we take our existing logic and update it to run here?
        detectedTags = m_Detector.detect(grayMat);
        
      //   for (AprilTagDetection detection : detections) {
      //       System.out.println("foundSomething");
      //       var tagID = detection.getId();
      //       var centerX = detection.getCenterX();
      //       var centerY = detection.getCenterY();

      //   tags.add(tagID);
      //   /// Draw debug rectangle

      //   for (var corner = 0; corner <= 3; corner++) {
      //     var nextCorner = (corner + 1) % 4;
      //     var pt1 = new Point(detection.getCornerX(corner), detection.getCornerY(corner));
      //     var pt2 = new Point(detection.getCornerX(nextCorner), detection.getCornerY(nextCorner));
      //     Imgproc.line(mat, pt1, pt2, outlineColor, 2);
      //   }

      //   // Draw debug retical
      //   Imgproc.line(mat, new Point(centerX - reticalSize, centerY), new Point(centerX + reticalSize, centerY), xColor, 2);
      //   Imgproc.line(mat, new Point(centerX, centerY - reticalSize), new Point(centerX, centerY + reticalSize), xColor, 2);
      //   Imgproc.putText(mat, Integer.toString(tagID), new Point (centerX + reticalSize, centerY), Imgproc.FONT_HERSHEY_SIMPLEX, 1, xColor, 3);
      // }

      SmartDashboard.putString("tag", detectedTags.toString());
      // Give the output stream a new image to display
      //outputStream.putFrame(mat);
    

        //detector.close();

        //This is to check if the end of the process command ran. - josh Sorry this is not an error
        //System.out.println("The process command ran");

        // This is the center of the vision pipeline
    }
    
}