package pipelines;

import org.opencv.core.Mat; 

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.vision.VisionPipeline;

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
        // TODO! How can we take our existing logic and update it to run here?
        // This is the center of the vision pipeline
    }

    
}
