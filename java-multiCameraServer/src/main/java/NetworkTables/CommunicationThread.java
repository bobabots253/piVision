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
    synchronized(this) {
        // System.out.println(this.primaryTagTable.familyTopic.toString());
        // System.out.println(this.primaryTagTable.idTopic.toString());
        // System.out.println(this.primaryTagTable.hammingTopic.toString());
        // System.out.println(this.primaryTagTable.decisionMarginTopic.toString());
        // System.out.println(this.primaryTagTable.homography.toString());
        // System.out.println(this.primaryTagTable.centerXTopic.toString());
        // System.out.println(this.primaryTagTable.centerYTopic.toString());
        // System.out.println(this.primaryTagTable.cornersTopic.toString());
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