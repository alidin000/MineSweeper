import javax.swing.ImageIcon;

public class Constants {
//    private ImageIcon flagImage = new ImageIcon("images/flag.png");
private ImageIcon flagImage = new ImageIcon(getClass().getClassLoader().getResource(("flag.png")));
    private ImageIcon bombImage = new ImageIcon(getClass().getClassLoader().getResource(("bomb.png")));
    private ImageIcon explosion = new ImageIcon(getClass().getClassLoader().getResource(("explosion.png")));
    private ImageIcon omg = new ImageIcon(getClass().getClassLoader().getResource(("omg.png")));
    private ImageIcon thumbsUp = new ImageIcon(getClass().getClassLoader().getResource(("thumbsUp.png")));

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
