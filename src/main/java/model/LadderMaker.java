package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import strategy.PassGenerator;

public class LadderMaker {

    private static final int CONVERT_INDEX_VALUE = 1;

    private final PassGenerator generator;
    private Ladder ladder;

    public LadderMaker(PassGenerator generator) {
        this.generator = generator;
    }

    public void initLadder(Height height, int totalParticipantSize) {
        List<Line> lines = new ArrayList<>();

        while (height.isContinueMakeLadder()) {
            lines.add(initLine(totalParticipantSize));
        }
        ladder = new Ladder(lines);
    }

    private Line initLine(int peopleCount) {
        List<Path> paths = new ArrayList<>();

        while (--peopleCount > 0) {
            paths.add(generatePath(paths));
        }
        return new Line(paths);
    }

    private Path generatePath(List<Path> paths) {
        int size = paths.size();

        if (size > 0) {
            return Path.calculatePath(paths.get(size - CONVERT_INDEX_VALUE), generator.generate());
        }
        return Path.calculatePath(generator.generate());
    }

    public Ladder findLadder() {
        if (Objects.isNull(ladder)) {
            throw new IllegalStateException("사다리가 생성되지 않았습니다.");
        }
        return ladder;
    }
}
