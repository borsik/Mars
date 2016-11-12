import java.util.LinkedList;

public class Terrain {
    private final int a = 100000;
    private int height;
    private int width;
    private Place[][] terrain;
    private Point start, finish;
    private LinkedList<Point> sourcesOfRadiation;
    private LinkedList<Point> path;

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }


    public Terrain(int width, int height){

        terrain = new Place[height][width];
        this.width = width;
        this.height = height;
        sourcesOfRadiation = new LinkedList();
        path = new LinkedList();
    }

    public Place[][] getTerrain() {
        return terrain;
    }

    public LinkedList<Point> getSourcesOfRadiation() {
        return sourcesOfRadiation;
    }

    public LinkedList<Point> getPath() {
        return path;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getFinish() {
        return finish;
    }

    public void setFinish(Point finish) {
        this.finish = finish;
    }

    public double length (int i, int j, Point p){
        return Math.sqrt( (i - p.y())*(i - p.y()) + (j - p.x())*(j - p.x()) );
    }

    public void calculateRadiation(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for (Point p: sourcesOfRadiation ) {
                    if(p.y() != i && p.x() != j)
                        terrain[i][j].setRadiation(terrain[i][j].getRadiation() + a / length(i,j,p) );
                }
            }
        }
    }
}
