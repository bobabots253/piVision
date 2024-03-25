package NetworkTables;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.networktables.DoubleArrayPublisher;
import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.DoubleArrayTopic;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.DoubleTopic;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.IntegerSubscriber;
import edu.wpi.first.networktables.IntegerTopic;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StringPublisher;
import edu.wpi.first.networktables.StringSubscriber;
import edu.wpi.first.networktables.StringTopic;

public class AprilTagTable {

    public NetworkTable table;
    
    public StringTopic familyTopic;

    public IntegerTopic idTopic;
    public IntegerTopic hammingTopic;

    public DoubleTopic decisionMarginTopic;
    public DoubleArrayTopic homography;
    public DoubleTopic centerXTopic;
    public DoubleTopic centerYTopic;
    public DoubleArrayTopic cornersTopic;
    
    public AprilTagTable(String identifier, NetworkTableInstance tableInstance) {
        this.table = tableInstance.getTable("AprilTag." + identifier);

        this.familyTopic = this.table.getStringTopic("family");
        this.idTopic = this.table.getIntegerTopic("id");
        this.hammingTopic = this.table.getIntegerTopic("hamming");
        this.decisionMarginTopic = this.table.getDoubleTopic("decisionMargin");
        this.homography = this.table.getDoubleArrayTopic("homography");
        this.centerXTopic = this.table.getDoubleTopic("centerX");
        this.centerYTopic = this.table.getDoubleTopic("centerY");
        this.cornersTopic = this.table.getDoubleArrayTopic("corners");
    }

    /**
     * Use this to publish values to network tables on the Raspberry Pi
     * @return
     */
    public AprilTagTable.Publisher publisher() {
        return new AprilTagTable.Publisher(this);
    }

    /**
     * Use this to recieve values from network tables on the Rio
     * @return
     */
    public AprilTagTable.Subscriber subscriber() {
        return new AprilTagTable.Subscriber(this);
    }

    public AprilTagDetection currentAprilTag() {
        return null;
    }

    public class Publisher {
    
        public AprilTagTable table;

        public StringPublisher family;

        public IntegerPublisher id;
        public IntegerPublisher hamming;

        public DoublePublisher decisionMargin;
        public DoubleArrayPublisher homography;
        public DoublePublisher centerX;
        public DoublePublisher centerY;
        public DoubleArrayPublisher corners;

        public Publisher(AprilTagTable table) {
            this.table = table;

            this.family = table.familyTopic.publish();
            this.id = table.idTopic.publish();
            this.hamming = table.hammingTopic.publish();
            this.decisionMargin = table.decisionMarginTopic.publish();
            this.homography = table.homography.publish();
            this.centerX = table.centerXTopic.publish();
            this.centerY = table.centerYTopic.publish();
            this.corners = table.cornersTopic.publish();
        }

        public void send(AprilTagDetection detection) {
            if (detection == null) { 
                this.clear();
                return;
            }
            this.family.set(detection.getFamily());
            this.id.set(detection.getId());
            this.hamming.set(detection.getHamming());
            this.homography.set(detection.getHomography());
            this.centerX.set(detection.getCenterX());
            this.centerY.set(detection.getCenterY());
            this.corners.set(detection.getCorners());
        }

        public void clear() {
            double[] emptyArray = {};
            String emptyString ="";

            this.family.set(emptyString);
            this.id.set(0);
            this.hamming.set(0);
            this.homography.set(emptyArray);
            this.centerX.set(0);
            this.centerY.set(0);
            this.corners.set(emptyArray);
        }
    }

    public class Subscriber {
    
        public AprilTagTable table;

        public StringSubscriber family;

        public IntegerSubscriber id;
        public IntegerSubscriber hamming;

        public DoubleSubscriber decisionMargin;
        public DoubleArraySubscriber homography;
        public DoubleSubscriber centerX;
        public DoubleSubscriber centerY;
        public DoubleArraySubscriber corners;

        public Subscriber(AprilTagTable table) {
            this.table = table;

            double[] emptyDoubleArray = {};

            this.family = table.familyTopic.subscribe(null);
            this.id = table.idTopic.subscribe(0);
            this.hamming = table.hammingTopic.subscribe(0);
            this.decisionMargin = table.decisionMarginTopic.subscribe(0);
            this.homography = table.homography.subscribe(emptyDoubleArray);
            this.centerX = table.centerXTopic.subscribe(0);
            this.centerY = table.centerYTopic.subscribe(0);
            this.corners = table.cornersTopic.subscribe(emptyDoubleArray);
        }
    }
}