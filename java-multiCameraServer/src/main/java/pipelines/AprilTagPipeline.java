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

public class AprilTagPipeline implements VisionPipeline {


    /**
     * All tags currently detected by the emitting camera
     */
    public AprilTagDetection[] detectedTags;

    public AprilTagPipeline(String family) {
        AprilTagDetection[] emptyTags = {};
        this.detectedTags = emptyTags;
    }

    @Override
    public void process(Mat cameraFrame) {
        Mat mat = new Mat();
        Mat grayMat = new Mat();
        AprilTagDetector detector = new AprilTagDetector();
        Imgproc.cvtColor(mat, grayMat, Imgproc.COLOR_RGB2GRAY);
        var reticalSize = 20;
        Scalar outlineColor = new Scalar(0, 255, 0);
        Scalar xColor = new Scalar(0, 0, 255);
        ArrayList<Integer> tags = new ArrayList<>();
        CvSource outputStream = CameraServer.putVideo("detect", 640, 480);
        // TODO! How can we take our existing logic and update it to run here?
        AprilTagDetection[] detections= detector.detect(grayMat);
        tags.clear();
        for (AprilTagDetection detection : detections) {
            System.out.println("foundSomething");
            var tagID = detection.getId();
            var centerX = detection.getCenterX();
            var centerY = detection.getCenterY();

        tags.add(tagID);
        /// Draw debug rectangle

        for (var corner = 0; corner <= 3; corner++) {
          var nextCorner = (corner + 1) % 4;
          var pt1 = new Point(detection.getCornerX(corner), detection.getCornerY(corner));
          var pt2 = new Point(detection.getCornerX(nextCorner), detection.getCornerY(nextCorner));
          Imgproc.line(mat, pt1, pt2, outlineColor, 2);
        }

        // Draw debug retical
        Imgproc.line(mat, new Point(centerX - reticalSize, centerY), new Point(centerX + reticalSize, centerY), xColor, 2);
        Imgproc.line(mat, new Point(centerX, centerY - reticalSize), new Point(centerX, centerY + reticalSize), xColor, 2);
        Imgproc.putText(mat, Integer.toString(tagID), new Point (centerX + reticalSize, centerY), Imgproc.FONT_HERSHEY_SIMPLEX, 1, xColor, 3);
      }

      SmartDashboard.putString("tag", tags.toString());
      // Give the output stream a new image to display
      outputStream.putFrame(mat);
    

        detector.close();
  
        
        // This is the center of the vision pipeline
    }
    
}