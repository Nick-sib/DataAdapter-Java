package ltd.nickolay.listclick.Jv;

public class DataItem {
    private int di_Image;
    private String di_Title;
    private String di_SubText;

    public DataItem(int image, String title, String subtext) {
        di_Image = image;
        di_Title = title;
        di_SubText = subtext;
    }

    public int getImage() {
        return di_Image;
    }

    public String getTitle() {
        return di_Title;
    }

    public String getSubText() {
        return di_SubText;
    }
}
