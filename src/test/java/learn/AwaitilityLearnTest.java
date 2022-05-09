package learn;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AwaitilityLearnTest {
    private AsyncService asyncService;

    @Before
    public void setUp() {
        asyncService = new AsyncService();
    }

    @Test
    @DisplayName("Awaitility 기본 기능 테스트")
    public void AwaitilityBasicTest() {
        asyncService.initialize();
        System.out.println(asyncService.isInitialized());
        await()
                .atLeast(Duration.of(100, ChronoUnit.MILLIS)) //min 대기시간
                .atMost(Duration.of(5, ChronoUnit.SECONDS)) //max 대기시간
                .with()
                .pollInterval(Duration.of(100, ChronoUnit.MILLIS)) //polling 주가
                .until(asyncService::isInitialized); //종료 조건

        System.out.println(asyncService.isInitialized());

        assertTrue(true);
    }
}
