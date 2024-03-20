package NetworkTables;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagPoseEstimate;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CommunicationThread {
    
    public NetworkTableInstance networkTableInstance;

    private AprilTagDetection primaryDetection;

    // MARK: - Table References
    private AprilTagTable primaryTagTable;
    private AprilTagTable.Publisher primaryTagPublisher;

    public CommunicationThread(NetworkTableInstance networkTableInstance) {
        this.networkTableInstance = networkTableInstance;
        this.primaryTagTable = new AprilTagTable("Primary", networkTableInstance);
        this.primaryTagPublisher = primaryTagTable.publisher();
    }

   public void periodic() {
    // This is a dedicated space that can communicate with other processors
    // without interrupting or blocking detection of the next frame
    //Josh Later replace "this" with primaryDetection or the thing you are trying to send.
    synchronized(primaryDetection) {
        this.primaryTagPublisher.send(primaryDetection);// I don't know what to send but I'm pretty sure becuase we are sending primaryDetection it is a null error.
    }
   }

   // MARK: - Synchronized Setter

   public void setPrimaryTag(AprilTagDetection detection) {
    synchronized(primaryDetection) {
        this.primaryDetection = detection;
    }
   }
}