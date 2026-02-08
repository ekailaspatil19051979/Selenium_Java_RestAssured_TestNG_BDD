package utils;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisualUtil {

    private static final String BASELINE_DIR = "src/test/resources/baselines/";
    private static final String DIFF_DIR = "target/visual-diffs/";

    public static void compareScreenshot(WebDriver driver, String fileName) throws IOException {
        LoggerUtil.info("--- VISUAL REGRESSION CHECK: " + fileName + " ---");

        File baselineFile = new File(BASELINE_DIR + fileName + ".png");

        Screenshot actualScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(driver);

        if (!baselineFile.exists()) {
            LoggerUtil.info("Baseline not found. Creating baseline: " + fileName);
            baselineFile.getParentFile().mkdirs();
            ImageIO.write(actualScreenshot.getImage(), "PNG", baselineFile);
            return;
        }

        BufferedImage expectedImage = ImageIO.read(baselineFile);
        ImageDiff diff = new ImageDiffer().makeDiff(actualScreenshot.getImage(), expectedImage);

        if (diff.hasDiff()) {
            LoggerUtil.error("Visual mismatch detected for: " + fileName);
            File diffFile = new File(DIFF_DIR + fileName + "_diff.png");
            diffFile.getParentFile().mkdirs();
            ImageIO.write(diff.getMarkedImage(), "PNG", diffFile);
        } else {
            LoggerUtil.info("Visual comparison passed for: " + fileName);
        }
    }
}
