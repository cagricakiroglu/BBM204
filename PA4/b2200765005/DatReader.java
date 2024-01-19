import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatReader {

    private String fileContent;

    public DatReader(String filename) {
        Path filePath = Path.of(filename);
        try {
            fileContent = Files.readString(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getStringVar(String varName) {
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public Double getDoubleVar(String varName) {
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]*\\.?[0-9]+)");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return null;
    }

    public int getIntVar(String varName) {
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*([0-9]+)");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public Point getPointVar(String varName) {
        Point point = new Point(0, 0);
        Pattern pattern = Pattern.compile("[\\t ]*" + varName + "[\\t ]*=[\\t ]*\\([\\t ]*([0-9]+)[\\t ]*,[\\t ]*([0-9]+)[\\t ]*\\)");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            point.x = Integer.parseInt(matcher.group(1));
            point.y = Integer.parseInt(matcher.group(2));
        }
        return point;
    }
}
