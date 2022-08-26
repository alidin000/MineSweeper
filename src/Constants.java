import javax.swing.ImageIcon;

public class Constants {
    private ImageIcon flagImage = new ImageIcon("images/flag.png");
    private ImageIcon bombImage = new ImageIcon("images/bomb.png");
    private ImageIcon explosion = new ImageIcon("images/explosion.png");
    private ImageIcon omg = new ImageIcon("images/omg.png");
    private ImageIcon thumbsUp = new ImageIcon("images/thumbsUp.png");

    public Constants()
    {

    }

    public ImageIcon getFlagImg()
    {
        ImageIcon temp = flagImage;
        return temp;
    }

    public ImageIcon getBombImg()
    {
        ImageIcon temp = bombImage;
        return temp;
    }

    public ImageIcon getExplosionImg()
    {
        ImageIcon temp = explosion;
        return temp;
    }
    public ImageIcon getOmgImg()
    {
        ImageIcon temp = omg;
        return temp;
    }

    public ImageIcon getThumbImg()
    {
        ImageIcon temp = thumbsUp;
        return temp;
    }
}
