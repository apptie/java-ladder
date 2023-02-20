import controller.LadderController;
import utils.LadderApplicationConfig;
import utils.LadderStatus;

public class LadderApplication {

    public static void main(String[] args) {
        LadderApplicationConfig config = new LadderApplicationConfig();
        LadderController controller = config.ladderController();
        LadderStatus ladderStatus = LadderStatus.INPUT_PARTICIPANT_NAMES;

        while (ladderStatus.canPlay()) {
            ladderStatus = controller.run(ladderStatus);
        }
    }

}
