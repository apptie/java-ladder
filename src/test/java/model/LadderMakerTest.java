package model;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import strategy.PassGenerator;
import strategy.RandomPassGenerator;

@DisplayNameGeneration(ReplaceUnderscores.class)
class LadderMakerTest {

    @Nested
    class 생성자_테스트 {

        @ParameterizedTest
        @NullSource
        void 인자로_null을_전달하면_예외가_발생한다(List<Line> lines) {
            assertThatThrownBy(() -> new Ladder(lines))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("사다리 줄이 정상적으로 입력되지 않았습니다.");
        }

        @ParameterizedTest
        @EmptySource
        void 인자로_비어_있는_컬렉션을_전달하면_예외가_발생한다(List<Line> lines) {
            assertThatThrownBy(() -> new Ladder(lines))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1 이상의 사다리 줄을 입력해주세요.");
        }

        @Test
        void 인자로_비어있지_않은_컬렉션을_전달하면_Ladder를_생성한다() {
            assertThatCode(() -> new Ladder(List.of(new Line(List.of(Path.PASSABLE)))))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("arguments.LadderMakerArguments#provideNonEqualSizeLine")
        void 인자로_Path의_크기가_다른_Line이_주어지면_예외가_발생한다(Line firstLine, Line secondLine) {
            assertThatThrownBy(() -> new Ladder(List.of(firstLine, secondLine)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class findLadder_테스트 {
        @Test
        void findLadder_메소드는_ladder가_초기화_되기_전에_호출되면_예외가_발생한다() {
            PassGenerator generator = new RandomPassGenerator();
            LadderMaker ladderMaker = new LadderMaker(generator);

            assertThatThrownBy(ladderMaker::findLadder)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("사다리가 생성되지 않았습니다.");
        }

        @Test
        void findLadder_메소드는_ladder가_초기화한_이후_호출하면_ladder를_반환한다() {
            PassGenerator generator = new RandomPassGenerator();
            Height height = new Height(5);
            LadderMaker ladderMaker = new LadderMaker(generator);
            ladderMaker.initLadder(height, 4);

            assertThatCode(ladderMaker::findLadder).doesNotThrowAnyException();
        }
    }
}
